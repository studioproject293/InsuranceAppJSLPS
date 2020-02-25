package com.jslps.bimaseva.ui.home

import android.app.Activity
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.jslps.bimaseva.Constant
import com.twidpay.beta.model.ApiRequest


class HomePresenter(view: HomeView, context: Activity) : BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {

        getAppCache().insurancetype = data.toString()
        if (data.toString().equals("PMSBY"))
            getAppCache().insuranceStepSend = "1"
        else if (data.toString().equals("PMJJY"))
            getAppCache().insuranceStepSend = "2"
        else if (data.toString().equals("PMJAY"))
            getAppCache().insuranceStepSend = "3"
        else if (data.toString().equals("ASSET Insurance"))
            getAppCache().insuranceStepSend = "4"
        view?.gotoScreen(Constant.SCHEME_DETAILS_FRAGMENT, data)
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
        view?.loadData(null)
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {
    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }


}

