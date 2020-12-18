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
import com.f_a.project_moca.adapter.ATVShow
import com.f_a.project_moca.model.MTVShow
import com.f_a.project_moca.ui.detail.DetailActivity
import com.google.firebase.database.*

class TvShowFragment : Fragment() {

    lateinit var mView: View
    var fireDatabase : FirebaseDatabase? = null
    var ref : DatabaseReference? = null

    lateinit var aTVS: ATVShow
    var alTVShow: ArrayList<MTVShow>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_tv_show,container,false)

        val activity = activity as Context

        fireDatabase = FirebaseDatabase.getInstance()

        var recyclerView: RecyclerView = mView.findViewById(R.id.rv_tv_show)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager

        alTVShow = arrayListOf<MTVShow>()
        ref = FirebaseDatabase.getInstance().getReference("DataTVShow")

        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    alTVShow!!.clear()
                    for (h in p0.children){
                        val bal = h.getValue(MTVShow::class.java)
                        alTVShow?.add(bal!!)
                    }
                }
                aTVS = ATVShow(context!!,alTVShow as ArrayList<MTVShow>)
                recyclerView?.setAdapter(aTVS)
                aTVS.onItemClick = { MTVShow ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("idData", MTVShow.id)
                    intent.putExtra("type", "TVShow")
                    startActivity(intent)}
            }
        })

        return mView
    }
}
