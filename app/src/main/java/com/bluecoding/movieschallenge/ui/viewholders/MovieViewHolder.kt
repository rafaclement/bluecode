package com.bluecoding.movieschallenge.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluecoding.movieschallenge.R


class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieName: TextView? = null
    var parent: LinearLayout? = null
    var movieBanner: ImageView? = null
    var favouriteButton: ImageView? = null
    var watchLaterButton: ImageView? = null
    var ratingCount: TextView? = null

    init {
        movieName = itemView.findViewById(R.id.tv_movie_title_item_list) as TextView
        movieBanner = itemView.findViewById(R.id.iv_movie_poster)
        ratingCount = itemView.findViewById(R.id.tv_movie_rating_item_list)
        favouriteButton = itemView.findViewById(R.id.iv_movie_favourite_item_list)
        watchLaterButton = itemView.findViewById(R.id.iv_movie_watch_item_list)

        parent = itemView.findViewById(R.id.ll_movie_item) as LinearLayout
    }
}
