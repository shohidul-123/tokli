package com.tokli.adminapp.ui.orders

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tokli.adminapp.data.repository.AdminRepository
import com.tokli.adminapp.databinding.ActivityOrderManagementBinding

class OrderManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderManagementBinding
    private val repo = AdminRepository()
    private val adapter = AdminOrderAdapter(mutableListOf()) { order, status ->
        repo.updateOrderStatus(order.id, status) {
            it.onFailure { err -> Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrders.adapter = adapter

        repo.streamOrders(onUpdate = { adapter.submit(it) }, onError = {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
    }
}
