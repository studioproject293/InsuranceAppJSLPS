package com.jslps.bimaseva.ui.reports

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.MasterX

import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.reports.adapter.SchemeListAdapterReport

class SchemeDetailsFragmentReport : BaseFragment(), SchemeDetailsViewReport, OnFragmentListItemSelectListener {
    var homeRecyclerviewAdapter: SchemeListAdapterReport? = null
    override fun gotoScreen(fragmentID: Int, message: Any?) {
        mListener?.onFragmentInteraction(fragmentID,message as List<*>)
    }

    override fun loadData(cardInitResponse: ArrayList<MasterX>) {

        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter = SchemeListAdapterReport(cardInitResponse,getmActivity() as Activity,
                    AppCache.getCache().insurancetype.toString()
                )
            else
                homeRecyclerviewAdapter?.updateList(cardInitResponse)
            homeRecyclerviewAdapter?.setListner(presenter?.getListner())
            recycleview?.adapter = homeRecyclerviewAdapter
        } else {
            recycleview?.visibility = View.GONE
        }
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun noInternet() {
    }

    override fun onListItemSelected(itemId: Int, data: Any) {
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    private var rootView: View? = null
    private var recycleview: RecyclerView? = null
    var presenter: SchemeDetailsPresenterReport? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, AppCache.getCache().insurancetype.toString()))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)
        presenter = SchemeDetailsPresenterReport(this, activity as Activity)
        recycleview?.layoutManager = Constant.gridLayout(activity,2)
        presenter?.resume()
        return rootView!!
    }
    companion object {
        var insuranceName: String? = null
        fun getInstance(): SchemeDetailsFragmentReport {
            return SchemeDetailsFragmentReport()
        }
    }
}
