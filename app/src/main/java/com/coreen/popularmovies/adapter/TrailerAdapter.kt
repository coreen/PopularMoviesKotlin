package com.coreen.popularmovies.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.service.TrailerResult
import timber.log.Timber

internal class TrailerAdapter constructor(private val context: Context,
                                          private val clickHandler: TrailerAdapterOnClickHandler)
    : RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>() {

    private var trailers: List<TrailerResult>? = null
    private var count: Int = 0

    interface TrailerAdapterOnClickHandler {
        fun onClick(selectedTrailer: TrailerResult)
    }

    inner class TrailerAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mTrailerName: TextView = view.findViewById(R.id.tv_trailer_name)

        // need to set this or else nothing happens on click
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Timber.d("onClick TrailerAdapterViewHolder")
            clickHandler.onClick(trailers!![adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: TrailerAdapterViewHolder, position: Int) {
        val selectedTrailer : TrailerResult = trailers!![position]
        Timber.d("onBindViewHolder key: " + selectedTrailer.key)
        holder.mTrailerName.text = selectedTrailer.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerAdapterViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return TrailerAdapterViewHolder(
                layoutInflater.inflate(R.layout.trailer_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        if (trailers == null) {
            return 0
        }
        return count
    }

    fun setTrailerData(data: List<TrailerResult>) {
        Timber.d("setTrailerData")
        trailers = data
        count = data.size
        notifyDataSetChanged()
    }
}