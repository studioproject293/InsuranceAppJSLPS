package com.jslps.bimaseva.model.blockModel

import com.google.gson.annotations.SerializedName
import com.jslps.bimaseva.model.districtModel.BlockMasterClass

data class BlockModelClass (
    @SerializedName("Master") val master : List<BlockMasterClass>)
