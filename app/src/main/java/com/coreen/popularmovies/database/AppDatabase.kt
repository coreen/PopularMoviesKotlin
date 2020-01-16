package com.coreen.popularmovies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Architecture components instead of Content Provider for internal database
 *
 * https://developer.android.com/training/data-storage/room
 */
@Database(entities = arrayOf(Favorite::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    /**
     * Singleton with activity example
     *
     * https://android--code.blogspot.com/2019/02/android-kotlin-room-singleton-example.html
     */
    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context:Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "roomdb")
                        .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}