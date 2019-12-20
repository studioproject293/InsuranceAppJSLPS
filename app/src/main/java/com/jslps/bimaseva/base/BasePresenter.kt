package com.jslps.bimaseva.base

interface BasePresenter : BaseInterface {
    fun init()
    fun resume()
    fun onDestroy()
    fun onPause()
}