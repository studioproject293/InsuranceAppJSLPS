package com.jslps.bimaseva.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.adapter.CustomDropDownAdapter
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.blockModel.BlockModelClass
import com.jslps.bimaseva.model.districtModel.DistirctModelClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass
import com.jslps.bimaseva.network.DistrictBlockClusterAndOtherGetList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ClaimRegistrationActivityFamilySHGMember : AppCompatActivity() {
    var nameOfnomminee: TextInputEditText? = null
    var name: TextInputEditText? = null
    var contactnoofnominee: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var sppiner_district: Spinner? = null
    var spinnerPanchyt: Spinner? = null
    var spinnerBlock: Spinner? = null
    var spinnerVillage: Spinner? = null
    var spinnerBank: Spinner? = null
    var spinnerBranch: Spinner? = null
    var spinnerShg: Spinner? = null
    var datePicker: TextView? = null
    var cal = Calendar.getInstance()
    var clustercode: String? = null
    var buttonSave: Button? = null
    var checkBox1: CheckBox? = null
    var checkBox2: CheckBox? = null
    var checkBox3: CheckBox? = null
    var checkBox4: CheckBox? = null

    val list = arrayListOf<Int>()
    var radioGroup: RadioGroup? = null
    var genderId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.claim_registration_familymember_of_shg)
        setId()
        supportActionBar?.title = "Family Member Of SHG Claim Registration";
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {
            val intent = Intent(this, ClaimRegistrationActivityOtpScreen::class.java)
            startActivity(intent)
        }
        getDistrict()
        sppiner_district?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val districtMasterClass =
                    parent?.getItemAtPosition(position) as DistrictMasterClass?
                val gson = Gson()
                Log.d(
                    "fddgsgs",
                    "Body of Update product" + gson.toJson(districtMasterClass)
                )
                getBlockData(districtMasterClass?.districtCode.toString())

            }

        }

        spinnerBlock?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val blockMasterClass = parent?.getItemAtPosition(position) as BlockMasterClass?
                val gson = Gson()
                Log.d(
                    "fddgsgs",
                    "Body of Update product" + gson.toJson(blockMasterClass)
                )
                getClusterDataList(blockMasterClass?.blockCode.toString())

            }

        }
        spinnerPanchyt?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val blockMasterClass = parent?.getItemAtPosition(position) as BlockMasterClass?
                getVillageDataList(blockMasterClass?.clusterCode.toString())

            }

        }
        spinnerVillage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val blockMasterClass = parent?.getItemAtPosition(position) as BlockMasterClass?
                getSHGDataList(blockMasterClass?.villageCode.toString())

            }

        }
        /*val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                updateDateInView()
                view.maxDate = Date().time
            }*/
        datePicker?.setOnClickListener {
            /* DatePickerDialog(
                 this, R.style.datepicker,
                 dateSetListener,
                 // set DatePickerDialog to point to today's date when it loads up
                 cal.get(Calendar.YEAR),
                 cal.get(Calendar.MONTH),
                 cal.get(Calendar.DAY_OF_MONTH)

             ).show()*/
            datePickerStrt()
        }

    }

    private fun setId() {
        nameOfnomminee = findViewById(R.id.nameOfnomminee)
        mobileofcaller = findViewById(R.id.mobileofcaller)
        name = findViewById(R.id.name)
        contactnoofnominee = findViewById(R.id.contactnoofnominee)
        nameofcaller = findViewById(R.id.nameofcaller)
        buttonSave = findViewById(R.id.buttonSave)
        datePicker = findViewById(R.id.datepicker)
        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        sppiner_district = findViewById<Spinner>(R.id.sppiner_district)
        spinnerPanchyt = findViewById<Spinner>(R.id.spinner_panchayt)
        spinnerVillage = findViewById<Spinner>(R.id.spinner_village)
        spinnerBank = findViewById<Spinner>(R.id.sppiner_bank)
        spinnerBlock = findViewById<Spinner>(R.id.spinner_block)
        spinnerBranch = findViewById<Spinner>(R.id.sppiner_branch)
        spinnerShg = findViewById<Spinner>(R.id.sppiner_shg)
        radioGroup = findViewById<RadioGroup>(R.id.radioSex)

    }

    private fun getDistrict() {
        if (DialogUtil.isConnectionAvailable(this)) {
//            DialogUtil.displayProgress(this)
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
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList("", "s", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, DistirctModelClass::class.java)
                    updateSppinerDistrict(mStudentObject1.master)

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBlockData(distictCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
//            DialogUtil.displayProgress(this)
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
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(distictCode, "D", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    updateSppinerBlock(mStudentObject1.master, "block")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getClusterDataList(blockCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
//            DialogUtil.displayProgress(this)
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
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(blockCode, "B", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    updateSppinerBlock(mStudentObject1.master, "cluster")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getVillageDataList(clusterCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
//            DialogUtil.displayProgress(this)
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
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(clusterCode, "C", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    updateSppinerBlock(mStudentObject1.master, "village")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getSHGDataList(villgaeCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
//           DialogUtil.displayProgress(this)
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
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(villgaeCode, "V", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    updateSppinerBlock(mStudentObject1.master, "shg")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun updateSppinerDistrict(master: List<DistrictMasterClass>) {
        val adapter = CustomDropDownAdapter(
            this, "district", master
        )
        sppiner_district?.adapter = adapter
    }

    private fun updateSppinerBlock(master: List<BlockMasterClass>, dataType: String) {
        val adapter = CustomDropDownAdapter(
            this, dataType, master
        )
        when (dataType) {
            "block" -> spinnerBlock?.adapter = adapter
            "cluster" -> spinnerPanchyt?.adapter = adapter
            "village" -> spinnerVillage?.adapter = adapter
            "shg" -> spinnerShg?.adapter = adapter
        }

    }

    /* private fun updateDateInView() {
         val myFormat = "dd/MM/yyyy" // mention the format you need
         val sdf = SimpleDateFormat(myFormat, Locale.US)
         datePicker!!.text = sdf.format(cal.getTime())
     }*/
    private fun datePickerStrt(): Dialog {
        var c = Calendar.getInstance(Locale.ENGLISH)
        val ALyear = c.get(Calendar.YEAR)
        val ALmonthOfYear = c.get(Calendar.MONTH)
        val ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val dateSelected =
                    (getProperFormat(dayOfMonth) + "-" + getProperFormat(monthOfYear + 1) + "-" + year)
                datePicker?.setText(dateSelected)
            }, ALyear, ALmonthOfYear, ALdayOfMonth
        )
        c.add(Calendar.YEAR, 5)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
        return dpd
    }

    private fun getProperFormat(hhORmm: Int): String {
        var temp = hhORmm.toString()
        if (temp.length == 1) {
            temp = "0$temp"
        } else {
        }
        return temp
    }
}
