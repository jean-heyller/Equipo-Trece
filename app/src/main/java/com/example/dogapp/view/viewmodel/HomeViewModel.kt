// HomeViewModel.kt
package com.example.dogapp.viewmodel

import androidx.lifecycle.*
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.model.Cita
import com.example.dogapp.service.DogApiService
import kotlinx.coroutines.launch

class HomeViewModel(
    private val citaRepository: CitaRepository,
    private val dogApiService: DogApiService
) : ViewModel() {
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> = _citas

    fun cargarCitas() {
        viewModelScope.launch {
            val citaEntities = citaRepository.getCitas()
            // Agrega este log para ver cuántas citas hay
            println("Citas en BD: ${citaEntities.size}")
            val citas = citaEntities.map { entity ->
                val url = if (entity.imagenUrl.isNullOrEmpty()) {
                    try {
                        dogApiService.getBreedImage(entity.raza).message
                    } catch (e: Exception) {
                        "" // O un placeholder si falla
                    }
                } else {
                    entity.imagenUrl
                }
                Cita(
                    id = entity.id,
                    nombreMascota = entity.nombreMascota,
                    sintoma = entity.sintoma,
                    urlImagen = url
                )
            }
            println("Citas mapeadas: ${citas.size}")
            _citas.value = citas
        }
    }
}