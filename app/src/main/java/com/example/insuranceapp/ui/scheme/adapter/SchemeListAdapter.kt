package com.example.insuranceapp.ui.scheme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insuranceapp.R
import com.example.insuranceapp.listener.OnFragmentListItemSelectListener

class SchemeListAdapter(items: ArrayList<String>, activity: Context) : RecyclerView.Adapter<SchemeListAdapter.ViewHolder>() {

    var itemList: ArrayList<String>? = items
    var context: Context? = activity

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SchemeListAdapter.ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.home_adapter_row, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text= itemList!![p1]


    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var title: TextView = mView.findViewById(R.id.title)
        internal var view: View = mView

    }

    var mListner: OnFragmentListItemSelectListener? = null

    fun setListner(listner: OnFragmentListItemSelectListener?) {
        this.mListner = listner
    }

    fun updateList(cardList: ArrayList<String>) {
        this.itemList = cardList
    }

}