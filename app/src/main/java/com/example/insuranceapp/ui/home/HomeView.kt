package com.example.insuranceapp.ui.home

import com.example.insuranceapp.base.BaseView

interface HomeView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<String>?)
}