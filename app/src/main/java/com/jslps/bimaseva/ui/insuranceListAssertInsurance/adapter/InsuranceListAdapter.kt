package com.jslps.bimaseva.ui.insuranceListAssertInsurance.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.Master


class InsuranceListAdapter(items: ArrayList<Master>, activity: Context, insuranceName: String) :
    RecyclerView.Adapter<InsuranceListAdapter.ViewHolder>() {

    var itemList: ArrayList<Master>? = items
    var context: Context? = activity
    var insuranceNamee: String = insuranceName
    private var selectedPosition = -1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.row_item_insurace_list, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val masterModel = itemList?.get(p1)
        p0.nomineeName.text = masterModel?.name
        p0.block.text = masterModel?.blockname
        p0.village.text = masterModel?.villagename
        p0.contactNo.text = masterModel?.phno_ofNominee
        p0.bankbranch.text = masterModel?.branchName
        p0.nameOfInsurance.text =  "Asset Insurance"
        p0.view.setOnClickListener {
            if (masterModel != null) {
                mListner?.onListItemSelected(p1, masterModel)
            }
        }
        /*if (selectedPosition != -1) {
            if (selectedPosition == p1) {
                p0.buttonLayout.setVisibility(View.VISIBLE);
                p0.downArrow.setVisibility(View.GONE);
                p0.upArrow.setVisibility(View.VISIBLE);
            } else {
                p0.upArrow.setVisibility(View.GONE);
                p0.downArrow.setVisibility(View.VISIBLE);
                p0.buttonLayout.setVisibility(View.GONE);

            }
        }*/
        /*p0.downArrow.setOnClickListener(View.OnClickListener {
            selectedPosition = p0.getAdapterPosition()
            notifyItemChanged(selectedPosition)
            p0.buttonLayout.setVisibility(View.GONE)
            p0.downArrow.setVisibility(View.GONE)
            p0.upArrow.setVisibility(View.VISIBLE)
            notifyItemRangeChanged(0, itemCount)
        })
        p0.upArrow.setOnClickListener(View.OnClickListener {
            //selectedPosition = holder.getAdapterPosition();
            // notifyItemChanged(selectedPosition);
            p0.buttonLayout.setVisibility(View.GONE)
            p0.downArrow.setVisibility(View.VISIBLE)
            p0.upArrow.setVisibility(View.GONE)
            //notifyItemRangeChanged(0, getItemCount());
        })*/

    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var nameOfInsurance: TextView = mView.findViewById(R.id.insuranceName)
        var nomineeName: TextView = mView.findViewById(R.id.nomineeInsurance)
        var contactNo: TextView = mView.findViewById(R.id.contactNo)
        var block: TextView = mView.findViewById(R.id.block)
        var village: TextView = mView.findViewById(R.id.village)
        /*var downArrow: ImageView = mView.findViewById(R.id.moreInfoDown)
        var upArrow: ImageView = mView.findViewById(R.id.moreInfoUp)*/
        var document: ImageView = mView.findViewById(R.id.doucment)
        var actionButton: Button = mView.findViewById(R.id.actionButton)
        var uploadDocument: Button = mView.findViewById(R.id.uploadDocument)
        var buttonLayout: LinearLayout = mView.findViewById(R.id.buttonLayout)
        var bankbranch: TextView = mView.findViewById(R.id.bankbranch)
        internal var view: View = mView

    }

    var mListner: OnFragmentListItemSelectListener? = null

    fun setListner(listner: OnFragmentListItemSelectListener?) {
        this.mListner = listner
    }

    fun updateList(cardList: ArrayList<Master>) {
        this.itemList = cardList
    }

}