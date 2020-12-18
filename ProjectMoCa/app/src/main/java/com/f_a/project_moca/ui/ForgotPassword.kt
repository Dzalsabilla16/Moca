package com.f_a.project_moca.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.f_a.project_moca.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private var iEmail: EditText? = null
    private var btnSend: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        inisialisasi()
    }

    private fun inisialisasi(){
        iEmail = findViewById<View>(R.id.et_email) as EditText
        btnSend = findViewById<View>(R.id.bt_resset) as Button
        mAuth = FirebaseAuth.getInstance()
        btnSend!!.setOnClickListener { sendPasswordResetEmail() }
    }

    private fun sendPasswordResetEmail() {
        val email = iEmail?.text.toString()
        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val message = "Email sent."
//                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
//                        Log.w(TAG, task.exception!!.message)
                        Toast.makeText(this, "No user found with this email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
