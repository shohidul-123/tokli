package com.tokli.adminapp.data.model

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val imageUrl: String = ""
)

data class Order(
    val id: String = "",
    val userId: String = "",
    val productList: List<Map<String, Any>> = emptyList(),
    val totalPrice: Double = 0.0,
    val status: String = "Pending",
    val timestamp: Long = 0L
)
