package com.desafiolatam.weatherlatam

import com.desafiolatam.weatherlatam.data.local.WeatherDao
import com.desafiolatam.weatherlatam.data.local.WeatherEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UnitTestQuerySelect {

    @Test
    fun testInsertAndGetWeatherData() = runBlocking {
        val weather = WeatherEntity(
            id = 1,
            currentTemp = 25.0,
            maxTemp = 30.0,
            minTemp = 20.0,
            pressure = 1013.0,
            humidity = 50.0,
            windSpeed = 10.0,
            sunrise = 1680075600L,
            sunset = 1680122400L,
            cityName = "Test City"
        )

        // Insertar el registro
        WeatherDaoTest(weather)

        // Recuperar el registro
        val WeatherDao = weatherDao.getWeatherData().first()

        // Verificar que los datos coincidan
        assertEquals(1, weatherData.size)
        assertEquals(weather, weatherData[0])
    }
}