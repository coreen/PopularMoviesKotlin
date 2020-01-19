package com.coreen.popularmovies.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.coreen.popularmovies.R
import com.coreen.popularmovies.database.AppDatabase
import com.coreen.popularmovies.fragment.ReviewFragment
import com.coreen.popularmovies.fragment.TrailerFragment
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.utility.Constants.EXTRA_MOVIE
import com.coreen.popularmovies.utility.Constants.EXTRA_MOVIE_ID
import com.coreen.popularmovies.utility.DataUtil
import com.squareup.picasso.Picasso

/**
 * No need for explicit binding (i.e. findViewById or ButterKnife)
 *
 * https://stackoverflow.com/questions/46482018/kotlin-android-view-binding-findviewbyid-vs-butterknife-vs-kotlin-android-exten
 */
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    private lateinit var mDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var movie : Movie = intent.getParcelableExtra(EXTRA_MOVIE.value)

        title = movie.title
        Picasso.with(this).load(movie.imagePath).into(iv_poster)
        // get year only
        tv_release_date.text = movie.releaseDate!!.split("-")[0]
        tv_vote_avg.text = "Rating: " + movie.voteAvg + " / 10"
        tv_summary.text = movie.summary

        // set favorite button color and click listener
        mDb = AppDatabase.getInstance(applicationContext)
        setButtonColor(movie)
        button_favorite.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_UP -> {
                        // toggle favorite
                        DataUtil.toggleIsFavorite(mDb, movie)
                        Timber.d("Tagging movieId " + movie.id + " as favorite: " + movie.isFavorite(mDb))
                        setButtonColor(movie)
                    }
                }

                return true
            }
        })

        // fragments
        if (savedInstanceState == null) {
            var bundle = Bundle()
            bundle.putInt(EXTRA_MOVIE_ID.value, movie.id)

            var trailerFragment: Fragment = TrailerFragment()
            trailerFragment.arguments = bundle
            var reviewFragment: Fragment = ReviewFragment()
            reviewFragment.arguments = bundle

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.trailer_fragment_placeholder, trailerFragment, "trailers")
                    .add(R.id.review_fragment_placeholder, reviewFragment, "reviews")
                    .commit()
        }
    }

    private fun setButtonColor(movie: Movie) {
        // change button highlight to teal green
        if (movie.isFavorite(mDb)) {
            button_favorite.setBackgroundColor(resources.getColor(R.color.colorButtonHighlight))
        } else {
            // otherwise leave as default gray
            button_favorite.setBackgroundColor(resources.getColor(R.color.colorButtonDefault))
        }
    }
}