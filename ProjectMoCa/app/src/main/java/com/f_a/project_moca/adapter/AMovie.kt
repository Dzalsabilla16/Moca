package com.f_a.project_moca.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.f_a.project_moca.R
import com.f_a.project_moca.model.MMovie

class AMovie (val context: Context, alMovie: ArrayList<MMovie>) : RecyclerView.Adapter<AMovie.MovieVH>() {

    var onItemClick: ((MMovie) -> Unit)? = null
    var alMovie: ArrayList<MMovie> = alMovie

    inner class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster: ImageView
        var title: TextView
        var rating: TextView
        var detail: CardView

        init {
            poster = itemView.findViewById(R.id.iv_poster)
            title = itemView.findViewById(R.id.tv_movies)
            rating = itemView.findViewById(R.id.rating_movies)
            detail = itemView.findViewById(R.id.detail_movies)
            itemView.setOnClickListener {
                onItemClick?.invoke(alMovie[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,
            parent, false)
        return MovieVH(v)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val currentItem = alMovie[position]

        val encodeByte: ByteArray = android.util.Base64.decode(currentItem.poster, android.util.Base64.DEFAULT)
        val bM = BitmapFactory.decodeByteArray(
            encodeByte, 0,
            encodeByte.size
        )

        holder.poster.setImageBitmap(bM)
        holder.title.text = currentItem.title
        holder.rating.text = currentItem.rating
    }

    override fun getItemCount(): Int {
        return alMovie.size
    }
}