package com.f_a.project_moca.ui.detail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.f_a.project_moca.R
import com.f_a.project_moca.static.Static
import com.google.firebase.database.*

class DetailActivity: AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        inisialisasi()
    }

    private fun inisialisasi(){
        mDatabase = FirebaseDatabase.getInstance()
        val type=intent.getStringExtra("type")
        if(type == "Movie"){
            mDatabaseReference = mDatabase!!.reference!!.child(Static.FB_DataMovie)
        }else{
            mDatabaseReference = mDatabase!!.reference!!.child(Static.FB_DataTVShow)
        }
    }

    override fun onStart() {
        super.onStart()

        val id=intent.getStringExtra("idData")
        val mUserReference = mDatabaseReference!!.child(id)

        val title: TextView = findViewById(R.id.tv_title)
        val genre: TextView = findViewById(R.id.tv_genre)
        val release: TextView = findViewById(R.id.tv_year)
        val deskripsi: TextView = findViewById(R.id.tv_description)
        val poster: ImageView = findViewById(R.id.iv_poster)
        val background: ImageView = findViewById(R.id.iv_background)
        var rate_bar: RatingBar = findViewById(R.id.rb_star);

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                title!!.text = snapshot.child("title").value as String
                genre!!.text = snapshot.child("genre").value as String
                release!!.text = snapshot.child("release").value as String
                deskripsi!!.text = snapshot.child("deskripsi").value as String

                val dImg = snapshot.child("poster").value as String
                val EncodeByte: ByteArray = android.util.Base64.decode(dImg, android.util.Base64.DEFAULT)
                val BM = BitmapFactory.decodeByteArray(
                    EncodeByte, 0,
                    EncodeByte.size
                )
                poster.setImageBitmap(BM)
                background.setImageBitmap(BM)
                rate_bar.setRating((snapshot.child("rating").value as String).toFloat());
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}
