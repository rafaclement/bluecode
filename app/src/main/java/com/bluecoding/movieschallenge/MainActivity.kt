package com.bluecoding.movieschallenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluecoding.movieschallenge.network.NetworkRetrofitClient
import com.bluecoding.movieschallenge.network.pojos.Movie
import com.bluecoding.movieschallenge.network.pojos.Page
import com.bluecoding.movieschallenge.ui.adapters.MovieListAdapterPhone
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var movies = ArrayList<Movie>();
    var lastPage = 1

    var moviesAdapter: MovieListAdapterPhone = MovieListAdapterPhone(movies, this@MainActivity)

    private lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    var loading = false

    var visibleItemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupMovieList()
    }

    override fun onResume() {

        loadMovies()
        super.onResume()
    }

    private fun setupMovieList(){
        linearLayoutManager = LinearLayoutManager(this@MainActivity)
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
            var client =  NetworkRetrofitClient(applicationContext)
            var responseMovies = client.redditApi.findMovies(getString(R.string.movie_api_key), dateFormat.format(startDate.time) , dateFormat.format(endDate.time), lastPage)
            var moviesResponse: retrofit2.Response<Page>? = responseMovies.execute()
            var moviesResponseBody: Page? = moviesResponse?.body()
            var lastItemCount = movies.size
            movies.addAll(moviesResponseBody!!.results as ArrayList<Movie>)

            uiThread {
                rv_trending_movies.adapter = MovieListAdapterPhone(movies, this@MainActivity)
                rv_trending_movies.scrollToPosition(lastItemCount - visibleItemCount)
                moviesAdapter!!.notifyDataSetChanged()
            }
            loading = false
            pb_movies.visibility = View.INVISIBLE
        }

    }
}
