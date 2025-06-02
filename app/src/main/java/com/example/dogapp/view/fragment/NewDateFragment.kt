package com.example.dogapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dogapp.R
import com.example.dogapp.data.AppDatabase
import com.example.dogapp.databinding.FragmentNewDateBinding
import com.example.dogapp.model.Cita
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.view.viewmodel.RegisterViewModel

class NewDateFragment : Fragment() {

    private var _binding: FragmentNewDateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    companion object {
        private const val TOOLBAR_TITLE = "Nueva Cita"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = AppDatabase.getDatabase(requireContext(), viewLifecycleOwner.lifecycleScope)
        val citaRepository = CitaRepository(db.citaDao())

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(
                    requireActivity().application,
                    citaRepository
                ) as T
            }
        })[RegisterViewModel::class.java]

        binding.toolbarCustom.toolbarTitle.text = TOOLBAR_TITLE
        binding.toolbarCustom.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        setupAutoCompleteTextViews()
        setupInputListeners()
        setupSaveButton()
    }

    private fun setupInputListeners() {
        val editTexts = listOf(
            binding.etNombreMascota,
            binding.etNombrePropietario,
            binding.etTelefono
        )

        editTexts.forEach { editText ->
            editText.addTextChangedListener { validateForm() }
        }

        binding.actRaza.setOnItemClickListener { _, _, _, _ ->
            validateForm()
        }

        binding.actSintomas.setOnItemClickListener { _, _, _, _ ->
            validateForm()
        }
    }

    private fun setupAutoCompleteTextViews() {
        viewModel.breeds.observe(viewLifecycleOwner) { breeds ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                breeds
            )
            binding.actRaza.setAdapter(adapter)
            binding.actRaza.threshold = 2
        }

        viewModel.symptoms.observe(viewLifecycleOwner) { symptoms ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                symptoms
            )
            binding.actSintomas.setAdapter(adapter)
            binding.actSintomas.setOnClickListener {
                binding.actSintomas.showDropDown()
            }
        }
    }

    private fun setupSaveButton() {
        binding.btnGuardarCita.setOnClickListener {
            val sintomasSeleccionado = binding.actSintomas.text.toString().trim()

            if (sintomasSeleccionado.isEmpty()) {
                // Mostrar mensaje emergente
                android.app.AlertDialog.Builder(requireContext())
                    .setTitle("Atención")
                    .setMessage("Selecciona un síntoma")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                val cita = Cita(
                    nombreMascota = binding.etNombreMascota.text.toString().trim(),
                    nombrePropietario = binding.etNombrePropietario.text.toString().trim(),
                    raza = binding.actRaza.text.toString().trim(),
                    telefono = binding.etTelefono.text.toString().trim(),
                    sintoma = sintomasSeleccionado
                )
                viewModel.saveCita(cita)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun validateForm() {
        val nombreMascota = binding.etNombreMascota.text.toString().trim()
        val raza = binding.actRaza.text.toString().trim()
        val nombrePropietario = binding.etNombrePropietario.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()

        val allFieldsFilled = nombreMascota.isNotEmpty() &&
                raza.isNotEmpty() &&
                nombrePropietario.isNotEmpty() &&
                telefono.isNotEmpty()

        binding.btnGuardarCita.isEnabled = allFieldsFilled

        if (allFieldsFilled) {
            binding.btnGuardarCita.setEnabled(true)
            binding.btnGuardarCita.setTypeface(null, android.graphics.Typeface.BOLD)
            binding.btnGuardarCita.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_button))
        } else {
            binding.btnGuardarCita.setEnabled(false)
            binding.btnGuardarCita.setTypeface(null, android.graphics.Typeface.NORMAL)
            binding.btnGuardarCita.setBackgroundColor(android.graphics.Color.GRAY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}