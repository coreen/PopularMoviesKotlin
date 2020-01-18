package com.coreen.popularmovies.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coreen.popularmovies.R
import com.coreen.popularmovies.activity.DetailActivity
import com.coreen.popularmovies.adapter.FavoriteAdapter
import com.coreen.popularmovies.database.AppDatabase
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.utility.Constants
import kotlinx.android.synthetic.main.fragment_favorite.*
import timber.log.Timber

class FavoriteFragment : Fragment(), FavoriteAdapter.FavoriteAdapterOnClickHandler {
    private lateinit var mDb: AppDatabase
    private var mFavoriteAdapter: FavoriteAdapter? = null

    override fun onClick(selectedMovie: Movie) {
        Timber.d("onClick FavoriteFragment")
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_MOVIE.value, selectedMovie)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // grabbing instance expensive so do only on create and ensure only single favorite fragment creation
        mDb = AppDatabase.getInstance(context!!)
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("FavoriteFragment onActivityCreated")
        showLoadingView()

        recyclerview_favorite.layoutManager = GridLayoutManager(context!!, 5)
        recyclerview_favorite.setHasFixedSize(true)
        mFavoriteAdapter = FavoriteAdapter(mDb, context!!, this@FavoriteFragment)
        recyclerview_favorite.adapter = mFavoriteAdapter

        if (mFavoriteAdapter!!.itemCount == 0) {
            showNoneMessage()
        } else {
            showRecyclerView()
        }
    }

    private fun showNoneMessage() {
        pb_favorite_loading_indicator.visibility = View.INVISIBLE
        recyclerview_favorite.visibility = View.INVISIBLE
        tv_no_favorites.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        recyclerview_favorite.visibility = View.INVISIBLE
        tv_no_favorites.visibility = View.INVISIBLE
        pb_favorite_loading_indicator.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        pb_favorite_loading_indicator.visibility = View.INVISIBLE
        tv_no_favorites.visibility = View.INVISIBLE
        recyclerview_favorite.visibility = View.VISIBLE
    }
}