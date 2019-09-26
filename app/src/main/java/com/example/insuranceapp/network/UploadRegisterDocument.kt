package com.example.insuranceapp.network


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UploadRegisterDocument {
    @GET("InsuranceImages")
    fun uploadRegistedinsurance(@Query("sData") flag: String): Call<String>
}