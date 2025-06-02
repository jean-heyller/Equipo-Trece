package com.example.dogapp.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.view.repository.DogsRepository
import kotlinx.coroutines.launch

class DogsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DogsRepository(application)

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> get() = _breeds

    private val _symptoms = MutableLiveData<List<String>>()
    val symptoms: LiveData<List<String>> get() = _symptoms

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> get() = _progressState

    init {
        fetchBreeds()
        fetchSymptoms()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            _progressState.value = true
            try {
                _breeds.value = repository.getAllBreeds()
                _progressState.value = false
            } catch (e: Exception) {
                _breeds.value = emptyList()
                _progressState.value = false
            }
        }
    }
    private fun fetchSymptoms() {
        _symptoms.value = repository.getSymptoms()
    }

}