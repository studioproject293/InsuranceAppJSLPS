package com.example.insuranceapp.ui.reports.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insuranceapp.R
import com.example.insuranceapp.listener.OnFragmentListItemSelectListener
import com.example.insuranceapp.model.MasterX

class SchemeListAdapterReport(items: ArrayList<MasterX>, activity: Context, insuranceName: String) :
    RecyclerView.Adapter<SchemeListAdapterReport.ViewHolder>() {

    var itemList: ArrayList<MasterX>? = items
    var context: Context? = activity
    var insuranceName: String = insuranceName
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SchemeListAdapterReport.ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.home_adapter_row, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text = itemList?.get(p1)?.Column2.toString() + itemList?.get(p1)?.Column1.toString() + ")"
        p0.view.setOnClickListener { mListner?.onListItemSelected(p1, insuranceName) }

    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var title: TextView = mView.findViewById(R.id.title)
        internal var view: View = mView

    }

    var mListner: OnFragmentListItemSelectListener? = null

    fun setListner(listner: OnFragmentListItemSelectListener?) {
        this.mListner = listner
    }

    fun updateList(cardList: ArrayList<MasterX>) {
        this.itemList = cardList
    }

}