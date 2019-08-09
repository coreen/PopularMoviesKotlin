package com.coreen.popularmovies.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.coreen.popularmovies.model.Movie

/**
 * Kotlin uses Array<T> instead of primitive to have automatic getter, setter, and other functions defined
 *
 * https://kotlinlang.org/docs/reference/basic-types.html#arrays
 */
internal class MovieAdapter constructor(val context: Context, val movies: Array<Movie>): BaseAdapter() {
    override fun getCount(): Int {
        return movies.size
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    /**
     * Hack to stop Kotlin from complaining about NPE
     *
     * https://kotlinlang.org/docs/reference/null-safety.html
     */
    override fun getItem(position: Int): Any {
        return null!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var dummyTextView = TextView(context)
        dummyTextView.text = movies[position].title
        return dummyTextView
    }
}