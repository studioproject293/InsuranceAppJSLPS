package com.example.insuranceapp

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object Constant {
    val HOME_FRAGMENT = 101
    var SCHEME_DETAILS_FRAGMENT = 102
    var INSURANCE_LIST_FRAGMENT = 103
    var INSURANCE_DETAILS_FRAGMENT = 104
    var UNDER_PROCESS_DETAILS_FRAGMENT = 105
    var CLAIM_SETTELED_DETAILS_FRAGMENT = 106
    val setTitle = 1111
    const val UPDATE__NO_INTERNET = 209
    val API_BASE_URL = "http://swalekha.in/webServiceModalPopup.asmx/"
    const val NO_INTERNET = "No Internet Connection"
    fun getHorizontalLayout(context: Activity): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    fun getVerticalLayout(context: Activity): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    fun gridLayout(context: FragmentActivity?, count: Int): RecyclerView.LayoutManager {
        return GridLayoutManager(context, count)
    }

}