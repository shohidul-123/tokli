package com.tokli.userapp.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tokli.userapp.data.model.CartItem
import com.tokli.userapp.data.model.Order
import com.tokli.userapp.data.model.Product
import com.tokli.userapp.data.model.UserProfile

class UserRepository {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    fun register(name: String, email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = auth.currentUser?.uid.orEmpty()
                val profile = UserProfile(uid, name, email)
                db.collection("users").document(uid).set(profile)
                    .addOnSuccessListener { onResult(Result.success(Unit)) }
                    .addOnFailureListener { onResult(Result.failure(it)) }
            }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    fun streamProducts(onUpdate: (List<Product>) -> Unit, onError: (Exception) -> Unit) =
        db.collection("products").addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener onError(e)
            val products = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Product::class.java)?.copy(id = doc.id)
            }.orEmpty()
            onUpdate(products)
        }

    fun placeOrder(userId: String, cartItems: List<CartItem>, onResult: (Result<Unit>) -> Unit) {
        val total = cartItems.sumOf { it.product.price * it.quantity }
        val ref = db.collection("orders").document()
        val order = Order(ref.id, userId, cartItems, total, "Pending", System.currentTimeMillis())
        ref.set(order)
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    fun streamOrders(userId: String, onUpdate: (List<Order>) -> Unit, onError: (Exception) -> Unit) =
        db.collection("orders").whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener onError(e)
                val orders = snapshot?.documents?.mapNotNull { it.toObject(Order::class.java) }.orEmpty()
                onUpdate(orders.sortedByDescending { it.timestamp })
            }
}
