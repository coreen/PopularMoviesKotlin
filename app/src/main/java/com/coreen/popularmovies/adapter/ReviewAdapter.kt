package com.coreen.popularmovies.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coreen.popularmovies.R
import com.coreen.popularmovies.service.ReviewResult

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>() {
    private var reviews: List<ReviewResult>? = null
    private var count: Int = 0

    inner class ReviewAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mContent: TextView
        private val mAuthor: TextView

        init {
            mContent = view.findViewById(R.id.tv_review_content)
            mAuthor = view.findViewById(R.id.tv_review_author)
        }

        fun bind(position: Int) {
            mContent.text = reviews!![position].content
            mAuthor.text = reviews!![position].author
        }
    }

    override fun onBindViewHolder(holder: ReviewAdapterViewHolder, position: Int) {
        holder.bind(holder.adapterPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapterViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return ReviewAdapterViewHolder(
                layoutInflater.inflate(R.layout.review_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        if (reviews == null) {
            return 0
        }
        return count
    }

    fun setReviewData(data: List<ReviewResult>) {
        reviews = data
        count = data.size
        notifyDataSetChanged()
    }
}