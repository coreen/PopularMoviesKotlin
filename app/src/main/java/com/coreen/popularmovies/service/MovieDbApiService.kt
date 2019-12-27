package com.coreen.popularmovies.service

import com.coreen.popularmovies.model.Movie
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

// retrofit requires ending backslash that can't be used in NetworkUtil.buildUrl() builder
// NOTE: path parans ignored, need to be added in GET annotation
const val MOVIE_DB_BASE_URL = "http://api.themoviedb.org/"

/**
 * Utilizing RxJava
 *
 * https://android.jlelse.eu/keddit-part-6-api-retrofit-kotlin-d309074af0
 */
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
                    .build();
            /**
             * Need to make sure on OkHttp3 to avoid getting UnsupportedOperationException on interceptors UnmodifiableCollection
             *
             * https://stackoverflow.com/questions/34674820/unsupported-operation-android-retrofit-okhttp-adding-interceptor-in-okhttpcl
             */
//            client.interceptors().add(MovieDbInterceptor())

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