package com.jslps.bimaseva.ui.registration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.ui.BaseFragment

class RegistrationList : BaseFragment() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.activity_claim_registration, container, false)
        linearLayout1 = rootView.findViewById(R.id.shgRegistration)
        linearLayout2 = rootView.findViewById(R.id.shgFamilyregistration)
        linearLayout3 = rootView.findViewById(R.id.registrationothers)
        linearLayout1?.setOnClickListener {
        mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_SHG,"")
        }
        linearLayout2?.setOnClickListener {
            mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_FAMILY,"")
        }
        linearLayout3?.setOnClickListener {
            mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE_OTHER,"")
        }
        return rootView
    }
}
