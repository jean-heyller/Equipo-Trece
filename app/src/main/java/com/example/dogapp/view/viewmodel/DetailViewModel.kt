package com.example.dogapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.model.Cita
import com.example.dogapp.repository.CitaRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CitaRepository) : ViewModel() {

    private val _cita = MutableLiveData<Cita>()
    val cita: LiveData<Cita> = _cita
    private val _citaEliminada= MutableLiveData<Boolean>()
    val citaEliminada: LiveData<Boolean> get() = _citaEliminada

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

    fun eliminarCita(id: Int) {
        viewModelScope.launch {
            try {
                repository.eliminarCita(id)
                _citaEliminada.postValue(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
