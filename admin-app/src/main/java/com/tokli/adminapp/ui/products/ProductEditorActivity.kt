package com.tokli.adminapp.ui.products

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tokli.adminapp.data.model.Product
import com.tokli.adminapp.data.repository.AdminRepository
import com.tokli.adminapp.databinding.ActivityProductEditorBinding

class ProductEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductEditorBinding
    private val repo = AdminRepository()
    private var selectedImage: Uri? = null
    private var editingProduct: Product? = null
    private val adapter = AdminProductAdapter(mutableListOf(), onEdit = {
        editingProduct = it
        binding.inputName.setText(it.name)
        binding.inputPrice.setText(it.price.toString())
        binding.inputDescription.setText(it.description)
    }, onDelete = {
        repo.deleteProduct(it.id) { result ->
            result.onFailure { err -> Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show() }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter

        binding.buttonPickImage.setOnClickListener {
            Toast.makeText(this, "Connect image picker intent here", Toast.LENGTH_SHORT).show()
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.inputName.text.toString().trim()
            val price = binding.inputPrice.text.toString().toDoubleOrNull()
            val description = binding.inputDescription.text.toString().trim()
            if (name.isBlank() || price == null || description.isBlank()) {
                Toast.makeText(this, "Invalid values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            repo.upsertProduct(editingProduct?.id, name, price, description, selectedImage) {
                it.onSuccess {
                    editingProduct = null
                    binding.inputName.setText("")
                    binding.inputPrice.setText("")
                    binding.inputDescription.setText("")
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                }.onFailure { err ->
                    Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        repo.streamProducts(onUpdate = { adapter.submit(it) }, onError = {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
    }
}
