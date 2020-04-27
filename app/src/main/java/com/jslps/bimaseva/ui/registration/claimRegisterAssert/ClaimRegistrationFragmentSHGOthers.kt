package com.jslps.bimaseva.ui.registration.claimRegister

import CallCenter
import MasterLoginDb
import Table1LoginDb
import Table2Db
import Table3Db
import Table4Db
import Table7Db
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.ClaimRegistrationActivityOtpScreen
import com.jslps.bimaseva.activity.MainActivity
import com.jslps.bimaseva.activity.PoiAdapte
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.blockModel.BlockModelClass
import com.jslps.bimaseva.network.DistrictBlockClusterAndOtherGetList
import com.jslps.bimaseva.network.InsuranceCreateOTP
import com.jslps.bimaseva.network.InsuranceCreateTest
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.registration.CustomDropDownAdapter_RegistrationInside
import com.jslps.bimaseva.ui.registration.adapter.AutocompleteAdapterPG
import com.jslps.bimaseva.ui.registration.adapter.AutocompleteAdapterPanchayt
import com.jslps.bimaseva.ui.registration.adapter.AutocompleteAdapterSHG
import com.jslps.bimaseva.ui.registration.adapter.AutocompleteAdapterVillage
import com.orm.query.Condition
import com.orm.query.Select
import kotlinx.android.synthetic.main.activity_assets_registration.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ClaimRegistrationFragmentSHGOthers : BaseFragment() {

    var animals_died: TextView? = null
    var name: TextInputEditText? = null
    var Contact_Number: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var sppiner_district: Spinner? = null
    var spinnerPanchyt: AutoCompleteTextView? = null
    var spinnerBlock: Spinner? = null
    var spinnerVillage: AutoCompleteTextView? = null
    var spinnertype: Spinner? = null
    var datePicker: TextView? = null
    var clustercode: String? = null
    var buttonSave: Button? = null
    var distirctCode: String? = null
    var blockCode: String? = null
    var shgCode: String? = null
    val list = arrayListOf<Int>()
    var radioGroup: RadioGroup? = null
    var villageCode: String? = null
    var bankCode: String? = null
    var branchCode: String? = null
    var panchyatModel: BlockMasterClass? = null
    var villageModel: BlockMasterClass? = null
    var shgModel: BlockMasterClass? = null
    var selectedtype: String? = null
    var blockModel: BlockMasterClass? = null
    var schemeID: String = "1";
    var pgLayout: TextInputLayout? = null
    var shgLayout: TextInputLayout? = null
    override fun onResume() {
        super.onResume()
        mListener?.onFragmentUpdate(Constant.setTitle,
            HeaderData(false,"Asset Registration Of Other")
        )
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_claimregshgohers, container, false)

        animals_died = view.findViewById(R.id.animals_died)
        mobileofcaller = view.findViewById(R.id.mobileofbeneficiary)
        name = view.findViewById(R.id.name)
        Contact_Number = view.findViewById(R.id.Contact_Number)
        nameofcaller = view.findViewById(R.id.nameofcaller)
        buttonSave = view.findViewById(R.id.buttonSave)
        datePicker = view.findViewById(R.id.datepicker)
        sppiner_district = view.findViewById<Spinner>(R.id.sppiner_district)
        spinnerPanchyt = view.findViewById<AutoCompleteTextView>(R.id.spinner_panchayt)
        spinnerVillage = view.findViewById<AutoCompleteTextView>(R.id.spinner_village)
        spinnerBlock = view.findViewById<Spinner>(R.id.spinner_block)
        spinnertype=view.findViewById<Spinner>(R.id.type_of_asset)
        radioGroup = view.findViewById<RadioGroup>(R.id.radiotype)
        pgLayout = view.findViewById(R.id.pg)
        shgLayout = view.findViewById(R.id.SHG)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {
            if (name?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter Name of the Beneficiary ")
                    .sneakError()
            } else if (Contact_Number?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter contact no of Beneficiary")
                    .sneakError()
            } else if (TextUtils.isEmpty(distirctCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select district")
                    .sneakError()
            } else if (TextUtils.isEmpty(blockCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select block")
                    .sneakError()
            } else if (TextUtils.isEmpty(clustercode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select panchayat")
                    .sneakError()
            } else if (TextUtils.isEmpty(villageCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select village")
                    .sneakError()
            } else if (animals_died?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter animals died")
                    .sneakError()
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } else if (Reason_of_death?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                //please configure this code
                val id = UUID.randomUUID().toString()
                val s = TextUtils.join(", ", list)
                val prefs = activity?.getSharedPreferences(
                    "MyPrefInsurance", Context.MODE_PRIVATE
                );
                val createdBy = prefs?.getString("userName", "");
                val callCenter2 = CallCenter(
                    name?.text.toString(),
                    "",
                    Contact_Number?.text.toString(),
                    distirctCode!!,
                    blockCode!!,
                    clustercode!!,
                    villageCode.toString(),
                    "",
                    "",
                    "",
                    datePicker?.text.toString(),
                    schemeID!!,
                    "",
                    mobileofcaller?.text.toString(),
                    "",
                    id,
                    createdBy.toString(),
                    "0",
                    "0",
                    "",
                    assertType.toString(),
                    animals_died?.text.toString(),
                    Reason_of_death?.text.toString()
                )

                val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(callCenter2) + "] } "
                if (DialogUtil.isConnectionAvailable(activity!!)) {
                    DialogUtil.displayProgress(activity!!)
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
                        Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
                            ScalarsConverterFactory.create()
                        ).client(client).build()
                    val apiServices = retrofit.create(InsuranceCreateTest::class.java)
                    val createInsurance = apiServices.createInsurance(data)
                    createInsurance.enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>) {
                            DialogUtil.stopProgressDisplay()
                            val fullResponse = response.body()
                            val XmlString =
                                fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                            val result = XmlString?.replace(("</string>").toRegex(), "")
                            if (result.equals("\"1\"")) {
                                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                    .setTitle("Insurance Create Successfully ")
                                    .sneakSuccess()
                                val intent = Intent(activity, MainActivity::class.java)
                                Handler().postDelayed(object:Runnable {
                                    override fun run() {
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                    }
                                }, 2000)

                            } else {
                                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                    .setTitle("Please Try Again")
                                    .sneakError()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            DialogUtil.stopProgressDisplay()
                            val toast = Toast.makeText(activity, "Server Error Please Try Again", Toast.LENGTH_SHORT)
                            toast.show()
                            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                .setTitle(t.toString())
                                .sneakError()
                        }
                    })
                } else {
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(getString(R.string.no_internet_connection))
                        .sneakError()
                }
            }
        }


        val arraylistDistict: ArrayList<MasterLoginDb> =
            Select.from<MasterLoginDb>(
                MasterLoginDb::class.java
            ).list() as ArrayList<MasterLoginDb>

        val adapter = CustomDropDownAdapter_RegistrationInside(
            activity!!, "district", arraylistDistict
        )
        sppiner_district?.adapter = adapter

        sppiner_district?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val districtMasterClass = parent?.getItemAtPosition(position) as MasterLoginDb?
                distirctCode = districtMasterClass?.districtcode
                val arrayListBlock: ArrayList<Table1LoginDb> =
                    Select.from<Table1LoginDb>(
                        Table1LoginDb::class.java
                    ).where(
                        Condition.prop("districtcode").eq(distirctCode)
                    )
                        .list() as ArrayList<Table1LoginDb>

                val adapter = CustomDropDownAdapter_RegistrationInside(
                    activity!!, "block", arrayListBlock
                )
                spinnerBlock?.adapter = adapter
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
                val shgCodeModel = parent?.getItemAtPosition(position) as Table1LoginDb
                blockCode = shgCodeModel.blockcode

            }

        }
        val languages = resources.getStringArray(R.array.Asset_Insured)
        val adapter1 = ArrayAdapter(
            activity!!,
            R.layout.spiner_row, languages
        )
        spinnertype?.adapter = adapter1
        spinnertype?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedtype = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    assertType = 1
                } else if (position == 1) {
                    assertType = 2
                } else if (position == 2) {
                    assertType = 3
                } else if (position == 3) {
                    assertType = 4
                } else if (position == 4) {
                    assertType = 5
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        val arraylistPanchyat: ArrayList<Table2Db> =
            Select.from<Table2Db>(
                Table2Db::class.java
            ).list() as ArrayList<Table2Db>
        val adapterPanchyat = AutocompleteAdapterPanchayt(
            activity!!, R.layout.spiner_row_new, arraylistPanchyat, "cluster"
        )
        spinnerPanchyt?.setAdapter(adapterPanchyat)
        spinnerPanchyt?.threshold = 1

        spinnerVillage?.setOnItemClickListener() { parent, _, position, id ->
            val villageModel = parent?.getItemAtPosition(position) as Table3Db
            villageCode = villageModel.villagecode
            clustercode = villageModel.clustercode
            spinnerVillage?.setText(villageModel.villagename)


        }
        spinnerPanchyt?.setOnItemClickListener() { parent, _, position, id ->
            spinnerVillage?.clearFocus()
            spinnerVillage?.setText("")
            val panchaytModel = parent?.getItemAtPosition(position) as Table2Db?
            clustercode = panchaytModel?.clustercode
            spinnerPanchyt?.setText(panchaytModel?.clustername)
            val arrayListBlock = Select.from<Table3Db>(Table3Db::class.java).where(
                Condition.prop("clustercode").eq(clustercode))
                .list() as ArrayList<Table3Db>
            val adapter = AutocompleteAdapterVillage(
                activity!!, R.layout.spiner_row_new,arrayListBlock,"village")
            spinnerVillage?.setAdapter(adapter)
            spinnerVillage?.threshold = 1

        }



        datePicker?.setOnClickListener {
            datePickerStrt()
        }




        return view
    }
    var assertType: Int? = 1
    var instituionType: Int? = 1
    private fun getDistrict() {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(250, TimeUnit.SECONDS)
            builder.readTimeout(250, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
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
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    sppiner_district?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master,"district")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(getString(R.string.no_internet_connection))
                .sneakError()
        }
    }

    private fun getBlockData(distictCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                )
                {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerBlock?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "block")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(getString(R.string.no_internet_connection))
                .sneakError()
        }
    }

    private fun getClusterDataList(blockCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
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
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerPanchyt?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "cluster")
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(getString(R.string.no_internet_connection))
                .sneakError()
        }
    }

    private fun getVillageDataList(clusterCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerVillage?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "village")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(getString(R.string.no_internet_connection))
                .sneakError()
        }
    }

    private fun getSHGDataList(villgaeCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                    updateSppinerDistrict(mStudentObject1.master, "shg")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(getString(R.string.no_internet_connection))
                .sneakError()
        }
    }
    private fun updateSppinerDistrict(master: ArrayList<BlockMasterClass>, value: String) {
        val adapter1 = PoiAdapte(activity!!, R.layout.spiner_row, master, value)
        when (value) {
            "district" -> {
                sppiner_district?.setAdapter(adapter1)
            }
            "block" -> {
                spinnerBlock?.setAdapter(adapter1)
            }
            "cluster" -> {
                spinnerPanchyt?.setAdapter(adapter1)
            }
            "village" -> {
                spinnerVillage?.setAdapter(adapter1)
            }
        }
    }

    private fun datePickerStrt(): Dialog {
        var c = Calendar.getInstance(Locale.ENGLISH)
        val ALyear = c.get(Calendar.YEAR)
        val ALmonthOfYear = c.get(Calendar.MONTH)
        val ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            activity!!, R.style.datepicker,
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
