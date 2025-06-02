package com.example.dogapp.service

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedsResponse

    @GET("breed/{breed}/images/random")
    suspend fun getBreedImage(@Path("breed") breed: String): BreedImageResponse
}

data class BreedsResponse(val message: Map<String, List<String>>)
data class BreedImageResponse(val message: String)