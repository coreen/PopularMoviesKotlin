package com.coreen.popularmovies.service

import com.coreen.popularmovies.utility.Constants.MOVIE_DB_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber

interface MovieDbApiService {
    @GET("/3/movie/popular")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(): Call<MovieResponse>

    @GET("/3/movie/{id}/videos")
    fun getTrailers(@Path("id") id: Int?): Call<TrailerResponse>

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
                    .baseUrl(MOVIE_DB_BASE_URL.value)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            Timber.d("Created retrofit object with baseUrl: " + retrofit.baseUrl())

            return retrofit.create(MovieDbApiService::class.java)
        }
    }
}