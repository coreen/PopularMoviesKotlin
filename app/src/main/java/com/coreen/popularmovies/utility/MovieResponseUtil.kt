package com.coreen.popularmovies.utility

import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.service.MovieResponse
import com.coreen.popularmovies.service.Result

object MovieResponseUtil {
    fun parse(movieResponse: MovieResponse) : List<Movie> {
        val results: List<Result> = movieResponse.results
        var movies: ArrayList<Movie> = arrayListOf()
        results.forEach { movies.add(Movie(it.id, it.title, it.posterPath, it.overview,
                it.releaseDate, it.voteAvg.toString())) }
        return movies
    }
}