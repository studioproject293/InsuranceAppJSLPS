package com.jslps.bimaseva.model.blockModel

import com.google.gson.annotations.SerializedName

data class BlockMasterClass (
    @SerializedName("DistrictCode") var districtCode : Int,
    @SerializedName("BlockCode") var blockCode : Int,
    @SerializedName("BlockName") var blockName : String,
    @SerializedName("BlockName_H") var blockName_H : String,
    @SerializedName("ClusterCode") var clusterCode : String,
    @SerializedName("ClusterName") var clusterName : String,
    @SerializedName("ClusterName_H") var clusterName_H : String,
    @SerializedName("VillageCode") var villageCode : String,
    @SerializedName("VillageName") var villageName : String,
    @SerializedName("VillageName_H") var villageName_H : String,
    @SerializedName("GroupCode") var groupCode : String,
    @SerializedName("Group_Name") var group_Name : String,
    @SerializedName("Group_Name_H") var group_Name_H : String,
    @SerializedName("BankId") var bankId : Int,
    @SerializedName("BankCode") var bankCode : String,
    @SerializedName("BankName") var bankName : String,
    @SerializedName("BankName_Hindi") var bankName_Hindi : String,
    @SerializedName("ActLenght") var actLenght : String,
    @SerializedName("BranchCode") var branchCode : String,
    @SerializedName("BranchName") var branchName : String,
    @SerializedName("BranchName_Hindi") var branchName_Hindi : String
)