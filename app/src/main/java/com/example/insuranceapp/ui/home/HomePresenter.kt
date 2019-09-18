package com.example.insuranceapp.ui.home

import android.app.Activity
import android.widget.Toast
import com.example.insuranceapp.base.BasePresenter
import com.example.insuranceapp.base.Presenter
import com.example.insuranceapp.listener.OnFragmentListItemSelectListener
import com.example.insuranceapp.network.ServiceUpdateListner
import com.jslps.aaganbariapp.Constant
import com.twidpay.beta.model.ApiRequest


class HomePresenter(view: HomeView, context: Activity) : BasePresenter, Presenter(), OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {
     Toast.makeText(context,"Item Clicked",Toast.LENGTH_SHORT).show()
        view?.gotoScreen(Constant.SCHEME_DETAILS_FRAGMENT,"")
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: HomeView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }


    override fun resume() {
        val schemedata = ArrayList<String>()
        schemedata.add("PMSBY")
        schemedata.add("PMJJY")
        schemedata.add("PMJAY")
        schemedata.add("ASSET Insurance")
        view?.loadData(schemedata)
    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {

    }

    override fun onFailureResponse(request: ApiRequest, data: Any) {
        view?.hideProgress()
        /*if (chkIntenrnetIssue(data.message))
            view?.noInternet()*/
        view?.loadData(null)
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {

    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }

    fun getCardList() {
        /* val otpRequest = UserDetailRequest()
         otpRequest.unique_id = Utils.getDeviceIMEI(context as Activity)
         otpRequest.app_version = Utils.getAppVersion(context as Activity)
         otpRequest.os = Utils.getDeviceOS()
         otpRequest.version = Utils.getDeviceSdk()
         otpRequest.customer_id = getPref(context as Activity).getUserToken()!!.customer_id
         otpRequest.auth_token = getPref(context as Activity).getUserToken()!!.token
         val apiRequest = ApiRequest()
         apiRequest.context = context
         apiRequest.apiRequestData = otpRequest
         apiRequest.requestType = NetworkService.REQUEST_CARD_INIT
         view?.showProgress()
         ServiceUpdateListner.getInstance(context as Activity).passData(apiRequest)*/
    }
}

