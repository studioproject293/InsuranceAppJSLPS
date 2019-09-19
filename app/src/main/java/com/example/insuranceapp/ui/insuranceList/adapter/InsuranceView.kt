package com.example.insuranceapp.ui.insuranceList.adapter

import com.example.insuranceapp.base.BaseView
import com.example.insuranceapp.model.Master

interface InsuranceView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<Master>?)
}