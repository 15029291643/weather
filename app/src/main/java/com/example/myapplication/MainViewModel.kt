package com.example.myapplication

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.bean.City
import com.example.myapplication.bean.DayWeather
import com.example.myapplication.bean.HourWeather
import com.example.myapplication.bean.NowWeather
import com.example.myapplication.util.CityUtils
import com.example.myapplication.util.WeatherUtils
import kotlin.math.pow

class MainViewModel : ViewModel() {
    val nowWeather = MutableLiveData<NowWeather>()
    val day3Weather = MutableLiveData<DayWeather>()
    val day7Weather = MutableLiveData<DayWeather>()
    val hour24Weather = MutableLiveData<HourWeather>()
    private var nearestCity: City? = null

    fun City.location() = "${longitude},${latitude}"

    fun nearestCity(location: Location? = null): City? {
        // 为空返回上一次结果，可能为空
        if (location == null) {
            return nearestCity
        }
        return nearestCity ?: CityUtils.cityList().minBy { city ->
            (city.longitude.toDouble() - location.longitude).pow(2) + (city.latitude.toDouble() - location.latitude).pow(
                2
            )
        }.also {
            nearestCity = it
        }
    }

    fun refreshWeather(city: City) {
        Thread {
            WeatherUtils.nowWeather(city.location()).let {
                nowWeather.postValue(it)
            }
        }.start()
        Thread {
            WeatherUtils.day7Weather(city.location()).let {
                day7Weather.postValue(it)
            }
        }.start()
        Thread {
            WeatherUtils.hour24Weather(city.location()).let {
                hour24Weather.postValue(it)
            }
        }.start()
    }
}