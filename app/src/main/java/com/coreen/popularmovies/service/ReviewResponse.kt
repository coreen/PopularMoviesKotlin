package com.coreen.popularmovies.service

import com.google.gson.annotations.SerializedName

/**
 * Sample data
 *
 * {
 *      "id":297761,
 *      "page":1,
 *      "results":[
 *          {
 *              "author":"Gimly",
 *              "content":"Some semi-interesting visuals and a few characters I'd like to get to
 *                  know, but an absolute mess of a movie. The thing feels like a trailer, or a
 *                  clipshow, or a music video or some other sort of two-hour long promotional
 *                  material for the actual _Suicide Squad_ that comes out
 *                  later.\r\n\r\n_Final rating:★★ - Had some things that appeal to me, but a poor
 *                  finished product._",
 *              "id":"5831921492514162c0023889",
 *              "url":"https://www.themoviedb.org/review/5831921492514162c0023889"
 *          },
 *          {
 *              "author":"ColinJ",
 *              "content":"Aka NEEDLE DROP: THE MOVIE\r\n\r\nSUICIDE SQUAD is a mess. But an
 *                  entertaining, well-cast mess.",
 *              "id":"58a51120925141655f001c46",
 *              "url":"https://www.themoviedb.org/review/58a51120925141655f001c46"
 *          }
 *      ]
 * }
 */
class ReviewResponse {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("results")
    var results: List<ReviewResult> = emptyList()
}

class ReviewResult {
    @SerializedName("author")
    var author: String? = null
    @SerializedName("content")
    var content: String? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("url")
    var url: String? = null
}