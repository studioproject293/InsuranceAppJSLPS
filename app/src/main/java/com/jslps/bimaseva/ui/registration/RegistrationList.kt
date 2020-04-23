package com.jslps.bimaseva.ui.registration


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.cardview.widget.CardView
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.ui.BaseFragment

class RegistrationList : BaseFragment() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    var linearLayout4: LinearLayout? = null
    var linearLayout5: LinearLayout? = null
    var cardView1: CardView? = null
    var cardView2: CardView? = null
    var cardView3: CardView? = null
    var cardView4: CardView? = null
    var cardView5: CardView? = null
    var spinner: Spinner? = null
    val otherStrings = arrayOf("All Scheme", "PMSBY", "PMJJBY","PMJAY","Asset Insurance (Crop, Livestock, Others)")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.activity_claim_registration, container, false)
        linearLayout1 = rootView.findViewById(R.id.shgRegistration)
        linearLayout4 = rootView.findViewById(R.id.shgRegistration4)
        linearLayout2 = rootView.findViewById(R.id.shgFamilyregistration)
        linearLayout3 = rootView.findViewById(R.id.registrationothers)
        linearLayout5 = rootView.findViewById(R.id.registrationothers5)
        cardView1 = rootView.findViewById(R.id.card1)
        cardView2 = rootView.findViewById(R.id.card2)
        cardView3 = rootView.findViewById(R.id.card3)
        cardView4 = rootView.findViewById(R.id.card4)
        cardView5 = rootView.findViewById(R.id.card5)
        spinner = rootView.findViewById(R.id.sppiner_scheme)
        val scheme = resources.getStringArray(R.array.schemeArray)

        val adapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.spiner_row, otherStrings
            )
        }
        spinner?.adapter = adapter
        linearLayout1?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_SHG, "")
            } else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()

        }
        linearLayout2?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_FAMILY, "")
            } else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout3?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_OTHER, "")
            } else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout4?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.ASSERT_SHG_REGISTRATION, "")
            } else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout5?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                AppCache.getCache().schemeID = schemeId
                mListener?.onFragmentInteraction(Constant.ASSERT_REGISTRATION_OTHER, "")
            } else
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }

        spinner?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                    0 ->{
                        return
                    }
                    1 -> {
                        schemeId = "1"
                        cardView1?.visibility=View.VISIBLE
                        cardView2?.visibility=View.VISIBLE
                        cardView3?.visibility=View.VISIBLE
                        cardView4?.visibility=View.GONE
                        cardView5?.visibility=View.GONE
                    }
                    2 -> {
                        schemeId = "2"
                        cardView1?.visibility=View.VISIBLE
                        cardView2?.visibility=View.VISIBLE
                        cardView3?.visibility=View.VISIBLE
                        cardView4?.visibility=View.GONE
                        cardView5?.visibility=View.GONE

                    }
                    3 -> {
                        schemeId = "3"
                        cardView1?.visibility=View.VISIBLE
                        cardView2?.visibility=View.VISIBLE
                        cardView3?.visibility=View.VISIBLE
                        cardView4?.visibility=View.GONE
                        cardView5?.visibility=View.GONE

                    }
                    4 -> {
                        schemeId = "4"
                        cardView1?.visibility=View.GONE
                        cardView2?.visibility=View.GONE
                        cardView3?.visibility=View.GONE
                        cardView4?.visibility=View.VISIBLE
                        cardView5?.visibility=View.VISIBLE

                    }
                }
            }


        }


        return rootView
    }

    var schemeId: String? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Registration Type"))

    }
}
