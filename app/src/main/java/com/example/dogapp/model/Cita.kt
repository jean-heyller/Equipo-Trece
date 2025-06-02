package com.example.dogapp.model

data class Cita(
    val id: Int,
    val nombreMascota: String,
    val sintoma: String,
    val urlImagen: String? = null // Opcional, para cargar imágenes
)
