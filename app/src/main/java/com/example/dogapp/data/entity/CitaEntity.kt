package com.example.dogapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class CitaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreMascota: String,
    val sintoma: String,
    val raza: String,
    val imagenUrl: String
)
