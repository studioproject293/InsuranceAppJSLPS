package com.example.insuranceapp.ui.reports

import com.example.insuranceapp.base.BaseView
import com.example.insuranceapp.model.MasterX

interface SchemeDetailsViewReport : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<MasterX>)
}