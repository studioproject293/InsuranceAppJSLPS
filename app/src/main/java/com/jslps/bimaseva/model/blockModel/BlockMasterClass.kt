package com.jslps.bimaseva.model.blockModel

import com.google.gson.annotations.SerializedName

data class BlockMasterClass (
    @SerializedName("DistrictCode") val districtCode : Int,
    @SerializedName("BlockCode") val blockCode : Int,
    @SerializedName("BlockName") val blockName : String,
    @SerializedName("BlockName_H") val blockName_H : String,
    @SerializedName("ClusterCode") val clusterCode : String,
    @SerializedName("ClusterName") val clusterName : String,
    @SerializedName("ClusterName_H") val clusterName_H : String,
    @SerializedName("VillageCode") val villageCode : String,
    @SerializedName("VillageName") val villageName : String,
    @SerializedName("VillageName_H") val villageName_H : String,
    @SerializedName("GroupCode") val groupCode : String,
    @SerializedName("Group_Name") val group_Name : String,
    @SerializedName("Group_Name_H") val group_Name_H : String
)