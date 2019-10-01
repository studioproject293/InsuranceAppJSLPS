package com.example.insuranceapp.ui.insuranceList

import android.app.Activity
import com.chootdev.csnackbar.Snackbar
import com.chootdev.csnackbar.Type
import com.example.insuranceapp.Constant
import com.example.insuranceapp.DialogUtil
import com.example.insuranceapp.base.BasePresenter
import com.example.insuranceapp.base.Presenter
import com.example.insuranceapp.listener.OnFragmentListItemSelectListener
import com.example.insuranceapp.model.Master
import com.example.insuranceapp.model.UploadRegisterData
import com.example.insuranceapp.network.ServiceUpdateListner
import com.example.insuranceapp.network.UploadRegisterDocument
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.twidpay.beta.model.ApiRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class InsurancePresenter(view: InsuranceView, context: Activity) : BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {
        view?.gotoScreen(Constant.INSURANCE_DETAILS_FRAGMENT, data)
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: InsuranceView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }


    override fun resume() {
        val master = getAppCache().loginPojo?.Master as ArrayList<Master>
        view?.loadData(master)
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

    fun uploadRegisterDocument(insuranceNameeee: Master?, encodedBase64: String?) {

        if (DialogUtil.isConnectionAvailable(context)) {
            DialogUtil.displayProgress(context)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(120, TimeUnit.SECONDS)
            builder.readTimeout(120, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
                ScalarsConverterFactory.create()
            ).client(client).build()
            val apiServices = retrofit.create(UploadRegisterDocument::class.java)
            val id = UUID.randomUUID().toString()
            val uploadRegisterData = UploadRegisterData(insuranceNameeee?.Call_Id.toString(),
                insuranceNameeee?.CreatedBy.toString(),id, encodedBase64.toString(),
                insuranceNameeee?.CreatedOn.toString(),"1", encodedBase64.toString(),"1","",encodedBase64.toString(),"1","", 10.0F,
            "3","hello")
            val data = "{" + "\"InsuranceImages\"" + " : [" + Gson().toJson(uploadRegisterData) + "] }"
            println("jdfjhjds$data")
            val changePhotoResponseModelCall =
                apiServices.uploadRegistedinsurance(data)
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        DialogUtil.displayProgress(context)
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        var jsonObj: JSONObject? = null
                        try {
                            jsonObj = JSONObject(result.toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        try {
                            val categoryObject = jsonObj?.getJSONArray("Table")
                            val jsonObject = categoryObject?.getJSONObject(0)
                            val Result = jsonObject?.getString("RetValue")
                            if (Result.equals("1", ignoreCase = true)) {

                            }  else {
                                /* Snackbar.with(getActivity(), null)
                                     .type(Type.ERROR)
                                     .message("Please try again")
                                     .duration(Duration.SHORT)
                                     .fillParent(true)
                                     .textAlign(Align.CENTER)
                                     .show()*/
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        DialogUtil.displayProgress(context)
                        view?.showMessage(response.message())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    view?.showMessage(t.localizedMessage)
                }
            })
        } else {
            view?.noInternet()
        }
    }
}

