package com.example.insuranceapp.ui.reports

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.insuranceapp.Constant
import com.example.insuranceapp.DialogUtil
import com.example.insuranceapp.base.BasePresenter
import com.example.insuranceapp.base.Presenter
import com.example.insuranceapp.cache.AppCache
import com.example.insuranceapp.listener.OnFragmentListItemSelectListener
import com.example.insuranceapp.model.LoginPojo
import com.example.insuranceapp.network.LoginService
import com.example.insuranceapp.network.ServiceUpdateListner
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.twidpay.beta.model.ApiRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class SchemeDetailsPresenterReport(view: SchemeDetailsViewReport, context: Activity) : BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {


    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: SchemeDetailsViewReport? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }


    override fun resume() {
        val schemedata = ArrayList<String>()
        schemedata.add("Registered (10)")
        schemedata.add("Under Process (10)")
        schemedata.add("Claim Settled (10)")
        schemedata.add("Rejected (10)")
        view?.loadData(schemedata)
    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {

    }

    override fun onFailureResponse(request: ApiRequest, data: Any) {
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {

    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }

}

