package com.jslps.bimaseva.model.districtModel

import com.google.gson.annotations.SerializedName

data class DistrictMasterClass (
    @SerializedName("DistrictCode") val districtCode : Int,
    @SerializedName("DistrictName") val districtName : String,
    @SerializedName("DistrictName_H") val districtName_H : String
)