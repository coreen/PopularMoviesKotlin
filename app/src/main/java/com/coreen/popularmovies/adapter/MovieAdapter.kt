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

internal class MovieAdapter constructor(private val context: Context)
    : RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>() {

    private var movies: List<Movie>? = null
    private var count: Int = 0

    inner class MovieAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mMovieImageView: ImageView = view.findViewById(R.id.iv_movie_thumbnail)
        val mMovieTitle: TextView = view.findViewById(R.id.tv_movie_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MovieAdapterViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return MovieAdapterViewHolder(
                layoutInflater.inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        val selectedMovie : Movie = movies!![position]
        Picasso.with(context).load(selectedMovie.imagePath).into(holder.mMovieImageView)
        holder.mMovieTitle.text = selectedMovie.title
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