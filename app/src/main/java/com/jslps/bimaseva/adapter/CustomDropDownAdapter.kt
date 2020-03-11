package com.jslps.bimaseva.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass

class CustomDropDownAdapter(val context: Context, val dataType: String, var listItemsTxt: List<*>) :
    BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.spiner_row_new, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.

        val params = view.layoutParams
        params.height = 60
        view.layoutParams = params
        if (dataType == "district") {
            val districtMasterClass = listItemsTxt as ArrayList<DistrictMasterClass>
            vh.label.text = districtMasterClass.get(position).districtName
        } else if (dataType == "block") {
            val districtMasterClass = listItemsTxt as ArrayList<BlockMasterClass>
            vh.label.text = districtMasterClass.get(position).blockName
        }else if (dataType == "cluster") {
            val districtMasterClass = listItemsTxt as ArrayList<BlockMasterClass>
            vh.label.text = districtMasterClass.get(position).clusterName
        }else if (dataType == "village") {
            val districtMasterClass = listItemsTxt as ArrayList<BlockMasterClass>
            vh.label.text = districtMasterClass.get(position).villageName
        }else if (dataType == "shg") {
            val districtMasterClass = listItemsTxt as ArrayList<BlockMasterClass>
            vh.label.text = districtMasterClass.get(position).group_Name
        }

        return view
    }

    override fun getItem(position: Int): Any? {
        return if (listItemsTxt == null) null else listItemsTxt.get(position)

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView

        init {
            this.label = row?.findViewById(R.id.text) as TextView
        }
    }


}