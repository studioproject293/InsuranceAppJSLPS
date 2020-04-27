package com.jslps.bimaseva.cache

import android.content.Context
import android.content.SharedPreferences

class PrefManager {
    private var pref: SharedPreferences? = null
    private var _context: Context? = null
    private var editor: SharedPreferences.Editor? = null
    fun init(context: Context?) {
        _context = context
        val PRIVATE_MODE = 0
        pref = _context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }

    var pREF_VONAME: String?
        get() = pref!!.getString(PREF_VONAME, null)
        set(voCode) {
            editor!!.putString(PREF_VONAME, voCode)
            editor!!.commit()
        }

    var pREF_MonthName: String?
        get() = pref!!.getString(PREF_NAME_MONTH, null)
        set(voCode) {
            editor!!.putString(PREF_NAME_MONTH, voCode)
            editor!!.commit()
        }

    var pREF_YearName: String?
        get() = pref!!.getString(PREF_NAME_YEAR, null)
        set(voCode) {
            editor!!.putString(PREF_NAME_YEAR, voCode)
            editor!!.commit()
        }

    var prefPanchyatNAME: String?
        get() = pref!!.getString(PREF_PANCHYAT_NAME, null)
        set(imageLink) {
            editor!!.putString(PREF_PANCHYAT_NAME, imageLink)
            editor!!.commit()
        }

    var prefAaganwariNAME: String?
        get() = pref!!.getString(PREF_AAGANWARI_NAME, null)
        set(userApartmnet) {
            editor!!.putString(PREF_AAGANWARI_NAME, userApartmnet)
            editor!!.commit()
        }

    var pREF_VOCode: String?
        get() = pref!!.getString(PREF_VOCode, null)
        set(voCode) {
            editor!!.putString(PREF_VOCode, voCode)
            editor!!.commit()
        }

    var prefPanchyatCode: String?
        get() = pref!!.getString(PREF_PANCHYAT_CODE, null)
        set(imageLink) {
            editor!!.putString(PREF_PANCHYAT_CODE, imageLink)
            editor!!.commit()
        }

    var prefAaganwariCode: String?
        get() = pref!!.getString(PREF_AAGANWARI_CODE, null)
        set(userApartmnet) {
            editor!!.putString(PREF_AAGANWARI_CODE, userApartmnet)
            editor!!.commit()
        }

    var prefLangaugeSelection: String?
        get() = pref!!.getString(PREF_LANGAUGE_SELECTION, null)
        set(userApartmnet) {
            editor!!.putString(PREF_LANGAUGE_SELECTION, userApartmnet)
            editor!!.commit()
        }

    val size: Int
        get() {
            val size: Int
            val entries = pref!!.all
            size = entries?.size ?: 0
            return size
        }

    companion object {
        private var manager: PrefManager? = null
        private const val PREF_NAME = "AAGANWARI"
        private const val PREF_NAME_MONTH = "MONTHNAME"
        private const val PREF_NAME_YEAR = "YEARNAME"
        private const val PREF_PANCHYAT_CODE = "panchyatCode"
        private const val PREF_AAGANWARI_CODE = "aaganwariCode"
        private const val PREF_VOCode = "VOCode"
        private const val PREF_PANCHYAT_NAME = "panchyatName"
        private const val PREF_AAGANWARI_NAME = "aaganwariName"
        private const val PREF_VONAME = "VOName"
        private const val PREF_LANGAUGE_SELECTION = "langaugeSelection"
        val instance: PrefManager?
            get() {
                if (manager == null) manager = PrefManager()
                return manager
            }
    }
}