package com.example.explorr.data

import com.example.explorr.data.domain.Country
import com.example.explorr.util.Resource
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getAllCountries(): Flow<Resource<List<Country>>>
}