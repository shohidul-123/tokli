package com.tokli.userapp.ui.product

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tokli.userapp.data.repository.UserRepository
import com.tokli.userapp.databinding.ActivityProductDetailsBinding
import com.tokli.userapp.util.CartManager

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private val repo = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("productId").orEmpty()
        if (productId.isBlank()) return

        repo.streamProducts(onUpdate = { products ->
            val product = products.firstOrNull { it.id == productId } ?: return@streamProducts
            binding.textName.text = product.name
            binding.textPrice.text = "$${product.price}"
            binding.textDescription.text = product.description
            Glide.with(this).load(product.imageUrl).into(binding.imageProduct)
            binding.buttonAddToCart.setOnClickListener {
                CartManager.add(product)
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
            }
        }, onError = {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
    }
}
