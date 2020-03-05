package com.jslps.bimaseva.model.welcomePageReports

import com.google.gson.annotations.SerializedName

data class BaseClassReportsWelcomwPage(
    @SerializedName("Insurance") val insurance : String,
    @SerializedName("listdetails") val listdetails : List<Listdetails>
)