package com.jslps.bimaseva.ui.reports

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.MasterX
import com.jslps.bimaseva.model.ReportModel
import com.jslps.bimaseva.network.LoginService
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jslps.bimaseva.model.Master
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
    override fun onListItemSelected(itemId: Int, data: Any) {}

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    var view: SchemeDetailsViewReport? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }

    override fun resume(insuranceName: List<Master>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun resume() {
        DialogUtil.displayProgress(context!!)
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
        val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi("reportreg", "0", getAppCache().insurancetype!!,
            "")
        changePhotoResponseModelCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                DialogUtil.stopProgressDisplay()
                val gson = Gson()
                Log.v("Response prof :", "hgfgfrhgs" + response.body())

                val fullResponse = response.body()
                val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                val result = XmlString?.replace(("</string>").toRegex(), "")
                print("fhrjfghf" + result)
                val mStudentObject1 = gson.fromJson(result, ReportModel::class.java)
                for (item in mStudentObject1.Master) {
                    mStudentObject1.Master.get(index = 0).Column2="Registered ("
                    mStudentObject1.Master.get(index = 1).Column2="Under Process ("
                    mStudentObject1.Master.get(index = 2).Column2="Claim Settled ("
                    mStudentObject1.Master.get(index = 3).Column2="Rejected ("
                }
                System.out.println("vvh" + gson.toJson(mStudentObject1))
                view?.loadData(mStudentObject1.Master as ArrayList<MasterX>)
               // view?.gotoScreen(Constant.INSURANCE_LIST_FRAGMENT, mStudentObject1.Master)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                DialogUtil.stopProgressDisplay()
                val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                toast.show()
            }
        })
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

