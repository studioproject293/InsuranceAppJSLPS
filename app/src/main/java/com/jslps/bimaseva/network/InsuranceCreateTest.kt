package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface InsuranceCreateTest {
    @GET("CallCentertest")
    fun createInsurance(
        @Query("jsonstr") flag: String): Call<String>
}