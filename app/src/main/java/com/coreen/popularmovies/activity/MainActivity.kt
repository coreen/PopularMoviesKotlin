package com.coreen.popularmovies.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.adapter.MovieAdapter
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.model.SortBy
import com.coreen.popularmovies.service.MovieDbApiService
import com.coreen.popularmovies.service.MovieResponse
import com.coreen.popularmovies.utility.MovieDbJsonUtil
import com.coreen.popularmovies.utility.MovieResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // default sort is by popular movies
    private val mSort : SortBy = SortBy.POPULAR
//    private val movieDbApiServe by lazy {
//        MovieDbApiService.create()
//    }

    var mGridView : GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        /**
         * Initialize after implementing BaseAdapter
         *
         * https://stackoverflow.com/questions/46151681/how-can-we-implement-base-adapter-with-kotlin-in-android
         */
        mGridView = findViewById(R.id.gridview_movie)
//        mGridView!!.adapter = MovieAdapter(
//                this,
//                Array(2) { i -> Movie("myTitle" + i.toString(), "relativeImagePath",
//                        "mySummary", "myReleaseDate", "myVoteAvg") })
        loadMovieData()
    }

    private fun loadMovieData() {
        val movieDbApiServe = MovieDbApiService.create()
        var call : Call<MovieResponse>? = null
        /**
         * switch() has been replaced with when() in Kotlin
         *
         * https://kotlinlang.org/docs/reference/control-flow.html#when-expression
         */
        when(mSort) {
            SortBy.FAVORITES -> println("NotImplementException: (TODO) show Room database contents")
            SortBy.TOP_RATED -> {
                call = movieDbApiServe.getTopRatedMovies()
            }
            else -> {
                // SortBy.POPULAR
                call = movieDbApiServe.getPopularMovies()
            }
        }
        /**
         * Setting the types in Kotlin is tricky...
         *
         * https://www.c-sharpcorner.com/article/how-to-use-retrofit-2-with-android-using-kotlin/
         */
        call!!.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Timber.d("!!!!!!! call url: " + call.request().url())
                if (response.isSuccessful) {
                    val movieResponse : MovieResponse? = response.body()
                    Timber.d(mSort.name + " movies received successfully")

                    Timber.d("page: " + movieResponse!!.page)
                    val movies : List<Movie> = MovieResponseUtil.parse(movieResponse)

                    /**
                     * Unique "this" keyword syntax
                     *
                     * https://stackoverflow.com/questions/41617042/how-to-access-activity-this-in-kotlin/41620834
                     */
                    val movieAdapter = MovieAdapter(this@MainActivity, movies)
                    mGridView!!.adapter = movieAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                // TODO (coyuen) : add error text message and show if network call fails
                Timber.e(t, "Error occurred during network call to MovieDb")
            }
        })
    }
}
