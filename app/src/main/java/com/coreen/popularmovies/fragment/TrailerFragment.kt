package com.coreen.popularmovies.fragment

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coreen.popularmovies.R
import com.coreen.popularmovies.adapter.TrailerAdapter
import com.coreen.popularmovies.service.MovieDbApiService
import com.coreen.popularmovies.service.TrailerResponse
import com.coreen.popularmovies.service.TrailerResult
import com.coreen.popularmovies.utility.Constants.EXTRA_MOVIE_ID
import com.coreen.popularmovies.utility.Constants.VIDEO_KEY_QUERY
import com.coreen.popularmovies.utility.Constants.YOUTUBE_BASE_URL
import com.coreen.popularmovies.utility.ResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

import kotlinx.android.synthetic.main.fragment_trailer.*

/**
 * Make sure have v4 Fragment import
 *
 * https://www.raywenderlich.com/1364094-android-fragments-tutorial-an-introduction-with-kotlin#toc-anchor-007
 */
class TrailerFragment : Fragment(), TrailerAdapter.TrailerAdapterOnClickHandler {

    private var mTrailerAdapter: TrailerAdapter? = null

    override fun onClick(selectedTrailer: TrailerResult) {
        Timber.d("onClick TrailerFragment")
        val uriString = YOUTUBE_BASE_URL.value + "/?" + VIDEO_KEY_QUERY.value + "=" + selectedTrailer.key
        Timber.d("uriString: " + uriString)
        // alternate
        val uri: Uri = Uri.parse(YOUTUBE_BASE_URL.value)
                .buildUpon()
                .appendQueryParameter(VIDEO_KEY_QUERY.value, selectedTrailer.key)
                .build()
        val videoIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(uriString))
        // Youtube launch
        activity!!.intent.component = ComponentName(
                "com.google.android.youtube",
                "com.google.android.youtube.PlayerActivity")
        if (videoIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(videoIntent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trailer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("TrailerFragment onActivityCreated")

        mTrailerAdapter = TrailerAdapter(this@TrailerFragment)
        recyclerview_trailer.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        recyclerview_trailer.setHasFixedSize(true)
        recyclerview_trailer.adapter = mTrailerAdapter

        // only load if args are provided
        if (arguments != null) {
            /**
             * arguments from DetailActivity is mutable
             *
             * https://stackoverflow.com/questions/51364483/unable-to-access-parcelable-in-fragment-arguments-error-arguments-is-a-mutab
             */
            val movieId = arguments?.getInt(EXTRA_MOVIE_ID.value)
            loadTrailerData(movieId!!)
        }
    }

    private fun loadTrailerData(movieId: Int) {
        showLoadingView()

        val movieDbApiServe = MovieDbApiService.create()
        val call : Call<TrailerResponse> = movieDbApiServe.getTrailers(movieId)

        call.enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                Timber.d("!!!!!!! fragment call url: " + call.request().url())
                if (response.isSuccessful) {
                    val trailerResponse : TrailerResponse? = response.body()
                    Timber.d("trailers for movieId " + movieId + " received successfully")

                    Timber.d("trailer count: " + trailerResponse!!.results.size)
                    val trailers : List<TrailerResult> = ResponseUtil.parseTrailers(trailerResponse)

                    mTrailerAdapter!!.setTrailerData(trailers)
                    if (mTrailerAdapter!!.itemCount == 0) {
                        showErrorMessage()
                    } else {
                        showListView()
                    }
                }
            }

            override fun onFailure(call: Call<TrailerResponse>?, t: Throwable?) {
                Timber.e(t, "Error occurred during network call to MovieDb")
                showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        pb_trailer_loading_indicator.visibility = View.INVISIBLE
        recyclerview_trailer.visibility = View.INVISIBLE
        tv_no_trailers.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        recyclerview_trailer.visibility = View.INVISIBLE
        tv_no_trailers.visibility = View.INVISIBLE
        pb_trailer_loading_indicator.visibility = View.VISIBLE
    }

    private fun showListView() {
        pb_trailer_loading_indicator.visibility = View.INVISIBLE
        tv_no_trailers.visibility = View.INVISIBLE
        recyclerview_trailer.visibility = View.VISIBLE
    }
}