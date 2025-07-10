package com.example.dogapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class CitaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nombreMascota: String,
    val nombrePropietario: String,
    val sintoma: String,
    val raza: String,
    val telefono: String,
    val imagenUrl: String
)
