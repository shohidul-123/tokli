package com.tokli.userapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tokli.userapp.data.repository.UserRepository
import com.tokli.userapp.databinding.ActivityRegisterBinding
import com.tokli.userapp.ui.home.HomeActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val repo = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {
            val name = binding.inputName.text.toString().trim()
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            if (name.isBlank() || email.isBlank() || password.length < 6) {
                Toast.makeText(this, "Fill all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            repo.register(name, email, password) {
                it.onSuccess {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                }.onFailure { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
