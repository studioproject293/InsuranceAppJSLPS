package com.jslps.bimaseva.ui.schemeAssert

import com.jslps.bimaseva.base.BaseView

interface SchemeDetailsAssertView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<String>?)
    fun showMessage(message:String)
}