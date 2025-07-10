package com.example.dogapp.service

import com.example.dogapp.model.BreedImageResponse
import com.example.dogapp.model.BreedResponse
import com.example.dogapp.utils.Constants.ALL_BREEDS_ENDPOINT
import com.example.dogapp.utils.Constants.BREED_IMAGE_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET(ALL_BREEDS_ENDPOINT)
    suspend fun getBreeds(): BreedResponse

    @GET(BREED_IMAGE_ENDPOINT)
    suspend fun getBreedImage(@Path("breed") breed: String): BreedImageResponse
}