package com.bluecoding.movieschallenge

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.bluecoding.movieschallenge.fragments.MovieListFragment
import com.bluecoding.movieschallenge.fragments.MovieListSearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var movieListFragment : MovieListFragment
    private lateinit var movieListSearchFragment : MovieListSearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieListSearchFragment = MovieListSearchFragment.newInstance()
            movieListFragment = MovieListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_movie_container, MovieListFragment.newInstance(), "trendingMoviesFragment")
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()

        et_search_trending_movies.setOnEditorActionListener { v, actionId, event ->
            var text = et_search_trending_movies.editableText.toString()
            if(actionId == EditorInfo.IME_ACTION_DONE){
                if(text.length == 0){
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_movie_container, movieListFragment)
                        .commit()
                } else {

                    movieListSearchFragment.textToFind = text
                    val myFragment = supportFragmentManager.findFragmentByTag("search_frag")
                    if (myFragment == null || !myFragment!!.isVisible()) {
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_movie_container, movieListSearchFragment, "search_frag").commit()
                    } else {
                        movieListSearchFragment.loadMovies()
                    }


                }
                true
            } else {
                false
            }
        }
    }


}
