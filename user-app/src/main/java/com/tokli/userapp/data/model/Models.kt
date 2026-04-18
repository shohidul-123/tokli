package com.tokli.userapp.data.model

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val imageUrl: String = ""
)

data class UserProfile(
    val id: String = "",
    val name: String = "",
    val email: String = ""
)

data class CartItem(
    val product: Product = Product(),
    val quantity: Int = 1
)

data class Order(
    val id: String = "",
    val userId: String = "",
    val productList: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val status: String = "Pending",
    val timestamp: Long = System.currentTimeMillis()
)
