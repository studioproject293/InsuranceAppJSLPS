package com.jslps.bimaseva.ui.insuranceListAssertInsurance

import com.jslps.bimaseva.base.BaseView
import com.jslps.bimaseva.model.Master

interface InsuranceAssertView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<Master>?)
    fun showMessage(message: Any?)
}