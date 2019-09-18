package com.example.insuranceapp.ui.scheme

import com.example.insuranceapp.base.BaseView

interface SchemeDetailsView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<String>?)
}