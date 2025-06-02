package com.example.dogapp.view.webservice

import com.example.dogapp.view.model.BreedResponse
import com.example.dogapp.view.utils.Constants.ALL_BREEDS_ENDPOINT
import retrofit2.http.GET

interface ApiDogService {
    @GET(ALL_BREEDS_ENDPOINT)
    suspend fun getAllBreeds(): BreedResponse
}