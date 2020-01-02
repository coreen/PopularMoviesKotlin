package com.coreen.popularmovies.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.coreen.popularmovies.R
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.utility.Constants.EXTRA_MOVIE
import com.squareup.picasso.Picasso

/**
 * No need for explicit binding (i.e. findViewById or ButterKnife)
 *
 * https://stackoverflow.com/questions/46482018/kotlin-android-view-binding-findviewbyid-vs-butterknife-vs-kotlin-android-exten
 */
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
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
    }
}