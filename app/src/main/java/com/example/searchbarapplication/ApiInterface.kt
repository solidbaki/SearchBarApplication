package com.example.searchbarapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET(value = "search")
    fun getData(): Call<List<IMDBDataItem>>
}