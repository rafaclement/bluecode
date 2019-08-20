package com.bluecoding.movieschallenge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluecoding.movieschallenge.R
import com.bluecoding.movieschallenge.network.NetworkRetrofitClient
import com.bluecoding.movieschallenge.network.pojos.Movie
import com.bluecoding.movieschallenge.network.pojos.Page
import com.bluecoding.movieschallenge.ui.adapters.MovieListAdapterPhone
import kotlinx.android.synthetic.main.fragment_movie_list_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList


class MovieListSearchFragment : Fragment() {

    private var movies = ArrayList<Movie>();
    private var lastPage = 1

    private lateinit var moviesAdapter: MovieListAdapterPhone

    private lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    private var loading = false

    private var visibleItemCount = 0

    var textToFind = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setupMovieList()
    }

    override fun onResume() {
        super.onResume()
        loadMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list_search, container, false)
    }


    companion object {
        fun newInstance(): MovieListSearchFragment {
            return MovieListSearchFragment()
        }
    }

    private fun setupMovieList(){
        moviesAdapter = MovieListAdapterPhone(movies,this@MovieListSearchFragment.requireContext())
        linearLayoutManager = LinearLayoutManager(this@MovieListSearchFragment.context)
        rv_trending_movies_search.layoutManager = linearLayoutManager
        rv_trending_movies_search.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0){
                    visibleItemCount = linearLayoutManager.getChildCount()
                    var totalItemCount = linearLayoutManager.getItemCount()
                    var pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount && !loading) {
                        lastPage++
                        loadMovies()
                    }

                }
            }
        })
    }

    public fun loadMovies(){
        loading = true
        pb_movies_search.visibility = View.VISIBLE

        doAsync {
            var client =  NetworkRetrofitClient(this@MovieListSearchFragment.requireContext())
            var year = Calendar.getInstance().get(Calendar.YEAR).toString()
            var responseMovies = client.redditApi.findMoviesByName(getString(R.string.movie_api_key), year, lastPage, textToFind)
            var moviesResponse: retrofit2.Response<Page>? = responseMovies.execute()
            var moviesResponseBody: Page? = moviesResponse?.body()
            var lastItemCount = movies.size
            movies.addAll(moviesResponseBody!!.results as ArrayList<Movie>)

            uiThread {
                rv_trending_movies_search.adapter = MovieListAdapterPhone(movies, this@MovieListSearchFragment.requireContext())
                rv_trending_movies_search.scrollToPosition(lastItemCount - visibleItemCount)
                moviesAdapter!!.notifyDataSetChanged()
            }
            loading = false
            pb_movies_search.visibility = View.INVISIBLE
        }

    }



}
