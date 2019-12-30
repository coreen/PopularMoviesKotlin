package com.coreen.popularmovies.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.GridView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.adapter.MovieAdapter
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.model.SortBy
import com.coreen.popularmovies.service.MovieDbApiService
import com.coreen.popularmovies.service.MovieResponse
import com.coreen.popularmovies.utility.MovieResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // default sort is by popular movies
    private var mSort : SortBy = SortBy.POPULAR

    private var mRecyclerView: RecyclerView? = null
    private var mMovieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        mRecyclerView = findViewById(R.id.recyclerview_movie)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView!!.setHasFixedSize(true)
        /**
         * Unique "this" keyword syntax
         *
         * https://stackoverflow.com/questions/41617042/how-to-access-activity-this-in-kotlin/41620834
         */
        mMovieAdapter = MovieAdapter(this@MainActivity)
        mRecyclerView!!.adapter = mMovieAdapter

        loadMovieData(mSort)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_by, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_favorites_sort) {
            loadMovieData(SortBy.FAVORITES)
            return true
        }

        if (item!!.itemId == R.id.action_top_rated_sort) {
            loadMovieData(SortBy.TOP_RATED)
            return true
        }

        if (item!!.itemId == R.id.action_most_popular_sort) {
            loadMovieData(SortBy.POPULAR)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadMovieData(sortBy: SortBy) {
        val movieDbApiServe = MovieDbApiService.create()
        var call : Call<MovieResponse>? = null
        /**
         * switch() has been replaced with when() in Kotlin
         *
         * https://kotlinlang.org/docs/reference/control-flow.html#when-expression
         */
        when(sortBy) {
            SortBy.FAVORITES -> println("NotImplementException: (TODO) show Room database contents")
            SortBy.TOP_RATED -> {
                call = movieDbApiServe.getTopRatedMovies()
            }
            else -> {
                // SortBy.POPULAR
                call = movieDbApiServe.getPopularMovies()
            }
        }
        mSort = sortBy
        /**
         * Network call setup
         *
         * https://www.c-sharpcorner.com/article/how-to-use-retrofit-2-with-android-using-kotlin/
         */
        call!!.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Timber.d("!!!!!!! call url: " + call.request().url())
                if (response.isSuccessful) {
                    val movieResponse : MovieResponse? = response.body()
                    Timber.d(mSort.name + " movies received successfully")

                    Timber.d("movie count: " + movieResponse!!.results.size)
                    val movies : List<Movie> = MovieResponseUtil.parse(movieResponse)

                    mMovieAdapter!!.setMovieData(movies)

                }
            }

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                // TODO (coyuen) : add error text message and show if network call fails
                Timber.e(t, "Error occurred during network call to MovieDb")
            }
        })
    }
}
