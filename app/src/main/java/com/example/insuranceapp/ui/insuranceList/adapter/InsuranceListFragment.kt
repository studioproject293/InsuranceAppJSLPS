package com.example.insuranceapp.ui.insuranceList.adapter

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

import com.example.insuranceapp.Constant
import com.example.insuranceapp.model.Master
import com.example.insuranceapp.ui.insuranceList.adapter.adapter.InsuranceListAdapter

class InsuranceListFragment : BaseFragment(), InsuranceView, OnFragmentListItemSelectListener {
    override fun loadData(cardInitResponse: ArrayList<Master>?) {
        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter = InsuranceListAdapter(cardInitResponse, getmActivity() as Activity,insuranceName!!)
            else
                homeRecyclerviewAdapter?.updateList(cardInitResponse)
            homeRecyclerviewAdapter?.setListner(presenter?.getListner())
            recycleview?.adapter = homeRecyclerviewAdapter
        } else {
            recycleview?.visibility = View.GONE
        }
    }

    override fun gotoScreen(fragmentID: Int, message: Any?) {
    }

    var homeRecyclerviewAdapter: InsuranceListAdapter? = null

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
    var presenter: InsurancePresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Dashboard"))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)
        presenter = InsurancePresenter(this, activity as Activity)
        recycleview?.layoutManager = Constant.getVerticalLayout(activity!!)
        presenter?.resume()
        return rootView!!
    }

    companion object {
        var insuranceName: String? = null
        fun getInstance(insuranceNamee: String): InsuranceListFragment {
            insuranceName = insuranceNamee
            return InsuranceListFragment()
        }
    }
}

