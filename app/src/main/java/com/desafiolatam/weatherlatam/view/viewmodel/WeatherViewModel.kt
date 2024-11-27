package com.desafiolatam.weatherlatam.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiolatam.weatherlatam.data.WeatherRepositoryImp
import com.desafiolatam.weatherlatam.model.WeatherDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepositoryImp) : ViewModel() {

    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    // LiveData para el nombre de la ciudad
    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> get() = _cityName

    suspend fun insertData() {
        viewModelScope.launch(dispatcherIO) {
            repository.getWeatherData().collectLatest {
                it?.let { list ->
                    if (list.isEmpty()) repository.insertData(datosIniciales())
                }
            }
        }
    }

    suspend fun getWeather() = repository.getWeatherData().stateIn(viewModelScope)

    suspend fun getWeatherDataById(id: Int) = repository.getWeatherDataById(id).stateIn(viewModelScope)

    suspend fun saveCityName(cityName: String,  value: Int) {
        viewModelScope.launch(dispatcherIO) {
           //repository.clearAll()
            // Obtén el flujo
            val dtoFlow = repository.getWeatherDataById(value)

            dtoFlow.onEmpty {
                // Inserta un dato inicial si el flujo está vacío
                val initialData = datosIniciales(cityName)
                repository.insertData(initialData)
                _cityName.postValue(cityName)
            }.collectLatest { dto ->
                val updatedDto = dto?.copy(cityName = cityName) ?: datosIniciales(cityName)
                repository.insertData(updatedDto)
                _cityName.postValue(cityName) // Asegura que el cambio se notifique
            }
        }
    }


    private fun datosIniciales(cityName: String? = null): WeatherDto = WeatherDto(
        id = 0,
        currentTemp = 20.0,
        maxTemp = 26.0,
        minTemp = 11.0,
        pressure = 1014.2,
        humidity = 63.0,
        windSpeed = 13.0,
        sunrise = 1661834187,
        sunset = 1681882248,
        cityName = cityName ?: "Santiago, CLP",
    )
}

