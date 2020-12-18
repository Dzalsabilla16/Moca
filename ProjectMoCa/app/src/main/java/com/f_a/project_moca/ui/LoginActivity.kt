package com.f_a.project_moca.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.f_a.project_moca.MainActivity
import com.f_a.project_moca.R
import com.f_a.project_moca.session.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity: AppCompatActivity() {

    //global variables
    private var email: String? = null
    private var password: String? = null
    //UI elements
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    //Firebase references
    private var mAuth: FirebaseAuth? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var sAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var email: EditText = findViewById(R.id.et_email)
        var pass: EditText = findViewById(R.id.et_password)

        inisialisasi()
    }

    private fun inisialisasi(){
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.bt_sign_in) as Button
        mAuth = FirebaseAuth.getInstance()
        btnLogin!!.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        setSession()
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSession(){
        val sharedPreference:SharedPreferences=SharedPreferences(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("User")
        sAuth = FirebaseAuth.getInstance()

        super.onStart()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nama = mUser!!.uid

                sharedPreference.save("name",nama)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateUI(){
        val login = Intent(this, MainActivity::class.java)
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(login)
        finish()
    }

    fun sign_up(view: View) {
        val signup = Intent(this, SignUpActivity::class.java)
        startActivity(signup)
    }

    fun resset(view: View) {
        val rPass = Intent(this, ForgotPassword::class.java)
        startActivity(rPass)
    }
}
