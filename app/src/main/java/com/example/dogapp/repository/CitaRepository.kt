package com.example.dogapp.repository

import com.example.dogapp.data.dao.CitaDao
import com.example.dogapp.data.entity.CitaEntity
import com.example.dogapp.model.Cita
import com.example.dogapp.model.toEntity

class CitaRepository(private val citaDao: CitaDao) {
    suspend fun getCitas() = citaDao.getAll()

    suspend fun obtenerCitaPorId(id: Int): CitaEntity {
        return citaDao.obtenerCitaPorId(id)
    }

    suspend fun eliminarCita(id: Int) = citaDao.delete(id)

    suspend fun insertCita(cita: CitaEntity) = citaDao.insert(cita)

    suspend fun insertarCita(cita: Cita) {
        val entity = cita.toEntity()
        citaDao.insertAll(entity)
    }
}