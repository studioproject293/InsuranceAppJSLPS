package com.example.insuranceapp.ui.reports

import com.example.insuranceapp.base.BaseView

interface SchemeDetailsViewReport : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<String>?)
}