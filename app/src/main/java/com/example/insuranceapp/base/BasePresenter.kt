package com.example.insuranceapp.base

interface BasePresenter : BaseInterface {
    fun init()
    fun resume()
    fun onDestroy()
    fun onPause()
}