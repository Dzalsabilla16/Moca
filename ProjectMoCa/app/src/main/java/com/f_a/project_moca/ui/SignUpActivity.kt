package com.f_a.project_moca.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.f_a.project_moca.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class SignUpActivity: AppCompatActivity() {
    private var iNama : EditText? = null
    private var iEmail : EditText? = null
    private var iPass : EditText? = null

    private var name: String? = null
    private var email: String? = null
    private var pass: String? = null
    private var img: String? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    val REQUEST_CODE_GALLERY = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        inisialisasi()
    }

    private fun inisialisasi(){
        val imgAdd = findViewById<View>(R.id.iv_Gallery) as ImageView
        iNama = findViewById<View>(R.id.et_Nama) as EditText
        iEmail = findViewById<View>(R.id.et_Email) as EditText
        iPass = findViewById<View>(R.id.et_Password) as EditText
        val btnCreatAccount = findViewById<View>(R.id.bt_Sign_Up) as Button

        imgAdd.setOnClickListener{
            openGallery()
        }

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("User")
        mAuth = FirebaseAuth.getInstance()

        btnCreatAccount!!.setOnClickListener{
            CreateAccount()
        }
    }

    private fun CreateAccount() {
        var ivImg:ImageView = findViewById(R.id.civ_avatar)

        img = ivtoBytetoString(ivImg)
        name = iNama?.text.toString()
        email = iEmail?.text.toString()
        pass = iPass?.text.toString()
        val password = iPass!!.text.toString().trim()
        if (password.length < 6){
            Toast.makeText(applicationContext,"Password too short, enter mimimum 6 charcters" , Toast.LENGTH_LONG).show()
        }else{
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
//                mProgressBar!!.setMessage("Registering User...")
//                mProgressBar!!.show()

                mAuth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
//                        mProgressBar!!.hide()
                        if (task.isSuccessful) {
//                            Log.d(TAG, "createUserWithEmail:success")
                            val userId = mAuth!!.currentUser!!.uid

                            verifyEmail();

                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("name").setValue(name)
                            currentUserDb.child("email").setValue(email)
                            currentUserDb.child("pass").setValue(pass)
                            currentUserDb.child("avatar").setValue(img)

                            updateUserInfoAndUI()
                        } else {
//                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInfoAndUI() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
//                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun openGallery(){
        ActivityCompat.requestPermissions(
            this@SignUpActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_GALLERY
        )
    }

    private fun ivtoBytetoString(IV: ImageView): String? {
        val BM = (IV.drawable as BitmapDrawable).bitmap
        val BAOS = ByteArrayOutputStream()
        BM.compress(Bitmap.CompressFormat.JPEG, 50, BAOS)
        val b = BAOS.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    )
    {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val I = Intent(Intent.ACTION_PICK)
                I.type = "image/*"
                startActivityForResult(I, REQUEST_CODE_GALLERY)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Anda Tidak Memiliki Izin untuk Mengakses Lokasi File",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )
    {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
                var IVImg:ImageView = findViewById(R.id.civ_avatar)
                val IS = contentResolver.openInputStream(uri!!)
                val BM = BitmapFactory.decodeStream(IS)
                IVImg.setImageBitmap(BM)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
