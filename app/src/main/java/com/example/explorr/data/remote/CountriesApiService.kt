package com.example.explorr.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
/*
private const val BASE_URL = "https://restcountries.com/v3.1/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
var service: CountriesApi= retrofit.create(CountriesApi::class.java)
*/
  interface CountriesApi {

      @GET("all")
      suspend fun getAllCountries(): List<CountryDTO>
  }


