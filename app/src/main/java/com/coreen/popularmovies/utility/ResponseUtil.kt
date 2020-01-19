package com.coreen.popularmovies.utility

import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.service.*

object ResponseUtil {
    fun parseMovies(movieResponse: MovieResponse) : List<Movie> {
        val results: List<MovieResult> = movieResponse.results
        var movies: ArrayList<Movie> = arrayListOf()
        results.forEach { movies.add(Movie(it.id, it.title, it.posterPath, it.overview,
                it.releaseDate, it.voteAvg.toString())) }
        return movies
    }

    fun parseTrailers(trailerResponse: TrailerResponse) : List<TrailerResult> {
        return trailerResponse.results
    }

    fun parseReviews(reviewResponse: ReviewResponse): List<ReviewResult> {
        return reviewResponse.results
    }
}