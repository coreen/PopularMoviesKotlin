package com.coreen.popularmovies.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coreen.popularmovies.R
import com.coreen.popularmovies.adapter.ReviewAdapter
import com.coreen.popularmovies.service.MovieDbApiService
import com.coreen.popularmovies.service.ReviewResponse
import com.coreen.popularmovies.service.ReviewResult
import com.coreen.popularmovies.utility.Constants
import com.coreen.popularmovies.utility.ResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : Fragment() {
    private var mReviewAdapter: ReviewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerview_reviews.layoutManager = LinearLayoutManager(activity)
        mReviewAdapter = ReviewAdapter()
        recyclerview_reviews.adapter = mReviewAdapter

        if (arguments != null) {
            val movieId = arguments?.getInt(Constants.EXTRA_MOVIE_ID.value)
            loadReviewData(movieId!!)
        }
    }

    fun loadReviewData(movieId: Int) {
        showLoadingView()

        val movieDbApiServe = MovieDbApiService.create()
        val call : Call<ReviewResponse> = movieDbApiServe.getReviews(movieId)
        call.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                Timber.d("!!!!!!! fragment call url: " + call.request().url())
                if (response.isSuccessful) {
                    val reviewResponse : ReviewResponse? = response.body()
                    Timber.d("reviews for movieId " + movieId + " received successfully")

                    Timber.d("review count: " + reviewResponse!!.results.size)
                    val reviews : List<ReviewResult> = ResponseUtil.parseReviews(reviewResponse)

                    mReviewAdapter!!.setReviewData(reviews)
                    if (mReviewAdapter!!.itemCount == 0) {
                        showNoneMessage()
                    } else {
                        showListView()
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>?, t: Throwable?) {
                Timber.e(t, "Error occurred during network call to MovieDb")
                showNoneMessage()
            }
        })
    }

    private fun showNoneMessage() {
        pb_review_loading_indicator.visibility = View.INVISIBLE
        recyclerview_reviews.visibility = View.INVISIBLE
        tv_no_reviews.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        recyclerview_reviews.visibility = View.INVISIBLE
        tv_no_reviews.visibility = View.INVISIBLE
        pb_review_loading_indicator.visibility = View.VISIBLE
    }

    private fun showListView() {
        pb_review_loading_indicator.visibility = View.INVISIBLE
        tv_no_reviews.visibility = View.INVISIBLE
        recyclerview_reviews.visibility = View.VISIBLE
    }
}