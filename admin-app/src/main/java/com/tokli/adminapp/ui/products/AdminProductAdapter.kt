package com.tokli.adminapp.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokli.adminapp.data.model.Product
import com.tokli.adminapp.databinding.ItemAdminProductBinding

class AdminProductAdapter(
    private val items: MutableList<Product>,
    private val onEdit: (Product) -> Unit,
    private val onDelete: (Product) -> Unit
) : RecyclerView.Adapter<AdminProductAdapter.VH>() {

    inner class VH(private val binding: ItemAdminProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textName.text = product.name
            binding.textPrice.text = "$${product.price}"
            binding.buttonEdit.setOnClickListener { onEdit(product) }
            binding.buttonDelete.setOnClickListener { onDelete(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAdminProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    fun submit(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
