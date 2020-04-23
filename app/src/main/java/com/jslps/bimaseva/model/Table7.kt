package com.jslps.bimaseva.model

import com.google.gson.annotations.SerializedName

data class Table7 (
    @SerializedName("ClusterCode") val ClusterCode : String,
    @SerializedName("PGcode") val SHGCode : String,
    @SerializedName("PGname") val Pg_Name : String,
    @SerializedName("VillageCode") val VillageCode : String
)
