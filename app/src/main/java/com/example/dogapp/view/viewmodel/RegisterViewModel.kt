package com.example.dogapp.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dogapp.utils.mappers.toEntity
import com.example.dogapp.model.Cita
import com.example.dogapp.repository.CitaRepository
import com.example.dogapp.repository.DogsRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application, private val citaRepository: CitaRepository) : AndroidViewModel(application) {
    private val repository = DogsRepository(application)

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> get() = _breeds

    private val _symptoms = MutableLiveData<List<String>>()
    val symptoms: LiveData<List<String>> get() = _symptoms

    init {
        fetchBreeds()
        fetchSymptoms()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            try {
                _breeds.value = repository.getAllBreeds()
            } catch (e: Exception) {
                _breeds.value = emptyList()
            }
        }
    }
    private fun fetchSymptoms() {
        _symptoms.value = repository.getSymptoms()
    }

    fun saveCita(cita: Cita) {
        viewModelScope.launch {
            citaRepository.insertCita(cita.toEntity())
        }
    }

}