package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getAsteroidsInfo()
    }

    private fun getAsteroidsInfo() {
        NasaApi.retrofitAsteroidsService.getAsteroids(Constants.API_KEY).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val asteroidList = parseAsteroidsJsonResult(JSONObject(response.body()!!))
                Log.i("MainVM", asteroidList.toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: ${t.message}"
                t.message?.let { Log.i("MainVM", it) }
            }

        })

        NasaApi.retrofitPictureService.getPicture(Constants.API_KEY).enqueue(object: Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                response.body()?.let { Log.i("MainVM", it.url) }
                _response.value = response.body()?.url
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                t.message?.let { Log.i("MainVM", it) }
            }


        })
    }
}