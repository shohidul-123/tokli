package com.tokli.adminapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tokli.adminapp.data.repository.AdminRepository
import com.tokli.adminapp.databinding.ActivityDashboardBinding
import com.tokli.adminapp.ui.orders.OrderManagementActivity
import com.tokli.adminapp.ui.products.ProductEditorActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val repo = AdminRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonManageProducts.setOnClickListener {
            startActivity(Intent(this, ProductEditorActivity::class.java))
        }
        binding.buttonManageOrders.setOnClickListener {
            startActivity(Intent(this, OrderManagementActivity::class.java))
        }

        repo.streamProducts(onUpdate = { binding.textProductCount.text = "Products: ${it.size}" }, onError = {})
        repo.streamOrders(onUpdate = { binding.textOrderCount.text = "Orders: ${it.size}" }, onError = {})
    }
}
