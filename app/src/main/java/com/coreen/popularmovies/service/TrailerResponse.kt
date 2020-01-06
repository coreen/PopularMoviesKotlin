package com.coreen.popularmovies.service

import com.google.gson.annotations.SerializedName

/**
 * Sample data format
 *
 * {
 *      "id":297761,
 *      "results":[
 *          {
 *              "id":"58f218d69251412fa90094e1",
 *              "iso_639_1":"en",
 *              "iso_3166_1":"US",
 *              "key":"CmRih_VtVAs",
 *              "name":"Suicide Squad - Official Trailer 1 [HD]",
 *              "site":"YouTube",
 *              "size":1080,
 *              "type":"Trailer"
 *          }
 *      ]
 * }
 */
class TrailerResponse {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("results")
    var results: List<TrailerResult> = emptyList()
}

class TrailerResult {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("iso_639_1")
    var language: String? = null
    @SerializedName("iso_3166_1")
    var location: String? = null
    @SerializedName("key")
    var key: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("site")
    var site: String? = null
    @SerializedName("size")
    var size: Int = 0
    @SerializedName("type")
    var type: String? = null
}