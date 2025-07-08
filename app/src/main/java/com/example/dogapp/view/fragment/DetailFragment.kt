package com.example.dogapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.data.AppDatabase
import com.example.dogapp.databinding.FragmentDetailBinding
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.utils.Constants.DELETE_DATE
import com.example.dogapp.utils.Constants.DELETE_MESSAGE
import com.example.dogapp.utils.Constants.NO
import com.example.dogapp.utils.Constants.YES
import com.example.dogapp.view.viewmodel.DetailViewModel


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val citaId = requireArguments().getInt("citaId")

        val db = AppDatabase.getDatabase(requireContext(), viewLifecycleOwner.lifecycleScope)
        val repository = CitaRepository(db.citaDao())
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(repository) as T
            }
        })[DetailViewModel::class.java]

        binding.toolbarCustom.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.cita.observe(viewLifecycleOwner) { cita ->
            binding.toolbarCustom.toolbarTitle.text = cita.nombreMascota
            binding.tvTurno.text = "#${cita.id}"
            binding.tvRaza.text = cita.raza
            binding.tvSintoma.text = cita.sintoma
            binding.tvPropietario.text = "Propietario: ${cita.nombrePropietario}"
            binding.tvTelefono.text = "Teléfono: ${cita.telefono}"
            Glide.with(requireContext())
                .load(cita?.urlImagen)
                .placeholder(R.drawable.dog)
                .into(binding.imgMascota)
        }

        viewModel.cargarCitaPorId(citaId)

        setupEliminarCita()

        binding.fabEdit.setOnClickListener {
            val citaId = requireArguments().getInt("citaId")
            val bundle = Bundle().apply {
                putInt("citaId", citaId)
            }

            val editFragment = EditDateFragment()
            editFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.contenedor_general, editFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun setupEliminarCita() {
        binding.fabDelete.setOnClickListener {
            android.app.AlertDialog.Builder(requireContext())
                .setTitle(DELETE_DATE)
                .setMessage(DELETE_MESSAGE)
                .setPositiveButton(YES) { dialog, _ ->
                    val citaId = requireArguments().getInt("citaId")
                    viewModel.eliminarCita(citaId)
                    viewModel.citaEliminada.observe(viewLifecycleOwner) { eliminada ->
                        if (eliminada == true) {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                            dialog.dismiss()
                        }
                    }

                }
                .setNegativeButton(NO) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}