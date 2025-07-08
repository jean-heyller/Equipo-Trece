package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dogapp.R
import com.example.dogapp.data.AppDatabase
import com.example.dogapp.databinding.FragmentEditBinding
import com.example.dogapp.model.Cita
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.view.viewmodel.EditViewModel

class EditDateFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val citaId = requireArguments().getInt("citaId")
        val db = AppDatabase.getDatabase(requireContext(), viewLifecycleOwner.lifecycleScope)
        val repository = CitaRepository(db.citaDao())

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val application = requireActivity().application
                return EditViewModel(application, repository) as T
            }
        })[EditViewModel::class.java]

        viewModel.cargarCitaPorId(citaId)


        binding.toolbarCustom.toolbarTitle.text = "Editar Cita"
        binding.toolbarCustom.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        viewModel.cita.observe(viewLifecycleOwner) { cita ->
            binding.etNombreMascota.setText(cita.nombreMascota)
            binding.etRaza.setText(cita.raza)
            binding.etNombrePropietario.setText(cita.nombrePropietario)
            binding.etTelefono.setText(cita.telefono)
        }


        viewModel.actualizacionCompleta.observe(viewLifecycleOwner) { completada ->
            if (completada) {
                Toast.makeText(requireContext(), "Cita actualizada", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Error al actualizar la cita", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnGuardar.setOnClickListener {
            val citaActual = viewModel.cita.value
            if (citaActual != null) {
                val citaEditada = Cita(
                    id = citaId,
                    nombreMascota = binding.etNombreMascota.text.toString(),
                    raza = binding.etRaza.text.toString(),
                    sintoma = citaActual.sintoma,
                    nombrePropietario = binding.etNombrePropietario.text.toString(),
                    telefono = binding.etTelefono.text.toString(),
                    urlImagen = citaActual.urlImagen
                )

                viewModel.actualizarCita(citaEditada, citaActual.raza)
            } else {
                Toast.makeText(requireContext(), "Error: no se pudo cargar la cita", Toast.LENGTH_SHORT).show()
            }
        }

        setupAutoCompleteTextViews()
        setupInputListeners()
    }

    private fun setupInputListeners() {
        val editTexts = listOf(
            binding.etNombreMascota,
            binding.etNombrePropietario,
            binding.etTelefono,
            binding.etRaza
        )

        editTexts.forEach { editText ->
            editText.addTextChangedListener { validateForm() }
        }
        binding.etRaza.setOnItemClickListener { _, _, _, _ ->
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
            binding.etRaza.setAdapter(adapter)
            binding.etRaza.threshold = 2
        }
    }

    private fun validateForm() {
        val nombreMascota = binding.etNombreMascota.text.toString().trim()
        val raza = binding.etRaza.text.toString().trim()
        val nombrePropietario = binding.etNombrePropietario.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()

        val allFieldsFilled = nombreMascota.isNotEmpty() &&
                raza.isNotEmpty() &&
                nombrePropietario.isNotEmpty() &&
                telefono.isNotEmpty()

        binding.btnGuardar.isEnabled = allFieldsFilled

        if (allFieldsFilled) {
            binding.btnGuardar.setEnabled(true)
            binding.btnGuardar.setTypeface(null, android.graphics.Typeface.BOLD)
            binding.btnGuardar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            binding.btnGuardar.setEnabled(false)
            binding.btnGuardar.setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_gray))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}