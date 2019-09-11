package com.example.insuranceapp.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.example.insuranceapp.R
import com.example.insuranceapp.listener.OnFragmentInteractionListener
import com.jslps.aaganbariapp.Constant
import com.jslps.aaganbariapp.fragment.HomeFragment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private var mFragmentManager: FragmentManager? = null
    private var mFragmentTag: String? = null
    private var mCurrentFragment: Int = 0
    internal var toolbar_home: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar_home = findViewById(R.id.toolbar_home)
    }


    override fun onFragmentInteraction(fragmentId: Int, data: Any) {
        mFragmentManager = supportFragmentManager
        mCurrentFragment = fragmentId
        mFragmentTag = fragmentId.toString()
        when (fragmentId) {
            Constant.HOME_FRAGMENT -> {
                mFragmentManager?.beginTransaction()!!.addToBackStack(mFragmentTag)
                    .replace(R.id.fragment_main, HomeFragment(), mFragmentTag).commitAllowingStateLoss()
            }
            
        }
    }

    override fun onFragmentUpdate(type: Int, data: Any) {
        when (type) {

        }
    }

    override fun onBackPressed() {
        //super.onBackPressed();
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            closeApp()
        } else
            super.onBackPressed()

    }

    fun closeApp() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(getString(R.string.exit_message))
        alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { arg0, arg1 -> finish() }
        alertDialogBuilder.setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {

        @Throws(IOException::class)
        fun copyFile(sourceFile: File, destFile: File): Boolean {
            //        if (!destFile.exists()) {
            destFile.createNewFile()
            var source: FileChannel? = null
            var destination: FileChannel? = null
            try {
                source = FileInputStream(sourceFile).channel
                destination = FileOutputStream(destFile).channel
                destination!!.transferFrom(source, 0, source!!.size())
            } finally {
                source?.close()
                destination?.close()
            }
            return true
        }
    }


}
