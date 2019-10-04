package com.example.insuranceapp.ui.claimSetteled

import com.example.insuranceapp.base.BaseView
import com.example.insuranceapp.model.Master

interface ClaimSetteledDetailsView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<Master>?)
    fun showMessage(message: Any?)
}