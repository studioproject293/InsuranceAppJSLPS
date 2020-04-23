package com.jslps.bimaseva.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.cardview.widget.CardView
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.R
import com.jslps.bimaseva.assetsReg.AssetsRegistrationOther
import com.jslps.bimaseva.assetsReg.AssetsRegistrationShgMember

class ClaimRegistrationActivity : AppCompatActivity() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    var linearLayout4: LinearLayout? = null
    var linearLayout5: LinearLayout? = null
    var cardView1: CardView? = null
    var cardView2: CardView? = null
    var cardView3: CardView? = null
    var cardView4: CardView? = null
    var cardView5: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim_registration)
        supportActionBar?.title = "Registration Type";
        linearLayout1 = findViewById(R.id.shgRegistration)
        linearLayout4 = findViewById(R.id.shgRegistration4)
        linearLayout2 = findViewById(R.id.shgFamilyregistration)
        linearLayout3 = findViewById(R.id.registrationothers)
        linearLayout5 = findViewById(R.id.registrationothers5)
        cardView1 = findViewById(R.id.card1)
        cardView2 = findViewById(R.id.card2)
        cardView3 = findViewById(R.id.card3)
        cardView4 = findViewById(R.id.card4)
        cardView5 = findViewById(R.id.card5)
        linearLayout1?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent = Intent(

                    this@ClaimRegistrationActivity,
                    ClaimRegistrationActivitySHGMember::class.java
                )
                intent.putExtra("schemeID", schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout2?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent = Intent(
                    this@ClaimRegistrationActivity,
                    ClaimRegistrationActivityFamilySHGMember::class.java
                )
                intent.putExtra("schemeID", schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout3?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent =
                    Intent(
                        this@ClaimRegistrationActivity,
                        ClaimRegistrationActivityOthers::class.java
                    )
                intent.putExtra("schemeID", schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout5?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent =
                    Intent(
                        this@ClaimRegistrationActivity,
                        AssetsRegistrationOther::class.java
                    )
                intent.putExtra("schemeID", schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout4?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent =
                    Intent(
                        this@ClaimRegistrationActivity,
                        AssetsRegistrationShgMember::class.java
                    )
                intent.putExtra("schemeID", schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        val scheme = resources.getStringArray(R.array.schemeArray)
        val sppiner_scheme = findViewById<Spinner>(R.id.sppiner_scheme)
        val adapter = ArrayAdapter(
            this,
            R.layout.spiner_row, scheme
        )
        sppiner_scheme.adapter = adapter
        sppiner_scheme.onItemSelectedListener = object :
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
                        schemeId = "1"
                        cardView1?.visibility = View.VISIBLE
                        cardView2?.visibility = View.VISIBLE
                        cardView3?.visibility = View.VISIBLE
                        cardView4?.visibility = View.GONE
                        cardView5?.visibility = View.GONE
                    }
                    2 -> {
                        schemeId = "2"
                        cardView1?.visibility = View.VISIBLE
                        cardView2?.visibility = View.VISIBLE
                        cardView3?.visibility = View.VISIBLE
                        cardView4?.visibility = View.GONE
                        cardView5?.visibility = View.GONE

                    }
                    3 -> {
                        schemeId = "3"
                        cardView1?.visibility = View.VISIBLE
                        cardView2?.visibility = View.VISIBLE
                        cardView3?.visibility = View.VISIBLE
                        cardView4?.visibility = View.GONE
                        cardView5?.visibility = View.GONE

                    }
                    4 -> {
                        schemeId = "4"
                        cardView1?.visibility = View.GONE
                        cardView2?.visibility = View.GONE
                        cardView3?.visibility = View.GONE
                        cardView4?.visibility = View.VISIBLE
                        cardView5?.visibility = View.VISIBLE

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    var schemeId: String? = null
}
