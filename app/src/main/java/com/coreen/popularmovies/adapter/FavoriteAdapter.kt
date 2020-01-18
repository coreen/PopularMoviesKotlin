package com.coreen.popularmovies.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.database.AppDatabase
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.utility.ResponseUtil
import com.squareup.picasso.Picasso
import timber.log.Timber

class FavoriteAdapter constructor(private val mDb: AppDatabase,
                                  private val context: Context,
                                  private val clickHandler: FavoriteAdapterOnClickHandler)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteAdapterViewHolder>() {

    interface FavoriteAdapterOnClickHandler {
        fun onClick(selectedMovie: Movie)
    }

    inner class FavoriteAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mMovieImageView: ImageView = view.findViewById(R.id.iv_movie_thumbnail)
        val mMovieTitle: TextView = view.findViewById(R.id.tv_movie_title)

        // need to set this or else nothing happens on click
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Timber.d("onClick FavoriteAdapterViewHolder")
            clickHandler.onClick(ResponseUtil.convertFavorite(mDb.favoriteDao().getAll()[adapterPosition]))
        }
    }

    override fun onBindViewHolder(holder: FavoriteAdapterViewHolder, position: Int) {
        val selectedMovie: Movie = ResponseUtil.convertFavorite(mDb.favoriteDao().getAll()[position])
        Picasso.with(context).load(selectedMovie.imagePath).into(holder.mMovieImageView)
        holder.mMovieTitle.text = selectedMovie.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteAdapterViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return FavoriteAdapterViewHolder(
                layoutInflater.inflate(R.layout.movie_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mDb.favoriteDao().getAll().size
    }
}