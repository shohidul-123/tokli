package com.tokli.userapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tokli.userapp.data.repository.UserRepository
import com.tokli.userapp.databinding.ActivityHomeBinding
import com.tokli.userapp.ui.cart.CartActivity
import com.tokli.userapp.ui.orders.OrderHistoryActivity
import com.tokli.userapp.ui.product.ProductDetailsActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val repo = UserRepository()
    private val adapter = ProductAdapter(mutableListOf()) { product ->
        startActivity(Intent(this, ProductDetailsActivity::class.java).putExtra("productId", product.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter

        binding.buttonCart.setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
        binding.buttonOrders.setOnClickListener { startActivity(Intent(this, OrderHistoryActivity::class.java)) }

        repo.streamProducts(
            onUpdate = { adapter.submit(it) },
            onError = { Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show() }
        )
    }
}
