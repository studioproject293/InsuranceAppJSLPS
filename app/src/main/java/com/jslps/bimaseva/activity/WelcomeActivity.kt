package com.jslps.bimaseva.activity

import BaseClass
import MasterLoginDb
import Table1LoginDb
import Table2Db
import Table3Db
import Table4Db
import Table5Db
import Table6Db
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.adapter.MyCustomPagerAdapter
import com.jslps.bimaseva.network.LoginServiceNew
import com.orm.SugarRecord
import com.orm.query.Condition
import com.orm.query.Select
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WelcomeActivity : AppCompatActivity() {
    internal var images = intArrayOf(R.drawable.pmjby, R.drawable.pmsby, R.drawable.apy)
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
    private var reports: TextView? = null
    private var runningThread = true

    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {

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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)

        viewPager = findViewById(R.id.view_pager)
        reports = findViewById(R.id.reports)
        reports?.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, WelcomeActivityNew::class.java)
            startActivity(intent)

        }
        logIn = findViewById(R.id.logIn)
        versionNo = findViewById(R.id.versionNo)
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            versionNo?.setText("Version No: $version")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
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
        preferences = getSharedPreferences("MyPrefInsurance", Context.MODE_PRIVATE)
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
                editTextPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
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
                    DialogUtil.hideKeyboard(sigiin, this@WelcomeActivity)
                    dialog.cancel()
                    val arrayListVillage1: ArrayList<MasterLoginDb> =
                        Select.from<MasterLoginDb>(
                            MasterLoginDb::class.java).list() as ArrayList<MasterLoginDb>
                    println("LogInDbsdfsdfs" + Gson().toJson(arrayListVillage1))
                    if (arrayListVillage1 != null && arrayListVillage1.size > 0) {
                        if (checkboxRember!!.isChecked()) {
                            val pref =
                                this.getSharedPreferences(
                                    "MyPrefInsurance",
                                    0
                                ) // 0 - for private mode
                            val editor = pref.edit()
                            editor.putString(
                                "userName",
                                editTextUserName.getText().toString()
                            )
                            editor.putString(
                                "Password",
                                editTextPassword.getText().toString()
                            )
                            editor.apply()

                        }
                        val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        if (DialogUtil.isConnectionAvailable(this@WelcomeActivity)) {
                            DialogUtil.displayProgress(this@WelcomeActivity)
                            val gson = GsonBuilder().setLenient().create()
                            val interceptor = HttpLoggingInterceptor()
                            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                            val builder = OkHttpClient.Builder()
                            //comment in live build and uncomment in uat
                            builder.interceptors().add(interceptor)
                            builder.connectTimeout(250, TimeUnit.SECONDS)
                            builder.readTimeout(250, TimeUnit.SECONDS)
                            val client = builder.build()
                            val retrofit =
                                Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                    .addConverterFactory(
                                        ScalarsConverterFactory.create()
                                    ).client(client).build()
                            val apiServices = retrofit.create(LoginServiceNew::class.java)
                            val changePhotoResponseModelCall =
                                apiServices.getTabletDownloadDataBCsakhi(
                                    "Login",
                                    editTextUserName.text.toString(),
                                    editTextPassword.text.toString()
                                )
                            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    val gson = Gson()
                                    if (checkboxRember!!.isChecked()) {
                                        val pref =
                                            applicationContext.getSharedPreferences(
                                                "MyPrefInsurance",
                                                0
                                            ) // 0 - for private mode
                                        val editor = pref.edit()
                                        editor.putString(
                                            "userName",
                                            editTextUserName.text.toString()
                                        )
                                        editor.putString(
                                            "Password",
                                            editTextPassword.text.toString()
                                        )
                                        editor.apply()
                                    }
                                    val fullResponse = response.body()
                                    val XmlString =
                                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                    val result = XmlString?.replace(("</string>").toRegex(), "")
                                    val mStudentObject1 =
                                        gson.fromJson(result, BaseClass::class.java)
                                    Thread(object : Runnable {
                                         override fun run() {
                                            while (true) {
                                                try {
                                                    if (!runningThread) {
                                                        val intent = Intent(
                                                            this@WelcomeActivity,
                                                            MainActivity::class.java
                                                        )
                                                        startActivity(intent)
                                                        finish()
                                                        return
                                                    }
                                                    SugarRecord.deleteAll(MasterLoginDb::class.java)
                                                    for (i in mStudentObject1.master.indices) {
                                                        val stateModel1 = MasterLoginDb(
                                                            mStudentObject1.master.get(i).districtCode,
                                                            mStudentObject1.master.get(i).districtname,
                                                            mStudentObject1.master.get(i).districtName_H
                                                        )
                                                        stateModel1.save()
                                                    }
                                                    SugarRecord.deleteAll(Table1LoginDb::class.java)
                                                    for (i in mStudentObject1.table1.indices) {
                                                        val stateModel1 = Table1LoginDb(
                                                            mStudentObject1.table1.get(i).blockname,
                                                            mStudentObject1.table1.get(i).blockCode,
                                                            mStudentObject1.table1.get(i).districtCode
                                                        )
                                                        stateModel1.save()
                                                    }

                                                    SugarRecord.deleteAll(Table2Db::class.java)
                                                    for (i in mStudentObject1.table2.indices) {
                                                        val stateModel1 = Table2Db(
                                                            mStudentObject1.table2[i].ClusterCode,
                                                            mStudentObject1.table2[i].clustername
                                                        )
                                                        stateModel1.save()
                                                    }
                                                    SugarRecord.deleteAll(Table3Db::class.java)
                                                    for (i in mStudentObject1.table3.indices) {
                                                        val stateModel1 = Table3Db(
                                                            mStudentObject1.table3[i].villagename,
                                                            mStudentObject1.table3[i].VillageCode,
                                                            mStudentObject1.table3[i].clustercode
                                                        )
                                                        stateModel1.save()
                                                    }
                                                    SugarRecord.deleteAll(Table4Db::class.java)
                                                    for (i in mStudentObject1.table4.indices) {
                                                        val stateModel1 = Table4Db(
                                                            mStudentObject1.table4[i].ClusterCode,
                                                            mStudentObject1.table4[i].VillageCode,
                                                            mStudentObject1.table4[i].SHGCode,
                                                            mStudentObject1.table4[i].Group_Name
                                                        )
                                                        stateModel1.save()
                                                    }
                                                    SugarRecord.deleteAll(Table5Db::class.java)
                                                    for (i in mStudentObject1.table5.indices) {
                                                        val stateModel1 = Table5Db(
                                                            mStudentObject1.table5[i].bankCode,
                                                            mStudentObject1.table5[i].branchCode,
                                                            mStudentObject1.table5[i].branchName,
                                                            mStudentObject1.table5[i].branchName_Hindi,
                                                            mStudentObject1.table5[i].iFSCCode
                                                        )
                                                        stateModel1.save()
                                                    }

                                                    SugarRecord.deleteAll(Table6Db::class.java)
                                                    for (i in mStudentObject1.table6.indices) {
                                                        val stateModel1 = Table6Db(
                                                            mStudentObject1.table6[i].BankId,
                                                            mStudentObject1.table6[i].BankCode,
                                                            mStudentObject1.table6[i].BankName,
                                                            mStudentObject1.table6[i].BankType,
                                                            mStudentObject1.table6[i].IFSCCode,
                                                            mStudentObject1.table6[i].BankName_Hindi
                                                        )
                                                        stateModel1.save()
                                                    }
                                                    runningThread = false

                                                } catch (e: InterruptedException) {
                                                    e.printStackTrace()
                                                }
                                            }
                                        }
                                    }).start()

                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    DialogUtil.stopProgressDisplay()
                                    Sneaker.with(this@WelcomeActivity) // Activity, Fragment or ViewGroup
                                        .setTitle(t.toString())
                                        .sneakError()
                                }
                            })
                        } else {
                            Sneaker.with(this@WelcomeActivity) // Activity, Fragment or ViewGroup
                                .setTitle(Constant.NO_INTERNET)
                                .sneakError()
                        }
                    }

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
