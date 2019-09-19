package com.example.insuranceapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginService {
    @GET("TabletDownloadDataBCsakhi")
    fun getTabletDownloadDataBCsakhi(
        @Query("flag") flag: String, @Query("whr1") whr1: String , @Query("whr2") whr2: String): Call<String>

}