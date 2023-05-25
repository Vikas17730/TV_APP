package com.example.pager2

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("getdashboards")
    fun getUrls(): Call<List<String?>>
}


