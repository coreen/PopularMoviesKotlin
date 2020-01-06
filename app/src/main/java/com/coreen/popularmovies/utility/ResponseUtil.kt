package com.coreen.popularmovies.utility

import com.coreen.popularmovies.model.Movie
import com.coreen.popularmovies.service.MovieResponse
import com.coreen.popularmovies.service.MovieResult
import com.coreen.popularmovies.service.TrailerResponse
import com.coreen.popularmovies.service.TrailerResult

object ResponseUtil {
    fun parseMovies(movieResponse: MovieResponse) : List<Movie> {
        val results: List<MovieResult> = movieResponse.results
        var movies: ArrayList<Movie> = arrayListOf()
        results.forEach { movies.add(Movie(it.id, it.title, it.posterPath, it.overview,
                it.releaseDate, it.voteAvg.toString())) }
        return movies
    }

    fun parseTrailers(trailerResponse: TrailerResponse) : List<String> {
        val results: List<TrailerResult> = trailerResponse.results
        var trailerLinks: ArrayList<String> = arrayListOf()
        results.forEach { trailerLinks.add(it.key!!) }

        return trailerLinks
    }
}