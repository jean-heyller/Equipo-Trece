package com.example.dogapp.service

class ApiUtils {
    companion object{
        fun getApiDogService(): DogApiService {
            return RetrofitClient.getRetrofit().create(DogApiService::class.java)
        }
    }
}