package com.example.insuranceapp.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.insuranceapp.R
import com.example.insuranceapp.activity.adapter.MyCustomPagerAdapter
import java.util.*

class WelcomeActivity : AppCompatActivity() {
    internal var images = intArrayOf(R.mipmap.ic_launcher)

    private var viewPager: ViewPager? = null
    private val myViewPagerAdapter: MyViewPagerAdapter? = null
    private val dotsLayout: LinearLayout? = null
    private val dots: Array<TextView>? = null
    private val layouts: IntArray? = null
    internal var currentPage = 0
    internal var timer: Timer?=null
    internal var checkBox: CheckBox?=null
    internal var checkboxRember: CheckBox?=null
    internal var preferences: SharedPreferences?=null
    internal val DELAY_MS: Long = 500//delay in milliseconds before task is to be executed
    internal val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.
    private var logIn: TextView? = null
    private var versionNo: TextView? = null


    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            //addBottomDots(position);
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)

        viewPager = findViewById(R.id.view_pager)
        logIn = findViewById(R.id.logIn)
        versionNo = findViewById(R.id.versionNo)
        versionNo!!.text = "Version No: 1.1"
        // making notification bar transparent
        changeStatusBarColor()
        val myCustomPagerAdapter = MyCustomPagerAdapter(this@WelcomeActivity, images)
        viewPager!!.adapter = myCustomPagerAdapter
        // myViewPagerAdapter = new MyViewPagerAdapter();
        // viewPager.setAdapter(myViewPagerAdapter);
        // viewPager.setOffscreenPageLimit(layouts.length);

        val handler = Handler()
        val Update = Runnable {
            val NUM_PAGES = images.size
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            viewPager!!.setCurrentItem(currentPage++, true)
        }

        timer = Timer() // This will create a new Thread
        timer?.schedule(object : TimerTask() { // task to be scheduled

            override fun run() {
                handler.post(Update)
            }
        }, 500, 3000)

        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        logIn!!.setOnClickListener { showCustomDialog() }


    }

    private fun showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Dialog_Alert)
        // Include dialog.xml file
        dialog.setContentView(R.layout.layout_dialog)
        val closeButton = dialog.findViewById<ImageView>(R.id.closeButton)
        val editTextUserName = dialog.findViewById<EditText>(R.id.etusername)
        val editTextPassword = dialog.findViewById<EditText>(R.id.etpass)
        val sigiin = dialog.findViewById<Button>(R.id.sigiin)
        checkBox = dialog.findViewById(R.id.checkbox)
        checkboxRember = dialog.findViewById(R.id.checkboxRember)
        preferences = getSharedPreferences(
            "MyPref",
            Context.MODE_PRIVATE
        )

        val value = preferences?.getString("userName", "")
        val value1 = preferences?.getString("Password", "")
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value1)) {
            editTextUserName.setText(value)
            editTextUserName.isEnabled = true
            editTextPassword.setText(value1)
        } else {
            editTextUserName.setText("")
            editTextUserName.isEnabled = true
            editTextPassword.setText("")
        }
        checkBox?.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked)
                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            else
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        sigiin.setOnClickListener {
            /*if (editTextUserName.text.toString().trim { it <= ' ' }.isEmpty()) {
                editTextUserName.error = "Please enter user name"
                editTextUserName.requestFocus()
                showError(editTextUserName)
            } else if (editTextPassword.text.toString().trim { it <= ' ' }.isEmpty()) {
                editTextPassword.error = "Please enter user name"
                editTextPassword.requestFocus()
                showError(editTextPassword)
            } else {
              val intent=Intent(this@WelcomeActivity,MainActivity::class.java)
                startActivity(intent)
            }*/
            val intent=Intent(this@WelcomeActivity,MainActivity::class.java)
            startActivity(intent)
        }
        closeButton.setOnClickListener { dialog.cancel() }
        dialog.setCancelable(false)
        dialog.show()

    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts!![position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    private fun showError(editText: EditText) {
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        editText.startAnimation(shake)
    }
}
