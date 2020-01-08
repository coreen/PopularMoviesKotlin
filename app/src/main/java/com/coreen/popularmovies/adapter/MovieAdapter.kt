package com.coreen.popularmovies.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coreen.popularmovies.model.Movie
import android.widget.ImageView
import com.coreen.popularmovies.R
import com.squareup.picasso.Picasso
import timber.log.Timber

internal class MovieAdapter constructor(private val context: Context,
                                        private val clickHandler: MovieAdapterOnClickHandler)
    : RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>() {

    private var movies: List<Movie>? = null
    private var count: Int = 0

    interface MovieAdapterOnClickHandler {
        fun onClick(selectedMovie: Movie)
    }

    /**
     * Extend RecyclerView.ViewHolder, implement View.OnClickListener (in Kotlin)
     *
     * https://stackoverflow.com/questions/48391217/extend-and-implement-at-the-same-time-in-kotlin/48391246
     */
    inner class MovieAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mMovieImageView: ImageView = view.findViewById(R.id.iv_movie_thumbnail)
        val mMovieTitle: TextView = view.findViewById(R.id.tv_movie_title)

        // need to set this or else nothing happens on click
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Timber.d("onClick MovieAdapterViewHolder")
            clickHandler.onClick(movies!![adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        val selectedMovie : Movie = movies!![position]
        Picasso.with(context).load(selectedMovie.imagePath).into(holder.mMovieImageView)
        holder.mMovieTitle.text = selectedMovie.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MovieAdapterViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return MovieAdapterViewHolder(
                layoutInflater.inflate(R.layout.movie_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        if (movies == null) {
            return 0
        }
        // cannot return movies.size since mutable
        return count
    }

    fun setMovieData(data: List<Movie>) {
        movies = data
        count = data.size
        notifyDataSetChanged()
    }
}