package com.f_a.project_moca.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.f_a.project_moca.R
import com.f_a.project_moca.adapter.AMovie
import com.f_a.project_moca.model.MMovie
import com.f_a.project_moca.ui.detail.DetailActivity
import com.google.firebase.database.*

class MovieFragment: Fragment() {

    lateinit var mView: View
    var fireDatabase : FirebaseDatabase? = null
    var ref : DatabaseReference? = null

    lateinit var aM: AMovie
    var alMovie: ArrayList<MMovie>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_movie,container,false)

        val activity = activity as Context

        fireDatabase = FirebaseDatabase.getInstance()

        var recyclerView: RecyclerView = mView.findViewById(R.id.rv_Movie)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager

        alMovie = arrayListOf<MMovie>()
        ref = FirebaseDatabase.getInstance().getReference("DataMovie")

        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    alMovie!!.clear()
                    for (h in p0.children){
                        val bal = h.getValue(MMovie::class.java)
                        alMovie?.add(bal!!)
                    }
                }
                aM = AMovie(context!!,alMovie as ArrayList<MMovie>)
                recyclerView?.setAdapter(aM)
                aM.onItemClick = { MMovie ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("idData", MMovie.id)
                    intent.putExtra("type", "Movie")
                    startActivity(intent)}

            }
        })

        return mView
    }
}
