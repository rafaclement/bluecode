package com.bluecoding.movieschallenge.fragments

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MovieListFragment : Fragment() {

    private var movies = ArrayList<Movie>();
    private var lastPage = 1

    private lateinit var moviesAdapter: MovieListAdapterPhone

    private lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    private var loading = false

    private var visibleItemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupMovieList()
    }

    override fun onResume() {
        super.onResume()
        loadMovies()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }

    private fun setupMovieList(){
        moviesAdapter = MovieListAdapterPhone(movies,this@MovieListFragment.requireContext())
        linearLayoutManager = LinearLayoutManager(this@MovieListFragment.context)
        rv_trending_movies.layoutManager = linearLayoutManager
        rv_trending_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun loadMovies(){
        loading = true
        pb_movies.visibility = View.VISIBLE
        var dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var startDate = Calendar.getInstance()
        startDate.set(Calendar.MONTH, 1)
        startDate.set(Calendar.DAY_OF_MONTH, 0)

        var endDate = Calendar.getInstance()
        endDate.set(Calendar.MONTH, 11)
        endDate.set(Calendar.DAY_OF_MONTH, 31)


        doAsync {
            var client =  NetworkRetrofitClient(this@MovieListFragment.requireContext())
            var responseMovies = client.redditApi.findMovies(getString(R.string.movie_api_key), dateFormat.format(startDate.time) , dateFormat.format(endDate.time), lastPage)
            var moviesResponse: retrofit2.Response<Page>? = responseMovies.execute()
            var moviesResponseBody: Page? = moviesResponse?.body()
            var lastItemCount = movies.size
            movies.addAll(moviesResponseBody!!.results as ArrayList<Movie>)

            uiThread {
                rv_trending_movies.adapter = MovieListAdapterPhone(movies, this@MovieListFragment.requireContext())
                rv_trending_movies.scrollToPosition(lastItemCount - visibleItemCount)
                moviesAdapter!!.notifyDataSetChanged()
            }
            loading = false
            pb_movies.visibility = View.INVISIBLE
        }

    }

}
