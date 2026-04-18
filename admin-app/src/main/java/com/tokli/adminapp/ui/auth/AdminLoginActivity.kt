package com.tokli.adminapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tokli.adminapp.data.repository.AdminRepository
import com.tokli.adminapp.databinding.ActivityAdminLoginBinding
import com.tokli.adminapp.ui.dashboard.DashboardActivity

class AdminLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding
    private val repo = AdminRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            if (email.isBlank() || password.length < 6) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            repo.login(email, password) {
                it.onSuccess {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }.onFailure { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
