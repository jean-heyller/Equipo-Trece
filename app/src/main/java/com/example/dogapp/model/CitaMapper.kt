package com.example.dogapp.model

import com.example.dogapp.data.entity.CitaEntity

fun Cita.toEntity(): CitaEntity {
    return CitaEntity(
        id = this.id ?: 0, // Room usa 0 como valor por defecto si es autogenerado
        nombreMascota = this.nombreMascota,
        nombrePropietario = this.nombrePropietario,
        raza = this.raza,
        telefono = this.telefono,
        sintoma = this.sintoma,
        imagenUrl = this.urlImagen.orEmpty()
    )
}