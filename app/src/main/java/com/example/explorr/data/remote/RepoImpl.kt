package com.example.explorr.data.remote

import android.util.Log
import com.example.explorr.data.CountryRepository
import com.example.explorr.data.domain.Country
import com.example.explorr.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(private val countriesApi: CountriesApi):
    CountryRepository {

    override fun getAllCountries(): Flow<Resource<List<Country>>> = flow{
        Log.d("countries", "count called")
        try {
            emit(Resource.Loading())
            val countries = countriesApi.getAllCountries().map { it.toCountry() }
            emit(Resource.Success(countries))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected Error Occurred"))

        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server please check your internet connection"))
        }
    }
}