package com.example.explorr.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorr.data.CountryRepository
import com.example.explorr.data.domain.Country
import com.example.explorr.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor (private val countryRepository: CountryRepository): ViewModel() {

    private val _countryListScreenState = mutableStateOf(CountryListScreenState())
    val countryListScreenState: State<CountryListScreenState> = _countryListScreenState

    private val _countryDetailScreenState = mutableStateOf(CountryDetailScreenState())
    val countryDetailScreenState: State<CountryDetailScreenState> = _countryDetailScreenState

    private var searchJob: Job? = null

    val filteredList = mutableListOf<Country>()

    init {

        getCountry()
    }

    private fun getCountry() {
        countryRepository.getAllCountries().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _countryListScreenState.value = CountryListScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _countryListScreenState.value = CountryListScreenState(countries = result.data ?: emptyList())
                    result.data?.let { filteredList.addAll(it.toList()) }
                }
                is Resource.Error -> {
                    _countryListScreenState.value = CountryListScreenState(error = result.message ?: "Error Occurred")
                }
            }


        }.launchIn(viewModelScope)
    }

    fun setSelectedCountry(country: Country) {
        _countryDetailScreenState.value = CountryDetailScreenState(country = country)
    }

    fun onEvent(event: CountryListScreenEvent) {
        when(event) {
            is CountryListScreenEvent.OnSearchQueryChange -> {
                _countryListScreenState.value = _countryListScreenState.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getSearchList()
                }
            }
        }
    }


    private fun getSearchList(query: String = countryListScreenState.value.searchQuery) {
        viewModelScope.launch {

            val newCountryList: List<Country> =
                filteredList.filter {country ->
                    country.name.contains(query,ignoreCase = true,)
                }

            _countryListScreenState.value = _countryListScreenState.value.copy(countries = newCountryList)
        }

    }
    data class CountryDetailScreenState(
        val country: Country? = null
    )

    sealed class CountryListScreenEvent {
        data class OnSearchQueryChange(val query: String): CountryListScreenEvent()
    }

    data class CountryListScreenState(
        val countries: List<Country> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = "",
        val searchQuery: String = ""
    )
}