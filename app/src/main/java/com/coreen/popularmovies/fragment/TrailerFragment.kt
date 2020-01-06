package com.coreen.popularmovies.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.coreen.popularmovies.R
import com.coreen.popularmovies.service.MovieDbApiService
import com.coreen.popularmovies.service.TrailerResponse
import com.coreen.popularmovies.utility.Constants.EXTRA_MOVIE_ID
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
class TrailerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_trailer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("TrailerFragment onActivityCreated")

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
                    val trailers : List<String> = ResponseUtil.parseTrailers(trailerResponse)

                    val adapter: ArrayAdapter<String> = ArrayAdapter(activity,
                            android.R.layout.simple_list_item_1, trailers)
                    listview_trailer.adapter = adapter
                    Timber.d("Set listview adapter")
                    showListView()
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
        listview_trailer.visibility = View.INVISIBLE
        tv_no_trailers.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        listview_trailer.visibility = View.INVISIBLE
        tv_no_trailers.visibility = View.INVISIBLE
        pb_trailer_loading_indicator.visibility = View.VISIBLE
    }

    private fun showListView() {
        pb_trailer_loading_indicator.visibility = View.INVISIBLE
        tv_no_trailers.visibility = View.INVISIBLE
        listview_trailer.visibility = View.VISIBLE
    }
}