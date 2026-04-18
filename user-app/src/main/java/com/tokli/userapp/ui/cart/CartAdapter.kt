package com.tokli.userapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokli.userapp.data.model.CartItem
import com.tokli.userapp.databinding.ItemCartBinding

class CartAdapter(private val items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartVH>() {
    inner class CartVH(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.textName.text = item.product.name
            binding.textQty.text = "x${item.quantity}"
            binding.textPrice.text = "$${item.product.price * item.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartVH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CartVH, position: Int) = holder.bind(items[position])
}
