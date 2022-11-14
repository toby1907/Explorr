package com.example.explorr.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorr.data.CountryRepository
import com.example.explorr.data.domain.Country
import com.example.explorr.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor (
    private val savedStateHandle: SavedStateHandle,
    private val countryRepository: CountryRepository): ViewModel() {
    private val _stateFlow = MutableSharedFlow<UiState>()
    val stateFlow = _stateFlow.asSharedFlow()

    private val _countryListScreenState = mutableStateOf(CountryListScreenState())
    val countryListScreenState: State<CountryListScreenState> = _countryListScreenState


    private val _countryDetailScreenState = mutableStateOf(CountryDetailScreenState())
    val countryDetailScreenState: State<CountryDetailScreenState> = _countryDetailScreenState

    private var searchJob: Job? = null

    val filteredList = mutableListOf<Country>()

    // filter implementation

    private val _continentList = mutableStateListOf(
        Continent("Africa", isSelected = false),
        Continent("Americas", isSelected = false),
        Continent("Asia", isSelected = false),
        Continent("Europe", isSelected = false),
        Continent("Oceania", isSelected = false),
        Continent("Antarctic", isSelected = false),
    )

    val continentList: List<Continent> = _continentList

    private val _timeZonesList = mutableStateListOf(
        TimeZone("UTC",false),
        TimeZone("UTC+01:00",false),
        TimeZone("UTC+02:00",false),
        TimeZone("UTC+03:00",false),
        TimeZone("UTC+04:00",false),
        TimeZone("UTC+05:00",false),
    )

    val timeZoneList: List<TimeZone> = _timeZonesList




    init {
        viewModelScope.launch {
            onEvent(UiState.LightMode)

        }
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

    fun setContinentSelectedAtIndex(index: Int, isSelected: Boolean) {
        _continentList[index] = _continentList[index].copy(isSelected = isSelected)
    }

    fun setTimeZoneSelectedAtIndex(index: Int, isSelected: Boolean) {
        _timeZonesList[index] = _timeZonesList[index].copy(isSelected = isSelected)
    }

    private fun continentFilter(): List<String> {
        val selectedContinent = _continentList.filter { it.isSelected }
        return selectedContinent.map { it.name }
    }

    private fun timezoneFilter(): List<String> {
        val selectedTimezones = _timeZonesList.filter { it.isSelected }
        return selectedTimezones.map { it.timeZone }
    }


    fun applyFiltersToCountryList() {
        val newFilteredList = mutableListOf<Country>()
        val continentFilterList = continentFilter()
        val timezoneList = timezoneFilter()
        viewModelScope.launch {

            continentFilterList.forEach { continent ->
                val result =  filteredList.filter { it.region == continent }
                newFilteredList.addAll(result)
            }

            timezoneList.forEach { timezone ->
                val result =  filteredList.filter { it.timeZone == timezone }
                newFilteredList.addAll(result)
            }
            _countryListScreenState.value = _countryListScreenState.value.copy(countries = newFilteredList)
        }

    }

    fun resetCountryListFilter(){

        with(_continentList.iterator()) {
            forEach {
                it.isSelected = false
            }
        }

        with(_timeZonesList.iterator()) {
            forEach {
                it.isSelected = false
            }
        }

        _countryListScreenState.value = _countryListScreenState.value.copy(countries = filteredList)
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

    fun onEvent (state: UiState){
        when(state){
            is UiState.DarkMode -> {
                viewModelScope.launch {
                    _stateFlow.emit(
                        UiState.DarkMode
                    )
                }
            }
            is UiState.LightMode -> {
                viewModelScope.launch {
                    _stateFlow.emit(
                        UiState.LightMode
                    )
                }
            }
        }
    }



    sealed class UiState{
        object DarkMode: UiState()
        object LightMode: UiState()
    }
    data class Continent(
        val name: String,
        var isSelected: Boolean = false
    )
    data class TimeZone(
        val  timeZone: String,
        var isSelected: Boolean = false,
    )

    data class Page(val url: String)
}