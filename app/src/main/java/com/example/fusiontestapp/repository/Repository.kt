package com.example.fusiontestapp.repository

import com.example.fusiontestapp.retrofit.RetrofitService

class Repository constructor(private val retrofitService: RetrofitService) {

    fun getEntriesDataList() = retrofitService.getEntriesDataList()
}