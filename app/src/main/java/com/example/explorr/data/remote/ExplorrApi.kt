package com.example.explorr.data.remote

import retrofit2.http.GET


interface ExplorrApi {
    @GET("all")
suspend fun  getCountriesApi(
)

}