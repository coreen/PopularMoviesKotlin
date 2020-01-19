package com.coreen.popularmovies.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.database.AppDatabase
import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.utility.DataUtil
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
        val mFavorite: ImageView = view.findViewById(R.id.iv_favorite)

        init {
            mFavorite.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_UP -> {
                            // toggle favorite
                            val selectedMovie: Movie = DataUtil.getFavoriteMovie(mDb, adapterPosition)
                            DataUtil.toggleIsFavorite(mDb, selectedMovie)
                            Timber.d("Tagging movieId " + selectedMovie.id + " as favorite: " + selectedMovie.isFavorite(mDb))
                            notifyItemChanged(adapterPosition)
                        }
                    }

                    return true
                }
            })

            // need to set this or else nothing happens on click
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Timber.d("onClick FavoriteAdapterViewHolder")
            clickHandler.onClick(DataUtil.getFavoriteMovie(mDb, adapterPosition))
        }
    }

    override fun onBindViewHolder(holder: FavoriteAdapterViewHolder, position: Int) {
        val selectedMovie: Movie = DataUtil.getFavoriteMovie(mDb, position)
        Timber.d("favorite imagePath: " + selectedMovie.imagePath)
        Picasso.with(context).load(selectedMovie.imagePath).into(holder.mMovieImageView)
        holder.mMovieTitle.text = selectedMovie.title
        holder.mFavorite.setImageResource(
                if (selectedMovie.isFavorite(mDb))
                    R.drawable.enabled_star
                else
                    R.drawable.disabled_star
        )
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