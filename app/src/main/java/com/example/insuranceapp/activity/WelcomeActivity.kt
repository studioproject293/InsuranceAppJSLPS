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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.insuranceapp.Constant
import com.example.insuranceapp.DialogUtil
import com.example.insuranceapp.R
import com.example.insuranceapp.activity.adapter.MyCustomPagerAdapter
import com.example.insuranceapp.cache.AppCache
import com.example.insuranceapp.model.LoginPojo
import com.example.insuranceapp.network.LoginService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WelcomeActivity : AppCompatActivity() {
    internal var images = intArrayOf(R.mipmap.ic_launcher)

    private var viewPager: ViewPager? = null
    private val myViewPagerAdapter: MyViewPagerAdapter? = null
    private val dotsLayout: LinearLayout? = null
    private val dots: Array<TextView>? = null
    private val layouts: IntArray? = null
    internal var currentPage = 0
    internal var timer: Timer? = null
    internal var checkBox: CheckBox? = null
    internal var checkboxRember: CheckBox? = null
    internal var preferences: SharedPreferences? = null
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
            when {
                editTextUserName.text.toString().trim { it <= ' ' }.isEmpty() -> {
                    editTextUserName.error = "Please enter user name"
                    editTextUserName.requestFocus()
                    showError(editTextUserName)
                }
                editTextPassword.text.toString().trim { it <= ' ' }.isEmpty() -> {
                    editTextPassword.error = "Please enter user name"
                    editTextPassword.requestFocus()
                    showError(editTextPassword)
                }
                else -> {
                    if (DialogUtil.isConnectionAvailable(this@WelcomeActivity)) {
                        DialogUtil.displayProgress(this@WelcomeActivity)
                        val gson = GsonBuilder().setLenient().create()
                        val interceptor = HttpLoggingInterceptor()
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                        val builder = OkHttpClient.Builder()
                        //comment in live build and uncomment in uat
                        builder.interceptors().add(interceptor)
                        builder.connectTimeout(120, TimeUnit.SECONDS)
                        builder.readTimeout(120, TimeUnit.SECONDS)
                        val client = builder.build()
                        val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
                            ScalarsConverterFactory.create()
                        ).client(client).build()
                        val apiServices = retrofit.create(LoginService::class.java)
                        val changePhotoResponseModelCall =
                            apiServices.getTabletDownloadDataBCsakhi("callcenter", editTextUserName.getText().toString()," ")
                        changePhotoResponseModelCall.enqueue(object : Callback<String> {
                            override fun onResponse(call: Call<String>, response: Response<String>) {
                                val gson = Gson()
                                Log.v("Response prof :", "hgfgfrhgs" + response.body())
                                if (checkboxRember!!.isChecked()) {
                                    val pref =
                                        getApplicationContext().getSharedPreferences("MyPref", 0) // 0 - for private mode
                                    val editor = pref.edit()
                                    editor.putString("userName", editTextUserName.getText().toString())
                                    editor.putString("Password", editTextPassword.getText().toString())
                                    editor.apply()
                                }
                                val fullResponse = response.body()
                                val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                val result = XmlString?.replace(("</string>").toRegex(), "")
                                print("fhrjfghf" + result)
                                val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                                System.out.println("vvh" + gson.toJson(mStudentObject1))
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                DialogUtil.stopProgressDisplay()
                                val toast = Toast.makeText(this@WelcomeActivity, t.toString(), Toast.LENGTH_SHORT)
                                toast.show()
                            }
                        })
                    } else {
                        val toast = Toast.makeText(this@WelcomeActivity, Constant.NO_INTERNET, Toast.LENGTH_SHORT)
                        toast.show()
                    }

                   /* val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                    startActivity(intent)*/
                }
            }
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
