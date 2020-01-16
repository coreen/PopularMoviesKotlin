package com.coreen.popularmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Equivalent to FavoriteContract from Content Provider model
 *
 * https://github.com/coreen/popular-movies/blob/master/app/src/main/java/com/udacity/popularmovies/data/FavoriteContract.java
 */
@Entity
data class Favorite(@PrimaryKey val movieId: Int,
                    @ColumnInfo(name="title") val title: String?,
                    @ColumnInfo(name="relative_image_path") val relativeImagePath: String?,
                    @ColumnInfo(name="summary") val summary: String?,
                    @ColumnInfo(name="release_date") val releaseDate: String?,
                    @ColumnInfo(name="vote_avg") val voteAvg: String?)