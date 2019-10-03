package com.example.insuranceapp.ui.scheme

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


class SchemeDetailsPresenter(view: SchemeDetailsView, context: Activity) : BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {

        val gson = GsonBuilder().setLenient().create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        //comment in live build and uncomment in uat
        builder.interceptors().add(interceptor)
        builder.connectTimeout(180, TimeUnit.SECONDS)
        builder.readTimeout(180, TimeUnit.SECONDS)
        val client = builder.build()
        val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
            ScalarsConverterFactory.create()
        ).client(client).build()
        val apiServices = retrofit.create(LoginService::class.java)
        when (itemId) {
            0 -> {
                AppCache.getCache().insuranceStep="Registered"
                DialogUtil.displayProgress(this!!.context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi("regproces", "0", " ")
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())

                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        print("fhrjfghf" + result)
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)

                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo

                        view?.gotoScreen(Constant.INSURANCE_LIST_FRAGMENT, mStudentObject1.Master)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            1 -> {
                AppCache.getCache().insuranceStep="Under Process"
                DialogUtil.displayProgress(this!!.context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi("underproces", "0", " ")
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())

                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        print("fhrjfghf" + result)
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                        view?.gotoScreen(Constant.INSURANCE_LIST_FRAGMENT, mStudentObject1.Master)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            2 -> {
                AppCache.getCache().insuranceStep="Claim Settled"
                DialogUtil.displayProgress(this!!.context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi("cs", "0", " ")
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        DialogUtil.stopProgressDisplay()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        print("fhrjfghf" + result)
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                        view?.gotoScreen(Constant.INSURANCE_LIST_FRAGMENT, mStudentObject1.Master)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            3 -> {
                AppCache.getCache().insuranceStep="Rejected"
                DialogUtil.displayProgress(this!!.context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi("rej", "2", " ")
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        DialogUtil.stopProgressDisplay()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        print("fhrjfghf" + result)
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                        view?.gotoScreen(Constant.INSURANCE_LIST_FRAGMENT, mStudentObject1.Master)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            else -> AppCache.getCache().insuranceStep="Total Claim"
        }

    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: SchemeDetailsView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }


    override fun resume() {
        val schemedata = ArrayList<String>()
        schemedata.add("Registered")
        schemedata.add("Under Process")
        schemedata.add("Claim Settled")
        schemedata.add("Rejected")
        schemedata.add("Total Claim")
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

