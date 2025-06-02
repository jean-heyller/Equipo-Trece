package com.example.dogapp.repository

import com.example.dogapp.data.dao.CitaDao
import com.example.dogapp.data.entity.CitaEntity
import com.example.dogapp.service.RetrofitInstance

class CitaRepository(private val citaDao: CitaDao) {
    suspend fun getCitas() = citaDao.getAll()
    suspend fun insertCita(cita: CitaEntity) = citaDao.insert(cita)
    suspend fun getBreeds() = RetrofitInstance.api.getBreeds()
    suspend fun getBreedImage(breed: String) = RetrofitInstance.api.getBreedImage(breed)
}