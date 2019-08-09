package com.coreen.popularmovies.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.adapter.MovieAdapter
import com.coreen.popularmovies.model.Movie

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Initialize after implementing BaseAdapter
         *
         * https://stackoverflow.com/questions/46151681/how-can-we-implement-base-adapter-with-kotlin-in-android
         */
        var mGridView = findViewById<GridView>(R.id.gridview_movie)
        mGridView.adapter = MovieAdapter(
                this,
                Array(2) { i -> Movie("myTitle" + i.toString(), "relativeImagePath",
                        "mySummary", "myReleaseDate", "myVoteAvg") })
    }
}
