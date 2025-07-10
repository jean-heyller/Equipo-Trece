// HomeViewModel.kt
package com.example.dogapp.viewmodel

import androidx.lifecycle.*
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.model.Cita
import kotlinx.coroutines.launch

class HomeViewModel(
    private val citaRepository: CitaRepository,
) : ViewModel() {
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> = _citas

    fun cargarCitas() {
        viewModelScope.launch {
            val citaEntities = citaRepository.getCitas()
            val citas = citaEntities.map { entity ->
                Cita(
                    id = entity.id,
                    nombreMascota = entity.nombreMascota,
                    raza = entity.raza,
                    nombrePropietario = entity.nombrePropietario,
                    sintoma = entity.sintoma,
                    telefono = entity.telefono,
                    urlImagen = entity.imagenUrl
                )
            }
            _citas.value = citas
        }
    }
}