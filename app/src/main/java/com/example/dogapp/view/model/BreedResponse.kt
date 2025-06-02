package com.example.dogapp.view.model

data class BreedResponse(
    val message: Map<String, List<String>>,
    val status: String
)