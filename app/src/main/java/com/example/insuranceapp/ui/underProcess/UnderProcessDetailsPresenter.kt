package com.example.insuranceapp.ui.underProcess

import android.app.Activity
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


class UnderProcessDetailsPresenter(view: UnderProcessDetailsView, context: Activity) : BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {


    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: UnderProcessDetailsView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }


    override fun resume() {

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

    fun uploadUnderProcess(insuranceNameeee: Master?, encodedBase64: String?) {
        if (DialogUtil.isConnectionAvailable(context)) {
            this!!.context?.let { DialogUtil.displayProgress(it) }
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
            val uploadRegisterData = UploadRegisterData(
                insuranceNameeee?.Call_Id.toString(),
                insuranceNameeee?.CreatedBy.toString(),
                id,
               "",
                insuranceNameeee?.CreatedOn.toString(),
                "0",
                encodedBase64.toString(),
                "1",
                "",
                "",
                "",
                "",
                "",
                "2", ""
            )
            val data = "{" + "\"InsuranceImages\"" + " : [" + Gson().toJson(uploadRegisterData) + "] }"
            println("jdfjhjds$data")
            val changePhotoResponseModelCall =
                apiServices.uploadRegistedinsurance(data)
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        DialogUtil.stopProgressDisplay()
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
                             view?.showMessage("Insurnace Update Sucessfully")
                            } else {
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
                        context?.let { DialogUtil.displayProgress(it) }
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

