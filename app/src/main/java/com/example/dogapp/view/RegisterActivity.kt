package com.example.dogapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dogapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    companion object {
        private const val TOOLBAR_TITLE = "Nueva Cita"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCustom.toolbarTitle.text = TOOLBAR_TITLE
        binding.toolbarCustom.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}