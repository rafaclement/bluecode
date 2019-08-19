package com.bluecoding.movieschallenge.network

import com.bluecoding.movieschallenge.network.pojos.Page
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieApiClient {

    @Headers("Content-Type: application/json")
    @GET("discover/movie")
    fun findMovies(@Query("api_key") api_key: String, @Query("primary_release_date.gte") from : String, @Query("primary_release_date.lte") to: String, @Query("page") page: Int): Call<Page>

}
