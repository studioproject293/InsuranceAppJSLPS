package com.jslps.bimaseva.ui.registration

import CallCenter
import MasterLoginDb
import Table1LoginDb
import Table2Db
import Table3Db
import Table4Db
import Table5Db
import Table6Db
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.MainActivity
import com.jslps.bimaseva.adapter.CustomDropDownAdapter
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.blockModel.BlockModelClass
import com.jslps.bimaseva.model.districtModel.DistirctModelClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass
import com.jslps.bimaseva.network.DistrictBlockClusterAndOtherGetList
import com.jslps.bimaseva.network.InsuranceCreate
import com.jslps.bimaseva.ui.BaseFragment
import com.orm.query.Condition
import com.orm.query.Select
import kotlinx.android.synthetic.main.claim_registration_shg.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class ClaimRegistrationFragmentSHGMember : BaseFragment() {
    var rootView: View? = null
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
    var radioGroup: RadioGroup? = null
    var arrayListBlock: ArrayList<Table3Db>? = null
    var arrayListShg: ArrayList<Table4Db>? = null
    var villageCode: String? = null
    var bankCode: String? = null
    var branchCode: String? = null
    override fun onResume() {
        super.onResume()
        mListener?.onFragmentUpdate(
            Constant.setTitle,
            HeaderData(false, "SHG Claim Registration")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for activity!! fragment
        rootView =
            inflater.inflate(R.layout.claim_registration_shg, container, false)
        setId()
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
        buttonSave = rootView?.findViewById(R.id.buttonSave)
        buttonSave?.text = "Save Data"
        buttonSave?.setOnClickListener {
            if (name?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name")
                    .sneakError()
            } else if (nameOfnomminee?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of nominee")
                    .sneakError()
            } else if (contactnoofnominee?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of nominee")
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
            } else if (TextUtils.isEmpty(shgCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select SHG")
                    .sneakError()
            } else if (TextUtils.isEmpty(bankCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select bank")
                    .sneakError()
            } else if (TextUtils.isEmpty(branchCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select branch")
                    .sneakError()
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } else if (list.size == 0) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select type of insurance")
                    .sneakError()
            } else if (nameofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                val id = UUID.randomUUID().toString()
                val s = TextUtils.join(", ", list)
                val prefs = activity?.getSharedPreferences(
                    "MyPrefInsurance", Context.MODE_PRIVATE
                );
                val createdBy = prefs?.getString("userName", "");
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
                    createdBy.toString(),
                    "0",
                    "0"
                )
                val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(callCenter) + "] } "
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
                                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                    .setTitle("Insurance Create Successfully ")
                                    .sneakSuccess()
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)

                            } else {
                                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                    .setTitle("Please Try Again")
                                    .sneakError()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            DialogUtil.stopProgressDisplay()
                            val toast = Toast.makeText(
                                activity,
                                "Server Error Please Try Again",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                .setTitle(t.toString())
                                .sneakError()
                        }
                    })
                } else {
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(Constant.NO_INTERNET)
                        .sneakError()
                }
            }
        }
        datePicker?.setOnClickListener {
            datePickerStrt()
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
        val arraylistPanchyat: ArrayList<Table2Db> =
            Select.from<Table2Db>(
                Table2Db::class.java
            ).list() as ArrayList<Table2Db>
        arraylistPanchyat.add(0, Table2Db("", "All Panchayat"))
        val adapterPanchyat = CustomDropDownAdapter_RegistrationInside(
            activity!!, "cluster", arraylistPanchyat
        )
        spinnerPanchyt?.adapter = adapterPanchyat
        spinnerPanchyt?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    return
                } else {
                    val panchaytModel = parent?.getItemAtPosition(position) as Table2Db?
                    clustercode = panchaytModel?.clustercode
                    val arrayListBlock =
                        Select.from<Table3Db>(
                            Table3Db::class.java
                        ).where(
                            Condition.prop("clustercode").eq(clustercode)
                        )
                            .list() as ArrayList<Table3Db>
                    arrayListBlock.add(0, Table3Db("All Village", "", ""))
                    val adapter = CustomDropDownAdapter_RegistrationInside(
                        activity!!, "village", arrayListBlock
                    )
                    spinnerVillage?.adapter = adapter
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
                    var villageModel = parent?.getItemAtPosition(position) as Table3Db
                    villageCode = villageModel?.villagecode
                    clustercode = villageModel?.clustercode
                    var arrayListShg =
                        Select.from<Table4Db>(
                            Table4Db::class.java
                        ).where(
                            Condition.prop("clustercode").eq(clustercode),
                            Condition.prop("villagecode").eq(villageCode)
                        )
                            .list() as ArrayList<Table4Db>
                    arrayListShg.add(0, Table4Db("", "", "", "All SHG"))
                    val adapter = CustomDropDownAdapter_RegistrationInside(
                        activity!!, "shg", arrayListShg
                    )
                    spinnerShg?.adapter = adapter
                }
            }

        }
        val arraylistBank: ArrayList<Table6Db> =
            Select.from<Table6Db>(
                Table6Db::class.java
            ).list() as ArrayList<Table6Db>
        arraylistBank.add(0, Table6Db("", "", "All Bank", "", "", ""))
        val adapterBankNew = CustomDropDownAdapter_RegistrationInside(
            activity!!, "bank", arraylistBank
        )
        spinnerBank?.adapter = adapterBankNew
        spinnerBank?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val bankCodeModel = parent?.getItemAtPosition(position) as Table6Db
                bankCode = bankCodeModel.bankcode
                var arrayListBranch =
                    Select.from<Table5Db>(
                        Table5Db::class.java
                    ).where(
                        Condition.prop("bankcode").eq(arraylistBank[position].bankcode)
                    )
                        .list() as ArrayList<Table5Db>
                arrayListBranch.add(0, Table5Db("", "", "All Branch", "", ""))
                val adapterBankNew = CustomDropDownAdapter_RegistrationInside(
                    activity!!, "branch", arrayListBranch
                )
                spinnerBranch?.adapter = adapterBankNew
            }

        }
        spinnerBranch?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val shgCodeModel = parent?.getItemAtPosition(position) as Table5Db
                branchCode = shgCodeModel.branchcode
            }

        }
        spinnerShg?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val shgCodeModel = parent?.getItemAtPosition(position) as Table4Db
                shgCode = shgCodeModel.SHGCode

            }

        }

        return rootView
    }



    private fun setId() {
        nameOfnomminee = rootView?.findViewById(R.id.nameOfnomminee)
        mobileofcaller = rootView?.findViewById(R.id.mobileofcaller)
        name = rootView?.findViewById(R.id.name)
        contactnoofnominee = rootView?.findViewById(R.id.contactnoofnominee)
        nameofcaller = rootView?.findViewById(R.id.nameofcaller)
        buttonSave = rootView?.findViewById(R.id.buttonSave)
        datePicker = rootView?.findViewById(R.id.datepicker)
        checkBox1 = rootView?.findViewById(R.id.checkBox1)
        checkBox2 = rootView?.findViewById(R.id.checkBox2)
        checkBox3 = rootView?.findViewById(R.id.checkBox3)
        checkBox4 = rootView?.findViewById(R.id.checkBox4)
        sppiner_district = rootView?.findViewById<Spinner>(R.id.sppiner_district)
        spinnerPanchyt = rootView?.findViewById<Spinner>(R.id.spinner_panchayt)
        spinnerVillage = rootView?.findViewById<Spinner>(R.id.spinner_village)
        spinnerBank = rootView?.findViewById<Spinner>(R.id.sppiner_bank)
        spinnerBlock = rootView?.findViewById<Spinner>(R.id.spinner_block)
        spinnerBranch = rootView?.findViewById<Spinner>(R.id.sppiner_branch)
        spinnerShg = rootView?.findViewById<Spinner>(R.id.sppiner_shg)
        radioGroup = rootView?.findViewById<RadioGroup>(R.id.radioSex)

    }

    private fun datePickerStrt(): Dialog {
        var c = Calendar.getInstance(Locale.ENGLISH)
        val ALyear = c.get(Calendar.YEAR)
        val ALmonthOfYear = c.get(Calendar.MONTH)
        val ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            activity!!,
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
