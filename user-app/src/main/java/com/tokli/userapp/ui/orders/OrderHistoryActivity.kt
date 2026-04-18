package com.tokli.userapp.ui.orders

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tokli.userapp.data.repository.UserRepository
import com.tokli.userapp.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private val repo = UserRepository()
    private val adapter = OrderAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrders.adapter = adapter

        val uid = Firebase.auth.currentUser?.uid.orEmpty()
        repo.streamOrders(uid, { adapter.submit(it) }) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}
