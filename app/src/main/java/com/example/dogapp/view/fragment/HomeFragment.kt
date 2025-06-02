package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.databinding.FragmentHomeBinding
import com.example.dogapp.data.AppDatabase
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.service.RetrofitInstance
import com.example.dogapp.view.adapter.CitaAdapter
import com.example.dogapp.viewmodel.HomeViewModel
import com.example.dogapp.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CitaAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext(), viewLifecycleOwner.lifecycleScope)
        val citaRepository = CitaRepository(db.citaDao())
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    citaRepository,
                    RetrofitInstance.api
                ) as T
            }
        })[HomeViewModel::class.java]

        adapter = CitaAdapter { cita ->
            findNavController().navigate(
                R.id.action_homeFragment_to_detalleFragment,
                bundleOf("citaId" to cita.id)
            )
        }

        binding.rvCitas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitas.adapter = adapter

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newDateFragment)
        }

        viewModel.citas.observe(viewLifecycleOwner) { citas ->
            adapter.submitList(citas)
        }

        viewModel.cargarCitas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}