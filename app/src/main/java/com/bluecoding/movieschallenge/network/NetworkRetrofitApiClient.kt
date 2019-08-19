package com.bluecoding.movieschallenge.network


import android.content.Context
import com.bluecoding.movieschallenge.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class NetworkRetrofitClient(context: Context) {


    val redditApi: MovieApiClient

    init {
        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()

        redditApi = retrofit.create(MovieApiClient::class.java)
    }

}