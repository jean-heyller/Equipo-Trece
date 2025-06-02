package com.example.dogapp.model

data class Cita(
    val id: Int? = null,
    val nombreMascota: String,
    val nombrePropietario: String,
    val raza: String,
    val telefono: String,
    val sintoma: String,
    val urlImagen: String? = null // Opcional, para cargar imágenes
)
