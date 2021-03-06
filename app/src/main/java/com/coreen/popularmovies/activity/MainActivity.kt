package com.coreen.popularmovies.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.coreen.popularmovies.R
import com.coreen.popularmovies.adapter.MovieAdapter
import com.coreen.popularmovies.database.AppDatabase
import com.coreen.popularmovies.fragment.FavoriteFragment
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.model.SortBy
import com.coreen.popularmovies.service.MovieDbApiService
import com.coreen.popularmovies.service.MovieResponse
import com.coreen.popularmovies.utility.Constants.EXTRA_MOVIE
import com.coreen.popularmovies.utility.ResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterOnClickHandler {
    // default sort is by popular movies
    private var mSort : SortBy = SortBy.POPULAR
    private var mMovieAdapter: MovieAdapter? = null
    private var mFavoriteFragment: FavoriteFragment? = null
    private lateinit var mDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        /**
         * replaced GridView setup
         *
         * https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
         */
        recyclerview_movie.layoutManager = GridLayoutManager(this, 5)
        recyclerview_movie.setHasFixedSize(true)
        /**
         * Unique "this" keyword syntax
         *
         * https://stackoverflow.com/questions/41617042/how-to-access-activity-this-in-kotlin/41620834
         */
        mDb = AppDatabase.getInstance(applicationContext)
        mMovieAdapter = MovieAdapter(mDb, this@MainActivity, this@MainActivity)
        recyclerview_movie.adapter = mMovieAdapter

        loadMovieData(mSort)
    }

    private fun loadMovieData(sortBy: SortBy) {
        showLoadingView()

        val movieDbApiServe = MovieDbApiService.create()
        var call : Call<MovieResponse>? = null
        /**
         * switch() has been replaced with when() in Kotlin
         *
         * https://kotlinlang.org/docs/reference/control-flow.html#when-expression
         */
        mSort = sortBy
        when(sortBy) {
            SortBy.FAVORITES -> {
                showFavoriteFragment()
                return
            }
            SortBy.TOP_RATED -> {
                call = movieDbApiServe.getTopRatedMovies()
            }
            else -> {
                // SortBy.POPULAR
                call = movieDbApiServe.getPopularMovies()
            }
        }
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
                    val movies : List<Movie> = ResponseUtil.parseMovies(movieResponse)

                    mMovieAdapter!!.setMovieData(movies)
                    showRecyclerView()
                }
            }

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Timber.e(t, "Error occurred during network call to MovieDb")
                showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        pb_loading_indicator.visibility = View.INVISIBLE
        recyclerview_movie.visibility = View.INVISIBLE
        tv_error_message.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        recyclerview_movie.visibility = View.INVISIBLE
        tv_error_message.visibility = View.INVISIBLE
        pb_loading_indicator.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        pb_loading_indicator.visibility = View.INVISIBLE
        tv_error_message.visibility = View.INVISIBLE
        recyclerview_movie.visibility = View.VISIBLE
        // hide favorite fragment if already loaded
        if (mFavoriteFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .hide(mFavoriteFragment)
                    .commit()
        }
    }

    private fun showFavoriteFragment() {
        pb_loading_indicator.visibility = View.INVISIBLE
        tv_error_message.visibility = View.INVISIBLE
        recyclerview_movie.visibility = View.INVISIBLE
        if (mFavoriteFragment == null) {
            mFavoriteFragment = FavoriteFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.favorite_fragment_placeholder, mFavoriteFragment, "favorites")
                    .commit()
        } else {
            // show hidden fragment
            supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .show(mFavoriteFragment)
                    .commit()
        }
    }

    override fun onClick(selectedMovie: Movie) {
        Timber.d("onClick MainActivity")
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE.value, selectedMovie)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_by, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        when (mSort) {
            SortBy.POPULAR -> menu.findItem(R.id.action_most_popular_sort).isChecked = true
            SortBy.TOP_RATED -> menu.findItem(R.id.action_top_rated_sort).isChecked = true
            SortBy.FAVORITES -> menu.findItem(R.id.action_favorites_sort).isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_favorites_sort) {
            loadMovieData(SortBy.FAVORITES)
            item.isChecked = !item.isChecked
            return true
        }

        if (item!!.itemId == R.id.action_top_rated_sort) {
            loadMovieData(SortBy.TOP_RATED)
            item.isChecked = !item.isChecked
            return true
        }

        if (item!!.itemId == R.id.action_most_popular_sort) {
            loadMovieData(SortBy.POPULAR)
            item.isChecked = !item.isChecked
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
