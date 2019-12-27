package com.coreen.popularmovies.service

import com.google.gson.annotations.SerializedName

/**
 * Sample data format
 *
 * https://github.com/coreen/popular-movies/blob/7b9179f3a7befa5407bec8f8d71aa1bdcede4008/app/src/main/java/com/udacity/popularmovies/utilities/JsonUtils.java
 */
class MovieResponse {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("results")
    var results: List<Result> = emptyList()
}

class Result {
    /**
     * Convert underscore field names to Java format
     *
     * https://stackoverflow.com/questions/2370745/convert-json-style-properties-names-to-java-camelcase-names-with-gson
     */
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("adult")
    var adult: Boolean? = false
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("release_date")
    var releaseDate: String? = null
    @SerializedName("genre_ids")
    var genreIds: ArrayList<String>? = null
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("original_title")
    var originalTitle: String? = null
    @SerializedName("original_language")
    var origLang: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("popularity")
    var popularity: Double = 0.toDouble()
    @SerializedName("vote_count")
    var voteCount: Int = 0
    @SerializedName("video")
    var video: Boolean? = false
    @SerializedName("vote_average")
    var voteAvg: Double = 0.toDouble()
}