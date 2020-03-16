package com.jslps.bimaseva.activity

import CallCenter
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import com.jslps.bimaseva.network.InsuranceCreate
import kotlinx.android.synthetic.main.claim_registration_shg.*
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
    var distirctCode: String? = null
    var blockCode: String? = null
    var shgCode: String? = null
    val list = arrayListOf<Int>()
    var radioButton: RadioButton? = null
    var radioGroup: RadioGroup? = null
    var genderId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.claim_registration_familymember_of_shg)
        setId()
        supportActionBar?.title = "Family Member Of SHG Claim Registration";
        checkBox1?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(1)
            else
                list.remove(1)
        }
        checkBox2?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(2)
            else
                list.remove(2)
        }
        checkBox3?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(3)
            else
                list.remove(3)
        }
        checkBox4?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(4)
            else
                list.remove(4)
        }
        val languages = resources.getStringArray(R.array.Relationship)
        val sppiner_relationship = findViewById<Spinner>(R.id.sppiner_relationship)
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {
            if (name?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name")
                    .sneakError()
            } else if (nameOfnomminee?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of nominee")
                    .sneakError()
            } else if (contactnoofnominee?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of nominee")
                    .sneakError()
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } else if (list.size == 0) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please select type of insurance")
                    .sneakError()
            } else if (nameofcaller?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                val selectedId = radioGroup?.getCheckedRadioButtonId();
                /*radioButton = findViewById<RadioButton>(selectedId!!);
                if (radioButton?.text.toString().equals("Male")) {
                    genderId = 1
                } else
                    genderId = 2*/
                val id = UUID.randomUUID().toString()
                val s = TextUtils.join(", ", list)
                val callCenter = CallCenter(
                    name?.text.toString(),
                    nameOfnomminee?.text.toString(),
                    contactnoofnominee?.text.toString(),
                    distirctCode!!,
                    blockCode!!,
                    clustercode!!,
                    villageCode.toString(),
                    shgCode!!,
                    bankCode.toString(),
                    branchCode.toString(),
                    datePicker?.text.toString(),
                    s, "",
                    mobileofcaller?.text.toString(),
                    nameofcaller?.text.toString(),
                    id,
                    "Admin",
                    "",
                    relationShipId.toString()
                )
                val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(callCenter) + "] } "
                if (DialogUtil.isConnectionAvailable(this@ClaimRegistrationActivityFamilySHGMember)) {
                    DialogUtil.displayProgress(this@ClaimRegistrationActivityFamilySHGMember)
                    val gson = GsonBuilder().setLenient().create()
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val builder = OkHttpClient.Builder()
                    //comment in live build and uncomment in uat
                    builder.interceptors().add(interceptor)
                    builder.connectTimeout(120, TimeUnit.SECONDS)
                    builder.readTimeout(120, TimeUnit.SECONDS)
                    val client = builder.build()
                    val retrofit =
                        Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
                            ScalarsConverterFactory.create()
                        ).client(client).build()
                    val apiServices = retrofit.create(InsuranceCreate::class.java)
                    val createInsurance = apiServices.createInsurance(data)
                    createInsurance.enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            DialogUtil.stopProgressDisplay()
                            val fullResponse = response.body()
                            val XmlString =
                                fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                            val result = XmlString?.replace(("</string>").toRegex(), "")
                            if (result.equals("\"1\"")) {
                                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                                    .setTitle("Insurance Create Successfully ")
                                    .sneakSuccess()
                                val intent = Intent(
                                    this@ClaimRegistrationActivityFamilySHGMember,
                                    MainActivity::class.java
                                )
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)

                            } else {
                                Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                                    .setTitle("Please Try Again")
                                    .sneakError()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            DialogUtil.stopProgressDisplay()
                            Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                                .setTitle("Server Error Please Try Again")
                                .sneakError()
                        }
                    })
                } else {
                    Sneaker.with(this@ClaimRegistrationActivityFamilySHGMember) // Activity, Fragment or ViewGroup
                        .setTitle(Constant.NO_INTERNET)
                        .sneakError()
                }
            }
        }
        val adapter = ArrayAdapter(
            this,
            R.layout.spiner_row, languages
        )
        sppiner_relationship.adapter = adapter

        sppiner_relationship.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        return
                    }
                    1 -> {
                        relationShipId = "1"
                    }
                    2 -> {
                        relationShipId = "2"
                    }
                    3 -> {
                        relationShipId = "3"
                    }
                    4 -> {
                        relationShipId = "4"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        getDistrict()
        getBankList()
        sppiner_district?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                    return
                else {
                    val districtMasterClass =
                        parent?.getItemAtPosition(position) as DistrictMasterClass?
                    val gson = Gson()
                    Log.d(
                        "fddgsgs",
                        "Body of Update product" + gson.toJson(districtMasterClass)
                    )
                    distirctCode = districtMasterClass?.districtCode.toString()
                    getBlockData(districtMasterClass?.districtCode.toString())

                }
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
                if (position == 0)
                    return
                else {
                    val blockMasterClass = parent?.getItemAtPosition(position) as BlockMasterClass?
                    val gson = Gson()
                    Log.d(
                        "fddgsgs",
                        "Body of Update product" + gson.toJson(blockMasterClass)
                    )
                    blockCode = blockMasterClass?.blockCode.toString()
                    getClusterDataList(blockMasterClass?.blockCode.toString())

                }
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
                if (position == 0)
                    return
                else {
                    val blockMasterClass = parent?.getItemAtPosition(position) as BlockMasterClass?
                    getVillageDataList(blockMasterClass?.clusterCode.toString())
                }

            }

        }
        spinnerBank?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                    return
                else {
                    val districtMasterClass =
                        parent?.getItemAtPosition(position) as BlockMasterClass
                    bankCode = districtMasterClass.bankCode
                    getBranchDataList(districtMasterClass.bankCode)
                }
            }

        }
        spinnerBranch?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                    return
                else {
                    val districtMasterClass =
                        parent?.getItemAtPosition(position) as BlockMasterClass
                    branchCode = districtMasterClass.branchCode
                }
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
                if (position == 0)
                    return
                else {
                    val blockMasterClass = parent?.getItemAtPosition(position) as BlockMasterClass?
                    villageCode = blockMasterClass?.villageCode.toString()
                    getSHGDataList(blockMasterClass?.villageCode.toString())
                }

            }

        }

        datePicker?.setOnClickListener {

            datePickerStrt()
        }

    }

    var villageCode: String? = null
    var relationShipId: String? = null
    var bankCode: String? = null
    var branchCode: String? = null
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
        master.get(0).districtName = "All District"
        val adapter = CustomDropDownAdapter(
            this, "district", master
        )
        sppiner_district?.adapter = adapter
    }

    private fun getBankList() {
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
                apiServices.fetchDistrictBlockClusterAndOtherGetList("", "BNK_NAME", "", "")
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
                    updateSppinerBlock(mStudentObject1.master, "bank")

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

    private fun getBranchDataList(bankCode: String) {
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
                apiServices.fetchDistrictBlockClusterAndOtherGetList(bankCode, "BRN_NAME", "", "")
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
                    updateSppinerBlock(mStudentObject1.master, "branch")

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

    private fun updateSppinerBlock(master: List<BlockMasterClass>, dataType: String) {

        when (dataType) {
            "block" -> {
                /*spinnerBlock?.isEnabled=true*/
                master.get(0).blockName = "All Block"
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                spinnerBlock?.adapter = adapter
            }

            "cluster" -> {
                /*spinnerBlock?.isEnabled=true*/
                master.get(0).clusterName = "All Panchyat"
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                spinnerPanchyt?.adapter = adapter
            }

            "village" -> {
                /*spinnerBlock?.isEnabled=true*/
                master.get(0).villageName = "All Village"
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                spinnerVillage?.adapter = adapter
            }
            "shg" -> {
                /*spinnerBlock?.isEnabled=true*/
                master.get(0).group_Name = "All SHG"
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                spinnerShg?.adapter = adapter
            }
            "bank" -> {
                /*spinnerBlock?.isEnabled=true*/
                master.get(0).bankName = "All Bank"
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                sppiner_bank?.adapter = adapter
            }
            "branch" -> {
                /*spinnerBlock?.isEnabled=true*/
                master.get(0).branchName = "All Branch"
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                sppiner_branch?.adapter = adapter
            }
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
