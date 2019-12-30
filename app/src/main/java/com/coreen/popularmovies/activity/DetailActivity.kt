package com.coreen.popularmovies.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.coreen.popularmovies.R
import com.coreen.popularmovies.model.Movie
import com.squareup.picasso.Picasso

/**
 * No need for explicit binding (i.e. findViewById or ButterKnife)
 *
 * https://stackoverflow.com/questions/46482018/kotlin-android-view-binding-findviewbyid-vs-butterknife-vs-kotlin-android-exten
 */
import kotlinx.android.synthetic.main.activity_detail.*

const val EXTRA_MOVIE = "movie"

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var movie : Movie = intent.getParcelableExtra(EXTRA_MOVIE)

        /**
         * Change actionbar title instead of taking up more screen space
         *
         * https://stackoverflow.com/questions/14483393/how-do-i-change-the-android-actionbar-title-and-icon
         */
        title = movie.title
//        actionBar!!.title = movie.title

        Picasso.with(this).load(movie.imagePath).into(iv_poster)
        tv_release_date.text = movie.releaseDate
        tv_vote_avg.text = movie.voteAvg
        tv_summary.text = movie.summary
    }
}