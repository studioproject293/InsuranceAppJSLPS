package com.example.insuranceapp.ui.scheme

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.insuranceapp.R
import com.example.insuranceapp.listener.OnFragmentListItemSelectListener
import com.example.insuranceapp.model.HeaderData
import com.example.insuranceapp.ui.BaseFragment

import com.jslps.aaganbariapp.Constant

class SchemeDetailsFragment : BaseFragment(), SchemeDetailsView, OnFragmentListItemSelectListener {
    var homeRecyclerviewAdapter: SchemeListAdapter? = null
    override fun gotoScreen(fragmentID: Int, message: Any?) {
    }

    override fun loadData(cardInitResponse: ArrayList<String>?) {

        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter = SchemeListAdapter(cardInitResponse,getmActivity() as Activity)
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
    var presenter: SchemeDetailsPresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Dashboard"))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)
        presenter = SchemeDetailsPresenter(this, activity as Activity)
        recycleview?.layoutManager = Constant.gridLayout(activity,2)
        presenter?.resume()
        return rootView!!
    }

}

