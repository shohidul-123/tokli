package com.tokli.adminapp.data.repository

import android.net.Uri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tokli.adminapp.data.model.Order
import com.tokli.adminapp.data.model.Product

class AdminRepository {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                if (!email.endsWith("@admin.com")) {
                    onResult(Result.failure(IllegalAccessException("Only admin accounts are allowed")))
                } else {
                    onResult(Result.success(Unit))
                }
            }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    fun upsertProduct(existingId: String?, name: String, price: Double, description: String, imageUri: Uri?, onResult: (Result<Unit>) -> Unit) {
        if (imageUri == null) {
            saveProduct(existingId, name, price, description, "", onResult)
            return
        }
        val fileName = "products/${System.currentTimeMillis()}.jpg"
        val ref = storage.reference.child(fileName)
        ref.putFile(imageUri).continueWithTask { ref.downloadUrl }
            .addOnSuccessListener { url ->
                saveProduct(existingId, name, price, description, url.toString(), onResult)
            }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    private fun saveProduct(existingId: String?, name: String, price: Double, description: String, imageUrl: String, onResult: (Result<Unit>) -> Unit) {
        val doc = if (existingId.isNullOrBlank()) db.collection("products").document() else db.collection("products").document(existingId)
        val data = Product(doc.id, name, price, description, imageUrl)
        doc.set(data)
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    fun deleteProduct(id: String, onResult: (Result<Unit>) -> Unit) {
        db.collection("products").document(id).delete()
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }

    fun streamProducts(onUpdate: (List<Product>) -> Unit, onError: (Exception) -> Unit) =
        db.collection("products").addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener onError(e)
            onUpdate(snapshot?.documents?.mapNotNull { doc -> doc.toObject(Product::class.java)?.copy(id = doc.id) }.orEmpty())
        }

    fun streamOrders(onUpdate: (List<Order>) -> Unit, onError: (Exception) -> Unit) =
        db.collection("orders").addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener onError(e)
            onUpdate(snapshot?.documents?.mapNotNull { it.toObject(Order::class.java)?.copy(id = it.id) }.orEmpty())
        }

    fun updateOrderStatus(orderId: String, status: String, onResult: (Result<Unit>) -> Unit) {
        db.collection("orders").document(orderId).update("status", status)
            .addOnSuccessListener { onResult(Result.success(Unit)) }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }
}
