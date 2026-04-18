package com.tokli.userapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tokli.userapp.data.repository.UserRepository
import com.tokli.userapp.databinding.ActivityLoginBinding
import com.tokli.userapp.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val repo = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            if (email.isBlank() || password.length < 6) {
                Toast.makeText(this, "Enter valid credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            repo.login(email, password) {
                it.onSuccess {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }.onFailure { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
