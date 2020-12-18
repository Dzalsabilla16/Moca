package com.f_a.project_moca.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.f_a.project_moca.R
import com.f_a.project_moca.model.MMovie
import com.f_a.project_moca.model.MTVShow
import com.f_a.project_moca.session.SharedPreferences
import com.f_a.project_moca.static.Static
import com.f_a.project_moca.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_favourite.view.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class FavouriteFragment : Fragment() {

//    lateinit var mView: View
//    lateinit var DB: DatabaseReference
//    val REQUEST_CODE_GALLERY = 999
//    var cal = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)

//        val activity = activity as Context
//        val sharedPreference: SharedPreferences = SharedPreferences(activity)
//        val text = mView!!.findViewById(R.id.tv_nama) as TextView
//
//        if (sharedPreference.getValueString("nama") != null) {
//            val nama = sharedPreference.getValueString("nama")!!
//            text.text = nama
//        } else {
//            text.text = "NO value found"
//        }
//
//        mView.bCImg.setOnClickListener { openGallery() }
//        mView.bAdd.setOnClickListener { addMovie() }
//
//        mView.btnLogout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            sharedPreference.clearSharedPreference()
//            val intent = Intent(activity, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
//        }
//
//        val genre = arrayOf(
//            "Action",
//            "Adventure",
//            "Comedy",
//            "Romace",
//            "Drama",
//            "Horror",
//            "Historical",
//            "Science Fiction",
//            "War"
//        )
//        val adapterGenre = ArrayAdapter(
//            activity,
//            android.R.layout.simple_spinner_item,
//            genre
//        )
//        adapterGenre.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
//
//        val spr_genre: Spinner = mView.findViewById(R.id.spr_genre)
//        spr_genre.adapter = adapterGenre;
//
//        val type = arrayOf("Movie", "TV Show")
//        val adapterType = ArrayAdapter(
//            activity,
//            android.R.layout.simple_spinner_item,
//            type
//        )
//        adapterType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
//
//        val spr_type: Spinner = mView.findViewById(R.id.spr_type)
//        spr_type.adapter = adapterType;
//
//        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
//            override fun onDateSet(
//                view: DatePicker, year: Int, monthOfYear: Int,
//                dayOfMonth: Int
//            ) {
//                cal.set(Calendar.YEAR, year)
//                cal.set(Calendar.MONTH, monthOfYear)
//                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateDateInView()
//            }
//        }
//
//        var etRelease: EditText = mView.findViewById(R.id.et_Release)
//        etRelease?.setOnClickListener {
//            DatePickerDialog(
//                activity,
//                dateSetListener,
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH)
//            ).show()
//        }
//
//        etRelease.setKeyListener(null)
//        return mView
    }

//    private fun openGallery() {
//        requestPermissions(
//            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//            REQUEST_CODE_GALLERY
//        )
//    }
//
//    private fun addMovie() {
//        var sType = spr_type.getSelectedItem().toString()
//        if (sType == "Movie") {
//            DB = FirebaseDatabase.getInstance().reference
//            var IVImg: ImageView = mView.findViewById(R.id.iv_poster)
//            val MM = MMovie.create()
//
//            MM.title = et_Title.text.toString()
//            MM.genre = spr_genre.getSelectedItem().toString()
//            MM.release = et_Release.text.toString()
//            MM.rating = java.lang.String.valueOf(rating_bar.getRating())
//            MM.deskripsi = et_Deskripsi.text.toString()
//            MM.poster = ivtoBytetoString(IVImg)
//
//            val newData = DB.child(Static.FB_DataMovie).push()
//            MM.id = newData.key
//
//            newData.setValue(MM)
//
//            et_Title.setText("")
//            spr_genre.setSelection(0)
//            et_Release.setText("")
//            rating_bar.rating = ("0.0").toFloat()
//            et_Deskripsi.setText("")
//
//            Toast.makeText(
//                activity,
//                "Movie added to the list successfully" + MM.id,
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            DB = FirebaseDatabase.getInstance().reference
//            var IVImg: ImageView = mView.findViewById(R.id.iv_poster)
//            val MTVS = MTVShow.create()
//
//            MTVS.title = et_Title.text.toString()
//            MTVS.genre = spr_genre.getSelectedItem().toString()
//            MTVS.release = et_Release.text.toString()
//            MTVS.rating = java.lang.String.valueOf(rating_bar.getRating())
//            MTVS.deskripsi = et_Deskripsi.text.toString()
//            MTVS.poster = ivtoBytetoString(IVImg)
//
//            val newData = DB.child(Static.FB_DataTVShow).push()
//            MTVS.id = newData.key
//
//            newData.setValue(MTVS)
//
//            et_Title.setText("")
//            spr_genre.setSelection(0)
//            et_Release.setText("")
//            rating_bar.rating = ("0.0").toFloat()
//            et_Deskripsi.setText("")
//
//            Toast.makeText(
//                activity,
//                "TVShow added to the list successfully" + MTVS.id,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//    }
//
//    private fun ivtoBytetoString(IV: ImageView): String? {
//        val BM = (IV.drawable as BitmapDrawable).bitmap
//        val BAOS = ByteArrayOutputStream()
//        BM.compress(Bitmap.CompressFormat.JPEG, 50, BAOS)
//        val b = BAOS.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == REQUEST_CODE_GALLERY) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val I = Intent(Intent.ACTION_PICK)
//                I.type = "image/*"
//                startActivityForResult(I, REQUEST_CODE_GALLERY)
//            } else {
//                Toast.makeText(
//                    activity,
//                    "Anda Tidak Memiliki Izin untuk Mengakses Lokasi File",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            return
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
//            val uri = data.data
//            val res: ContentResolver = context!!.contentResolver
//            try {
//                var IVImg: ImageView = mView.findViewById(R.id.iv_poster)
//                val IS = res.openInputStream(uri!!);
//                val BM = BitmapFactory.decodeStream(IS)
//                IVImg.setImageBitmap(BM)
//            } catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun updateDateInView() {
//        val myFormat = "MM-dd-yyyy" // mention the format you need
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        var etRelease: EditText = mView.findViewById(R.id.et_Release)
//        etRelease?.setText(sdf.format(cal.getTime()))
//    }
}
