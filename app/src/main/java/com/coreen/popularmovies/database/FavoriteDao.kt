package com.coreen.popularmovies.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Equivalent to DataUtils and FavoriteContentProvider from Content Provider model, FavoriteDbHelper implicit
 *
 * https://github.com/coreen/popular-movies/blob/master/app/src/main/java/com/udacity/popularmovies/utilities/DataUtils.java
 * https://github.com/coreen/popular-movies/blob/master/app/src/main/java/com/udacity/popularmovies/data/FavoriteContentProvider.java
 */
@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE movieId LIKE :movieId")
    fun findById(movieId: Int): Favorite?

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}