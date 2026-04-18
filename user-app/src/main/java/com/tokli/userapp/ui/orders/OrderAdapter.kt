package com.tokli.userapp.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokli.userapp.data.model.Order
import com.tokli.userapp.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderAdapter(private val orders: MutableList<Order>) : RecyclerView.Adapter<OrderAdapter.OrderVH>() {
    inner class OrderVH(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.textOrderId.text = "Order #${order.id.take(8)}"
            binding.textTotal.text = "Total: $${order.totalPrice}"
            binding.textStatus.text = order.status
            binding.textDate.text = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                .format(Date(order.timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderVH(binding)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderVH, position: Int) = holder.bind(orders[position])

    fun submit(items: List<Order>) {
        orders.clear()
        orders.addAll(items)
        notifyDataSetChanged()
    }
}
