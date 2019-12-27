package com.coreen.popularmovies.service

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

const val API_KEY_QUERY = "api_key"

class MovieDbInterceptor : Interceptor {
    /**
     * No exception throwing required in Kotlin, but here for informational purposes
     *
     * https://stackoverflow.com/questions/36528515/throws-exception-in-a-method-with-kotlin
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response {
        var request : Request = chain.request()
        val url : HttpUrl = request.url()
                .newBuilder()
                .addQueryParameter(API_KEY_QUERY, com.coreen.popularmovies.BuildConfig.apikey)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}