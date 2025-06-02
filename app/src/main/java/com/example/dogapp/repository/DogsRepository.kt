package com.example.dogapp.repository

import android.content.Context
import com.example.dogapp.R
import com.example.dogapp.service.ApiUtils

class DogsRepository( val context: Context) {
    private val apiService = ApiUtils.getApiDogService()

    suspend fun getAllBreeds(): List<String> {
        return try {
            val response = apiService.getBreeds()
            response.message.keys.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getSymptoms(): List<String> {
        return context.resources.getStringArray(R.array.sintomas).toList()
    }
}