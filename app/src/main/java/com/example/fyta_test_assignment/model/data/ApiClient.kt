package com.emedinaa.kotlinmvvm.data

import com.example.fyta_test_assignment.model.response.search.SearchResult
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * @author Eduardo Medina
 */
object ApiClient {

    //https://obscure-earth-55790.herokuapp.com/api/museums
    private const val API_BASE_URL = "https://my-api.plantnet.org/v2/"

    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java
        )

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServicesApiInterface {
        @Multipart
        @POST("identify/all?api-key=2b10txEbyt4ZY82vbnlahhFRPe")
        fun searchByImage(@Part images: ArrayList<MultipartBody.Part>): Call<SearchResult>



    }
}