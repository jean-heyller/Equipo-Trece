package com.example.dogapp.view.viewmodel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapp.R

class LoginViewModel : ViewModel() {
    private val _authResult = MutableLiveData<Boolean>()
    val authResult: LiveData<Boolean> = _authResult

    fun onBiometricSuccess() {
        _authResult.value = true
    }

    fun onBiometricError() {
        _authResult.value = false
    }
}