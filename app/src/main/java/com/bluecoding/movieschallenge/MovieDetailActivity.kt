package com.bluecoding.movieschallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluecoding.movieschallenge.network.pojos.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = getIntent().extras!!.get("movie") as? Movie

    }

    override fun onResume() {
        super.onResume()
        loadDetailContent()
    }

    fun loadDetailContent(){
        val base_image_url = getString(R.string.base_url_posters)
        Glide.with(this).load(base_image_url + movie!!.poster_path).into(iv_movie_detail_poster);
        tv_movie_detail_name.text = movie!!.original_title
        tv_movie_detail_rating_value.text = "" + movie!!.popularity
        tv_movie_detail_synopsis_value.text = movie!!.overview
    }
}
