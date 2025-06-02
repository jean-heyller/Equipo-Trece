package com.example.dogapp.repository

import com.example.dogapp.data.dao.CitaDao
import com.example.dogapp.data.entity.CitaEntity

class CitaRepository(private val citaDao: CitaDao) {
    suspend fun getCitas() = citaDao.getAll()
    suspend fun insertCita(cita: CitaEntity) = citaDao.insert(cita)
}