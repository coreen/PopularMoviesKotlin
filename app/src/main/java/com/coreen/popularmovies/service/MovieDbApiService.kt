package com.coreen.popularmovies.service

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

const val MOVIE_DB_BASE_URL = "http://api.themoviedb.org/"

interface MovieDbApiService {
    @GET("/3/movie/popular")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(): Call<MovieResponse>

    companion object {
        fun create(): MovieDbApiService {
            /**
             * Append mandatory "api_key" query param to every request
             *
             * https://stackoverflow.com/questions/32948083/is-there-a-way-to-add-query-parameter-to-every-request-with-retrofit-2
             */
            val client = OkHttpClient.Builder()
                    .addInterceptor(MovieDbInterceptor())
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(MOVIE_DB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            Timber.d("Created retrofit object with baseUrl: " + retrofit.baseUrl())

            return retrofit.create(MovieDbApiService::class.java)
        }
    }
}