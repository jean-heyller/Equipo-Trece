package com.example.dogapp.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dogapp.model.Cita
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.repository.DogsRepository
import kotlinx.coroutines.launch

class EditViewModel(application: Application, private val repository: CitaRepository) : AndroidViewModel(application) {

    private val dogsRepository = DogsRepository(application)

    private val _cita = MutableLiveData<Cita>()
    val cita: LiveData<Cita> get() = _cita

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> get() = _breeds


    init {
        fetchBreeds()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            try {
                _breeds.value = dogsRepository.getAllBreeds()
            } catch (e: Exception) {
                _breeds.value = emptyList()
                e.printStackTrace()
            }
        }
    }

    fun cargarCitaPorId(id: Int) {
        viewModelScope.launch {
            val resultado = repository.obtenerCitaPorId(id)
            val cita = Cita(
                id = resultado.id,
                nombreMascota = resultado.nombreMascota,
                raza = resultado.raza,
                nombrePropietario = resultado.nombrePropietario,
                sintoma = resultado.sintoma,
                telefono = resultado.telefono,
                urlImagen = resultado.imagenUrl
            )
            _cita.postValue(cita)
        }
    }

    fun actualizarCita(cita: Cita) {
        viewModelScope.launch {
            try {
                repository.insertarCita(cita)
            } catch (e: Exception) {
                Log.e("EditViewModel", "Error al actualizar la cita", e)
            }
        }
    }
}
