package com.tokli.userapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tokli.userapp.data.model.Product
import com.tokli.userapp.databinding.ItemProductBinding

class ProductAdapter(
    private val products: MutableList<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    inner class ProductVH(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textName.text = product.name
            binding.textPrice.text = "$${product.price}"
            Glide.with(binding.imageProduct).load(product.imageUrl).into(binding.imageProduct)
            binding.root.setOnClickListener { onClick(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductVH(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductVH, position: Int) = holder.bind(products[position])

    fun submit(newItems: List<Product>) {
        products.clear()
        products.addAll(newItems)
        notifyDataSetChanged()
    }
}
