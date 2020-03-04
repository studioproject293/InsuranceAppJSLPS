package com.jslps.bimaseva.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.jslps.bimaseva.R
import java.util.*

class ClaimRegistrationActivitySHGMember : AppCompatActivity() {
    var nameOfnomminee: TextInputEditText? = null
    var name: TextInputEditText? = null
    var contactnoofnominee: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var spinner: Spinner? = null
    var spinnerPanchyt: Spinner? = null
    var spinnerBlock: Spinner? = null
    var spinnerVillage: Spinner? = null
    var spinnerBank: Spinner? = null
    var spinnerBranch: Spinner? = null
    var spinnerShg: Spinner? = null
    var datePicker: TextView? = null
    var cal = Calendar.getInstance()
    var clustercode: String? = null
    var viillagecode: String? = null
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
    var genderId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.claim_registration_shg)
        buttonSave=findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {  }

    }
}
