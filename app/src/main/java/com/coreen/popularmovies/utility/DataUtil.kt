package com.coreen.popularmovies.utility

import com.coreen.popularmovies.database.AppDatabase
import com.coreen.popularmovies.database.Favorite
import com.coreen.popularmovies.model.Movie

object DataUtil {
    fun getFavoriteMovie(db: AppDatabase, position: Int): Movie {
        val favorite: Favorite = db.favoriteDao().getAll()[position]
        return Movie(favorite.movieId, favorite.title, favorite.relativeImagePath,
                favorite.summary, favorite.releaseDate, favorite.voteAvg)
    }

    fun toggleIsFavorite(db: AppDatabase, movie: Movie) {
        if (isFavorite(db, movie.id)) {
            // remove, @Delete uses object, can override with @Query using id for different params
            db.favoriteDao().delete(Favorite(movie.id, movie.title, movie.relativeImagePath,
                    movie.summary, movie.releaseDate, movie.voteAvg))
        } else {
            // add
            db.favoriteDao().insert(Favorite(movie.id, movie.title, movie.relativeImagePath,
                    movie.summary, movie.releaseDate, movie.voteAvg))
        }
    }

    fun isFavorite(db: AppDatabase, movieId: Int): Boolean {
        return db.favoriteDao().findById(movieId) != null
    }
}