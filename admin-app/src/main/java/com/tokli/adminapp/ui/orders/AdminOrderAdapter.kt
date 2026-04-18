package com.tokli.adminapp.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokli.adminapp.data.model.Order
import com.tokli.adminapp.databinding.ItemAdminOrderBinding

class AdminOrderAdapter(
    private val items: MutableList<Order>,
    private val onStatusChange: (Order, String) -> Unit
) : RecyclerView.Adapter<AdminOrderAdapter.VH>() {
    inner class VH(private val binding: ItemAdminOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.textOrderId.text = "Order #${order.id.take(8)}"
            binding.textTotal.text = "$${order.totalPrice}"
            binding.textStatus.text = order.status
            binding.buttonPending.setOnClickListener { onStatusChange(order, "Pending") }
            binding.buttonShipped.setOnClickListener { onStatusChange(order, "Shipped") }
            binding.buttonDelivered.setOnClickListener { onStatusChange(order, "Delivered") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAdminOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    fun submit(newItems: List<Order>) {
        items.clear()
        items.addAll(newItems.sortedByDescending { it.timestamp })
        notifyDataSetChanged()
    }
}
