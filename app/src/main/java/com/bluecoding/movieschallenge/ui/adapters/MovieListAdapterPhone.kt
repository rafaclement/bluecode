package com.bluecoding.movieschallenge.ui.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bluecoding.movieschallenge.MovieDetailActivity
import com.bluecoding.movieschallenge.R
import com.bluecoding.movieschallenge.network.pojos.Movie
import com.bluecoding.movieschallenge.ui.viewholders.RepoViewHolder
import com.bumptech.glide.Glide
import java.util.*


class MovieListAdapterPhone(private var mMovies: List<Movie>?, private val mContext: Context) :
    RecyclerView.Adapter<RepoViewHolder>(), Filterable {


    private var mMoviesFilter: List<Movie>? = null
    private var mFilter: MoviesFilter? = null

    var movies: List<Movie>?
        get() = mMovies
        set(mRepos) {
            this.mMoviesFilter = (mMovies as ArrayList<Movie>).clone() as List<Movie>
            this.mMovies = mMovies
        }

    init {
        this.mMoviesFilter = (mMovies as ArrayList<Movie>).clone() as List<Movie>
        filter

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val movie = mMoviesFilter!![position]
        holder.movieName!!.text = movie.original_title
        holder.ratingCount!!.text = "" + movie.vote_count
        holder.favouriteButton!!.setOnClickListener(View.OnClickListener {
            Toast.makeText(mContext,R.string.not_implemented, Toast.LENGTH_LONG).show()
        })
        holder.parent!!.setOnClickListener(View.OnClickListener {
            val intentDetail = Intent(mContext, MovieDetailActivity::class.java)
            intentDetail.putExtra("movie", movie)
            mContext.startActivity(intentDetail)
        })
        var base_url = mContext.getString(R.string.base_url_posters)
        Glide
            .with(mContext)
            .load(base_url + movie.poster_path)
            .centerCrop()
            .placeholder(android.R.drawable.spinner_background)
            .into(holder.movieBanner!!);

    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getItemCount(): Int {
        return mMoviesFilter!!.size
    }

    override fun getFilter(): Filter {
        if (mFilter == null) {
            mFilter = MoviesFilter()
        }

        return mFilter as MoviesFilter
    }

    private inner class MoviesFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val filterResults = Filter.FilterResults()
            if (constraint!!.length == 0) {
                filterResults.values = mMovies
            } else {

                if (constraint != null && constraint.length > 0) {
                    val tempList = ArrayList<Movie>()

                    // search content in friend list
                    for (movie in mMovies!!) {
                        if ((movie.original_title + "").contains(constraint)) {
                            tempList.add(movie)
                        }
                    }

                    filterResults.count = tempList.size
                    filterResults.values = tempList
                } else {
                    filterResults.count = mMovies!!.size
                    filterResults.values = mMovies
                }

            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            mMoviesFilter = results.values as List<Movie>
            notifyDataSetChanged()
        }
    }
}
