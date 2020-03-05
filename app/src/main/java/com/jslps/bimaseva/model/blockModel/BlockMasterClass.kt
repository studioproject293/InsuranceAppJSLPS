package com.jslps.bimaseva.model.districtModel

import com.google.gson.annotations.SerializedName

data class BlockMasterClass (
    @SerializedName("DistrictCode") val districtCode : Int,
    @SerializedName("BlockCode") val blockCode : Int,
    @SerializedName("BlockName") val blockName : String,
    @SerializedName("BlockName_H") val blockName_H : String
)