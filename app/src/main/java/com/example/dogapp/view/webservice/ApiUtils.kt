package com.example.dogapp.view.webservice

class ApiUtils {
    companion object{
        fun getApiDogService():ApiDogService{
            return RetrofitClient.getRetrofit().create(ApiDogService::class.java)
        }
    }
}