package com.example.insuranceapp.base

import android.app.Activity
import com.example.insuranceapp.cache.AppCache
import com.example.insuranceapp.Constant
import com.twidpay.beta.cache.PrefManager


open class Presenter {
    init {

    }

    fun getPref(activity: Activity): PrefManager {
        return PrefManager.getInstance(activity)
    }

    fun getAppCache(): AppCache {
        return AppCache.getCache()
    }
    fun chkIntenrnetIssue(data: Any?): Boolean {
        if (data != null) {
            val internet = data as String
            if (internet.equals(Constant.NO_INTERNET))
                return true
        }
        return false

    }


}