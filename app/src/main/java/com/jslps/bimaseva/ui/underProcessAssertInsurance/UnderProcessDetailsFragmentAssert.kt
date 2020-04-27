package com.jslps.bimaseva.ui.underProcessAssertInsurance

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.MainActivity
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.ui.BaseFragment
import java.io.*


class UnderProcessDetailsFragmentAssert : BaseFragment(), UnderProcessDetailsViewAssert,
    OnFragmentListItemSelectListener {
    override fun showMessage(message: Any?) {
        val toast = Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
        toast.show()
        if (message != null) {
            if (message == "Insurance Update Successfully") {
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }

    override fun gotoScreen(fragmentID: Int, message: Any?) {

    }

    override fun loadData(cardInitResponse: ArrayList<Master>?) {

    }

    override fun showProgress() {
        activity?.let { DialogUtil.displayProgress(it) }
    }

    override fun hideProgress() {
        DialogUtil.stopProgressDisplay()
    }

    override fun noInternet() {
        val toast = Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onListItemSelected(itemId: Int, data: Any) {
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    var documentpostmatem: ImageView? = null
    var documentTag: ImageView? = null
    var documentPanchnama: ImageView? = null
    var documentClaimImage: ImageView? = null
    var documentAnimal: ImageView? = null
    var documentPreviousAttached: ImageView? = null
    var uploadDocumentAnimal: Button? = null
    var uploadDocumentPanchnma: Button? = null
    var uploadDocumentPostmatem: Button? = null
    var uploadDocumentTag: Button? = null
    var uploadDocumentClaimImage: Button? = null
    var encodedBase64Animal: String? = null
    var encodedBase64TagImage: String? = null
    var encodedBase64Postmotom: String? = null
    var encodedBase64Panchnama: String? = null
    var encodedBase64ClaimImage: String? = null
    var REQUEST_CAMERA_ANIMAL = 0
    var SELECT_FILE_ANIMAL = 1
    var REQUEST_CAMERA_TAG = 2
    var SELECT_FILE_TAG = 3
    var REQUEST_CAMERA_PANCHNAMA = 4
    var SELECT_FILE_PANCHNAMA = 5
    var REQUEST_CAMERA_CLAIM_IMAGE = 6
    var SELECT_FILE_CLAIM_IMAGE = 7
    var REQUEST_CAMERA_POSTMOTOM = 8
    var SELECT_FILE_POSTMOTOM = 9
    var presenter: UnderProcessDetailsPresenterAssert? = null
    private var rootView: View? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(
            Constant.setTitle, HeaderData(false, AppCache.getCache().insurancetype.toString())
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    attchmemntPopup(activity as Activity)
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.insurace_details_underprocess_assert, container, false)
        val nameOfInsurance: TextView? = rootView?.findViewById(R.id.insuranceName)
        val textHeading: TextView? = rootView?.findViewById(R.id.textHeading)
        textHeading?.text = "Claim Document Submitted at Bank. "
        val nomineeName: TextView? = rootView?.findViewById(R.id.nomineeInsurance)
        val contactNo: TextView? = rootView?.findViewById(R.id.contactNo)
        val block: TextView? = rootView?.findViewById(R.id.block)
        val previousHeading: TextView? = rootView?.findViewById(R.id.previousHeading)
        val village: TextView? = rootView?.findViewById(R.id.village)
        setId()
        val actionButton: Button? = rootView?.findViewById(R.id.actionButton)

        val bankbranch: TextView? = rootView?.findViewById(R.id.bankbranch)
        nomineeName?.text = insuranceNameeee?.name
        block?.text = insuranceNameeee?.blockname
        // block?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.move));
        village?.text = insuranceNameeee?.villagename
        contactNo?.text = insuranceNameeee?.phno_ofNominee.toString()
        bankbranch?.text = insuranceNameeee?.branchName
        nameOfInsurance?.text = AppCache.getCache().insurancetype
        if (!TextUtils.isEmpty(insuranceNameeee?.imagebyte)) {
            previousHeading?.visibility = View.VISIBLE
            documentPreviousAttached?.visibility = View.VISIBLE
            try {
                val byteArray: ByteArray =
                    Base64.decode(insuranceNameeee?.imagebyte, 0)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                documentPreviousAttached?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: java.lang.Error) {
            }
        } else {
            previousHeading?.visibility = View.GONE
            documentPreviousAttached?.visibility = View.GONE
        }
        presenter = UnderProcessDetailsPresenterAssert(this, activity as Activity)
        actionButton?.text = "Claim Settled"
        actionButton?.setOnClickListener {
            when {
                TextUtils.isEmpty(encodedBase64Animal) -> {
                    val toast = Toast.makeText(activity, "Please Upload death photograph of the animal", Toast.LENGTH_SHORT)
                    toast.show()
                }
                TextUtils.isEmpty(encodedBase64TagImage) -> {
                    val toast = Toast.makeText(activity, "Please Upload photo with Tag", Toast.LENGTH_SHORT)
                    toast.show()
                }
                TextUtils.isEmpty(encodedBase64Panchnama) -> {
                    val toast = Toast.makeText(activity, "Please Upload panchnama signed by VO/PG", Toast.LENGTH_SHORT)
                    toast.show()
                }
                TextUtils.isEmpty(encodedBase64Postmotom) -> {
                    val toast = Toast.makeText(activity, "Please Upload Post-mortem report", Toast.LENGTH_SHORT)
                    toast.show()
                }
                TextUtils.isEmpty(encodedBase64ClaimImage) -> {
                    val toast = Toast.makeText(activity, "Please Upload photograph of the claim form", Toast.LENGTH_SHORT)
                    toast.show()
                }
                else -> {
                    showProgress()
                    presenter?.uploadUnderProcess(
                        insuranceNameeee,
                        encodedBase64Animal,
                        encodedBase64Panchnama,
                        encodedBase64Postmotom,
                        encodedBase64TagImage,
                        encodedBase64ClaimImage
                    )
                }
            }
        }
        uploadDocumentAnimal?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
                if (ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val toast = Toast.makeText(
                        context as Activity,
                        "Permission not given",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    attchmemntPopup(context as Activity, "ANIMAL")
                }
            } else {
                attchmemntPopup(context as Activity, "ANIMAL")
            }
        }
        uploadDocumentTag?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
                if (ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val toast = Toast.makeText(
                        context as Activity,
                        "Permission not given",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    attchmemntPopup(context as Activity, "TAG")
                }
            } else {
                attchmemntPopup(context as Activity, "TAG")
            }
        }
        uploadDocumentPanchnma?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
                if (ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val toast = Toast.makeText(
                        context as Activity,
                        "Permission not given",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    attchmemntPopup(context as Activity, "PANCHNAMA")
                }
            } else {
                attchmemntPopup(context as Activity, "PANCHNAMA")
            }
        }
        uploadDocumentPostmatem?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
                if (ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val toast = Toast.makeText(
                        context as Activity,
                        "Permission not given",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    attchmemntPopup(context as Activity, "POSTMOTOM")
                }
            } else {
                attchmemntPopup(context as Activity, "POSTMOTOM")
            }
        }
        uploadDocumentClaimImage?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
                if (ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val toast = Toast.makeText(
                        context as Activity,
                        "Permission not given",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    attchmemntPopup(context as Activity, "CLAIM_IMAGE")
                }
            } else {
                attchmemntPopup(context as Activity, "CLAIM_IMAGE")
            }
        }

        return rootView!!
    }

    private fun setId() {
        documentAnimal = rootView?.findViewById(R.id.doucmentAnimal)
        documentpostmatem = rootView?.findViewById(R.id.doucmentPostmartem)
        documentPanchnama = rootView?.findViewById(R.id.doucmentPanchnama)
        documentTag = rootView?.findViewById(R.id.doucmentTag)
        documentClaimImage = rootView?.findViewById(R.id.doucmentClaimForm)
        documentPreviousAttached = rootView?.findViewById(R.id.doucment_previous_registerd)
        uploadDocumentAnimal = rootView?.findViewById(R.id.uploadDocumentAnimal)
        uploadDocumentPanchnma = rootView?.findViewById(R.id.uploadDocumentPanchnam)
        uploadDocumentTag = rootView?.findViewById(R.id.uploadDocumentTag)
        uploadDocumentPostmatem = rootView?.findViewById(R.id.uploadDocumentPostmartem)
        uploadDocumentClaimImage = rootView?.findViewById(R.id.uploadDocumentClaimForm)
    }

    private fun attchmemntPopup(context: Activity, imageFor: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.attachment_popup, null)
        val alertD = android.app.AlertDialog.Builder(context).create()
        alertD.setCancelable(true)
        //Making alert as bottom
        val window = alertD.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
        val camera = layout.findViewById(R.id.camera) as LinearLayout
        val gallery = layout.findViewById(R.id.gallery) as LinearLayout

        camera.setOnClickListener {
            alertD.dismiss()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            when (imageFor) {
                "ANIMAL" -> startActivityForResult(intent, REQUEST_CAMERA_ANIMAL)
                "TAG" -> startActivityForResult(intent, REQUEST_CAMERA_TAG)
                "PANCHNAMA" -> startActivityForResult(intent, REQUEST_CAMERA_PANCHNAMA)
                "POSTMOTOM" -> startActivityForResult(intent, REQUEST_CAMERA_POSTMOTOM)
                "CLAIM_IMAGE" -> startActivityForResult(intent, REQUEST_CAMERA_CLAIM_IMAGE)
            }
        }

        gallery.setOnClickListener {
            alertD.dismiss()
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.setType("image/*")
            when (imageFor) {
                "ANIMAL" -> startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    SELECT_FILE_ANIMAL
                )
                "TAG" -> startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    SELECT_FILE_TAG
                )
                "PANCHNAMA" -> startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    SELECT_FILE_PANCHNAMA
                )
                "POSTMOTOM" -> startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    SELECT_FILE_POSTMOTOM
                )
                "CLAIM_IMAGE" -> startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    SELECT_FILE_CLAIM_IMAGE)
        }

        }
        alertD.setView(layout)
        alertD.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE_ANIMAL) {
                if (data != null) {
                    //onSelectFromGalleryResult(data)
                    val imageUri = data.data as Uri
                    val imageStream =
                        activity?.contentResolver?.openInputStream(imageUri) as InputStream
                    val selectedImage = BitmapFactory.decodeStream(imageStream) as Bitmap
                    documentAnimal?.setImageBitmap(selectedImage)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                    val imagedata = byteArrayOutputStream.toByteArray()
                    encodedBase64Animal = Base64.encodeToString(imagedata, Base64.DEFAULT)
                    Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64Animal)
                }
            } else if (requestCode == REQUEST_CAMERA_ANIMAL) {
                try {
                    if (data != null) {
                        //onCaptureImageResult(data)
                        val photo = data.extras!!.get("data") as Bitmap
                        documentAnimal?.setImageBitmap(photo)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                        val imagedata = byteArrayOutputStream.toByteArray()
                        encodedBase64Animal = Base64.encodeToString(imagedata, Base64.DEFAULT)
                        Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64Animal)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else if (requestCode == SELECT_FILE_PANCHNAMA) {
                if (data != null) {
                    //onSelectFromGalleryResult(data)
                    val imageUri = data.data as Uri
                    val imageStream =
                        activity?.contentResolver?.openInputStream(imageUri) as InputStream
                    val selectedImage = BitmapFactory.decodeStream(imageStream) as Bitmap
                    documentPanchnama?.setImageBitmap(selectedImage)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                    val imagedata = byteArrayOutputStream.toByteArray()
                    encodedBase64Panchnama = Base64.encodeToString(imagedata, Base64.DEFAULT)
                    Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64Panchnama)
                }
            } else if (requestCode == REQUEST_CAMERA_PANCHNAMA) {
                try {
                    if (data != null) {
                        //onCaptureImageResult(data)
                        val photo = data.extras!!.get("data") as Bitmap
                        documentPanchnama?.setImageBitmap(photo)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                        val imagedata = byteArrayOutputStream.toByteArray()
                        encodedBase64Panchnama = Base64.encodeToString(imagedata, Base64.DEFAULT)
                        Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64Panchnama)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else if (requestCode == SELECT_FILE_POSTMOTOM) {
                if (data != null) {
                    //onSelectFromGalleryResult(data)
                    val imageUri = data.data as Uri
                    val imageStream =
                        activity?.contentResolver?.openInputStream(imageUri) as InputStream
                    val selectedImage = BitmapFactory.decodeStream(imageStream) as Bitmap
                    documentpostmatem?.setImageBitmap(selectedImage)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                    val imagedata = byteArrayOutputStream.toByteArray()
                    encodedBase64Postmotom = Base64.encodeToString(imagedata, Base64.DEFAULT)
                    Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64Postmotom)
                }
            } else if (requestCode == REQUEST_CAMERA_POSTMOTOM) {
                try {
                    if (data != null) {
                        //onCaptureImageResult(data)
                        val photo = data.extras!!.get("data") as Bitmap
                        documentpostmatem?.setImageBitmap(photo)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                        val imagedata = byteArrayOutputStream.toByteArray()
                        encodedBase64Postmotom = Base64.encodeToString(imagedata, Base64.DEFAULT)
                        Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64Postmotom)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else if (requestCode == SELECT_FILE_TAG) {
                if (data != null) {
                    //onSelectFromGalleryResult(data)
                    val imageUri = data.data as Uri
                    val imageStream =
                        activity?.contentResolver?.openInputStream(imageUri) as InputStream
                    val selectedImage = BitmapFactory.decodeStream(imageStream) as Bitmap
                    documentTag?.setImageBitmap(selectedImage)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                    val imagedata = byteArrayOutputStream.toByteArray()
                    encodedBase64TagImage = Base64.encodeToString(imagedata, Base64.DEFAULT)
                    Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64TagImage)
                }
            } else if (requestCode == REQUEST_CAMERA_TAG) {
                try {
                    if (data != null) {
                        //onCaptureImageResult(data)
                        val photo = data.extras!!.get("data") as Bitmap
                        documentTag?.setImageBitmap(photo)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                        val imagedata = byteArrayOutputStream.toByteArray()
                        encodedBase64TagImage = Base64.encodeToString(imagedata, Base64.DEFAULT)
                        Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64TagImage)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else if (requestCode == SELECT_FILE_CLAIM_IMAGE) {
                if (data != null) {
                    //onSelectFromGalleryResult(data)
                    val imageUri = data.data as Uri
                    val imageStream =
                        activity?.contentResolver?.openInputStream(imageUri) as InputStream
                    val selectedImage = BitmapFactory.decodeStream(imageStream) as Bitmap
                    documentClaimImage?.setImageBitmap(selectedImage)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                    val imagedata = byteArrayOutputStream.toByteArray()
                    encodedBase64ClaimImage = Base64.encodeToString(imagedata, Base64.DEFAULT)
                    Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64ClaimImage)
                }
            } else if (requestCode == REQUEST_CAMERA_CLAIM_IMAGE) {
                try {
                    if (data != null) {
                        //onCaptureImageResult(data)
                        val photo = data.extras!!.get("data") as Bitmap
                        documentClaimImage?.setImageBitmap(photo)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream)
                        val imagedata = byteArrayOutputStream.toByteArray()
                        encodedBase64ClaimImage = Base64.encodeToString(imagedata, Base64.DEFAULT)
                        Log.d("mdfmwrgsdig", "dfhgjsg" + encodedBase64ClaimImage)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /* @Throws(IOException::class)
     private fun onCaptureImageResult(data: Intent) {
         val photo = data.getExtras()?.get("data") as Bitmap
         document?.setImageBitmap(photo)
         val tempUri = getImageUri(activity as FragmentActivity, photo)
         // CALL THIS METHOD TO GET THE ACTUAL PATH
         val finalFile = File(getRealPathFromURI(tempUri))
         Log.i("gbhvhjcbvhjcvbv:", finalFile.toString())
         if (finalFile.exists()) {
             val fileInputStreamReader: FileInputStream
             try {
                 fileInputStreamReader = FileInputStream(finalFile)
                 val bytes = ByteArray(finalFile.length() as Int)
                 fileInputStreamReader.read(bytes)
                 encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
             } catch (e: FileNotFoundException) {
                 e.printStackTrace()
             } catch (e: IOException) {
                 e.printStackTrace()
             } catch (e: Error) {

             }
         }
     }*/

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri): String? {
        val cursor = activity?.getContentResolver()?.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val idx = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return idx?.let { cursor.getString(it) }
    }

    companion object {
        var insuranceNameeee: Master? = null
        fun getInstance(insuranceNamee: Master): UnderProcessDetailsFragmentAssert {
            insuranceNameeee = insuranceNamee
            return UnderProcessDetailsFragmentAssert()
        }
    }
}

