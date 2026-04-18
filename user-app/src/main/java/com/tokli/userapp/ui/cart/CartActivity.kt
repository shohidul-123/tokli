package com.tokli.userapp.ui.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tokli.userapp.data.repository.UserRepository
import com.tokli.userapp.databinding.ActivityCartBinding
import com.tokli.userapp.util.CartManager

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val repo = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = CartManager.all()
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerCart.adapter = CartAdapter(items)
        binding.textTotal.text = "Total: $${CartManager.total()}"

        binding.buttonPlaceOrder.setOnClickListener {
            val uid = Firebase.auth.currentUser?.uid.orEmpty()
            if (uid.isBlank() || items.isEmpty()) {
                Toast.makeText(this, "Cart is empty or user invalid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            repo.placeOrder(uid, items) {
                it.onSuccess {
                    CartManager.clear()
                    Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show()
                    finish()
                }.onFailure { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
