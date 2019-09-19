package com.example.insuranceapp.cache

import com.example.insuranceapp.model.LoginPojo

    class AppCache {
     var loginPojo:LoginPojo?=null
    companion object {
        private var cache: AppCache? = null
        fun getCache(): AppCache {
            if (cache == null) cache = AppCache()
            return cache as AppCache
        }

    }

    fun clearCahe() {
        loginPojo = null
    }


}