package com.desafiolatam.weatherlatam

import com.desafiolatam.weatherlatam.extension.toCelsius
import com.desafiolatam.weatherlatam.extension.toFahrenheit
import junit.framework.Assert.assertEquals

import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTestImoperialToMetrics {

    @Test
    fun celsiusToFahrenheit() {
        val celsius = 0.0
        val expected = 32.0
        val result = celsius.toFahrenheit()
        assertEquals(expected,result)

    }

    @Test
    fun fahrenheitToCelsius(){
        val fahrenheit = 32.0
        val expected = 0.0
        val result = fahrenheit.toCelsius()
        assertEquals(expected,result)
    }

    @Test
    fun celsiusToFahrenheitCold(){
        val celsius = -0.12
        val expected = 31.78
        val result = celsius.toFahrenheit()
        assertEquals(expected,result,0.01)
    }

}