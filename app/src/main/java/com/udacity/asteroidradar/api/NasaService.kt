package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofitAsteroids = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

private val retrofitPicture = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidsRequest {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("api_key") apiKey: String
    ): Call<String>
}
interface PictureRequest {
    @GET("planetary/apod")
    fun getPicture(
        @Query("api_key") apiKey: String
    ): Call<PictureOfDay>
}

object NasaApi {
    val retrofitAsteroidsService : AsteroidsRequest by lazy {
        retrofitAsteroids.create(AsteroidsRequest::class.java)
    }

    val retrofitPictureService : PictureRequest by lazy {
        retrofitPicture.create(PictureRequest::class.java)
    }
}