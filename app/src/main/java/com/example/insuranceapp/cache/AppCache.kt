package com.example.insuranceapp.cache

class AppCache {

    companion object {
        private var cache: AppCache? = null

        fun getCache(): AppCache {
            if (cache == null) cache = AppCache()
            return cache as AppCache
        }

    }

    fun clearCahe() {

    }



}