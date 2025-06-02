package com.example.dogapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dogapp.databinding.ActivityRegisterBinding
import com.example.dogapp.view.viewmodel.DogsViewModel
import androidx.activity.viewModels
import android.widget.ArrayAdapter

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: DogsViewModel by viewModels()

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

        setupAutoCompleteTextViews()

    }

    private fun setupAutoCompleteTextViews() {
        // Configurar razas
        viewModel.breeds.observe(this) { breeds ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                breeds
            )
            binding.actRaza.setAdapter(adapter)
            binding.actRaza.threshold = 2
        }

        // Configurar síntomas
        viewModel.symptoms.observe(this) { symptoms ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                symptoms
            )
            binding.actSintomas.setAdapter(adapter)
            binding.actSintomas.setOnClickListener {
                binding.actSintomas.showDropDown()
            }
        }
    }
}