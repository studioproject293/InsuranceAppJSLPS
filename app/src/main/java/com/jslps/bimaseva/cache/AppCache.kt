package com.jslps.bimaseva.cache

import com.jslps.bimaseva.model.LoginPojo

class AppCache {
    var loginPojo: LoginPojo? = null
    var insurancetype: String? = null
    var insuranceStep: String? = null

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