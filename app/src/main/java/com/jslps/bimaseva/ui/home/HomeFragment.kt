package com.jslps.bimaseva.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.home.adapter.CardListAdapter
import com.jslps.bimaseva.Constant

class HomeFragment : BaseFragment(), HomeView, OnFragmentListItemSelectListener {
    private var homeRecyclerviewAdapter: CardListAdapter? = null
    override fun gotoScreen(fragmentID: Int, message: Any?) {
        mListener?.onFragmentInteraction(fragmentID, message as String)
    }

    override fun loadData(cardInitResponse: ArrayList<String>?) {

        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter =
                    CardListAdapter(cardInitResponse, getmActivity() as Activity)
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

    private var entryForm: Button? = null
    private var rootView: View? = null
    private var recycleview: RecyclerView? = null
    var presenter: HomePresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Dashboard"))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)
        entryForm = rootView?.findViewById(R.id.entryForm)
        presenter = HomePresenter(this, activity as Activity)
        recycleview?.layoutManager = Constant.gridLayout(activity, 2)
        entryForm?.visibility = View.VISIBLE
        entryForm?.setOnClickListener {
            mListener?.onFragmentInteraction(Constant.ENTRY_FORM_INSURANCE, "")
        }
        presenter?.resume()

        return rootView!!
    }

}

