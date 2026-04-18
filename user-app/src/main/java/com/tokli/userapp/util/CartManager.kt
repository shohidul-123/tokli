package com.tokli.userapp.util

import com.tokli.userapp.data.model.CartItem
import com.tokli.userapp.data.model.Product

object CartManager {
    private val items = mutableListOf<CartItem>()

    fun all(): List<CartItem> = items.toList()

    fun add(product: Product) {
        val index = items.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val existing = items[index]
            items[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            items.add(CartItem(product, 1))
        }
    }

    fun total(): Double = items.sumOf { it.product.price * it.quantity }

    fun clear() = items.clear()
}
