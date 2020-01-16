package com.coreen.popularmovies.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.coreen.popularmovies.database.AppDatabase

class FavoriteAdapter constructor(private val mDb: AppDatabase)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteAdapterViewHolder>() {

    inner class FavoriteAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //
    }

    override fun onBindViewHolder(holder: FavoriteAdapterViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteAdapterViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}