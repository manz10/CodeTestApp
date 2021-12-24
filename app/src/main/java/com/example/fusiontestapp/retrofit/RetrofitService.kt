package com.example.fusiontestapp.retrofit

import com.example.fusiontestapp.model.EntriesAllDataModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("entries")
    fun getEntriesDataList(): Call<EntriesAllDataModel>

    companion object {
        val BASE_URL = "https://api.publicapis.org/"

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService{

            if(retrofitService == null){
                val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(RetrofitService::class.java)
            }

            return retrofitService!!
        }
    }
}