package com.coreen.popularmovies.utility

import com.coreen.popularmovies.model.Movie
import com.google.gson.Gson
import org.json.JSONObject
import timber.log.Timber

const val RESULTS = "results"

object MovieDbJsonUtil {
    /**
     * Using GSON Java library to help with parsing
     *
     * https://stackoverflow.com/questions/45603236/using-gson-in-kotlin-to-parse-json-array
     */
    fun parseApiJson(responseJson: String): Array<Movie> {
        Timber.d("parse responseJson: " + responseJson)
        val response = JSONObject(responseJson)
        val movies = response.getJSONArray(RESULTS)
        Timber.d("Received " + movies.length() + " movies from network call")
        val movieJson = movies.toString()
        return Gson().fromJson(movieJson, Array<Movie>::class.java)
    }
}