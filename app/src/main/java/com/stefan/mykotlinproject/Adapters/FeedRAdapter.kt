package com.stefan.mykotlinproject.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stefan.mykotlinproject.Models.FeedItem
import com.stefan.mykotlinproject.R
import kotlinx.android.synthetic.main.row_item_feed.*
import kotlinx.android.synthetic.main.row_item_feed.view.*

/**
 * Created by Stefan on 5/23/2017.
 */
class FeedRAdapter(var context: Context): RecyclerView.Adapter<FeedRAdapter.FeedViewHolder>() {


    private var feedList: MutableList<FeedItem>? = null

    constructor(context: Context, list: MutableList<FeedItem>) : this(context) {
        feedList = list
    }




    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): FeedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder?, pos: Int) {
        val feedItem = feedList?.get(pos)
        holder?.message?.text = "Test"
    }

    override fun getItemCount(): Int {
        return feedList?.size as Int
    }


    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var profile = itemView.circularIV
        var image = itemView.imageView
        var message = itemView.message

    }
}