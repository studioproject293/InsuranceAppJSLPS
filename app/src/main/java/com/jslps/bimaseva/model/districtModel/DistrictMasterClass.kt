package com.jslps.bimaseva.model.districtModel

import com.google.gson.annotations.SerializedName

data class DistrictMasterClass (
    @SerializedName("DistrictCode") val districtCode : String,
    @SerializedName("DistrictName") val districtName : String,
    @SerializedName("DistrictName_H") val DistrictName_H: String

)