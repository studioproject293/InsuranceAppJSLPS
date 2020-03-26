package com.jslps.bimaseva.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.jslps.bimaseva.R

class ClaimRegistrationActivity : AppCompatActivity() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim_registration)
        linearLayout1 = findViewById(R.id.shgRegistration)
        linearLayout2 = findViewById(R.id.shgFamilyregistration)
        linearLayout3 = findViewById(R.id.registrationothers)
      linearLayout1?.setOnClickListener {
            val intent = Intent(this@ClaimRegistrationActivity, ClaimRegistrationActivitySHGMember::class.java)
            startActivity(intent)
        }
         linearLayout2?.setOnClickListener {
           val intent = Intent(this@ClaimRegistrationActivity, ClaimRegistrationActivityFamilySHGMember::class.java)
           startActivity(intent)
       }
       linearLayout3?.setOnClickListener {
           val intent = Intent(this@ClaimRegistrationActivity, ClaimRegistrationActivityOthers::class.java)
           startActivity(intent)
       }

    }
}
