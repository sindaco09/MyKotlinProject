package com.stefan.mykotlinproject.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.stefan.mykotlinproject.R

import java.io.IOException

/**
 * Created by Stefan on 5/23/2017.
 */

//class ListingRecyclerAdapter : RecyclerView.Adapter<ListingRecyclerAdapter.ListingViewHolder> {
//
//
//    inner class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        // This ViewHolder is the layout for one Recyclable item
//        // On Click Listeners is preference
//
//        private val price: TextView = itemView. as TextView
//        private val title: TextView
//        private val user: TextView
//        private val chats: TextView
//        private val status: TextView
//        private val image: ImageView
//        private val mainLayout: RelativeLayout
//        private val userLayout: RelativeLayout
//
//        init {
//            title = itemView.findViewById(R.id.title) as TextView
//            user = itemView.findViewById(R.id.sellerName) as TextView
//            status = itemView.findViewById(R.id.status) as TextView
//            chats = itemView.findViewById(R.id.chats) as TextView
//            image = itemView.findViewById(R.id.image) as ImageView
//            mainLayout = itemView.findViewById(R.id.relativeLayout) as RelativeLayout
//            userLayout = itemView.findViewById(R.id.status_container) as RelativeLayout
//        }
//    }
//
//    private var mContext: Context? = null
//    private var mListings: List<String>? = null
//
//    constructor(context: Context) {
//        mContext = context
//    }
//
//    constructor(context: Context, list: List<String>) {
//        mContext = context
//        mListings = list
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingRecyclerAdapter.ListingViewHolder {
//        val view = LayoutInflater.from(mContext).inflate(R.layout.item_listing, parent, false)
//        return ListingViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ListingRecyclerAdapter.ListingViewHolder, position: Int) {
//        val listing = mListings!![position]
//        holder.title.setText(listing.getTitle())
//        holder.price.setText(listing.getPrice())
//        holder.user.setText(listing.getCreator())
//        if (listing.getPhotoUrl() != null) {
//            try {
//                val imageBitmap = decodeFromFirebaseBase64(listing.getPhotoUrl())
//                holder.image.setImageBitmap(imageBitmap)
//            } catch (e: IOException) {
//                Log.e(TAG, "Error with bitmap", e)
//            }
//
//        } else {
//            holder.image.setImageDrawable(mContext!!.resources.getDrawable(R.drawable.ic_visibility_off_black_48dp))
//        }
//
//        if (listing.getCreatorId().equals(UserModel.uid)) {
//            holder.userLayout.visibility = View.VISIBLE
//            val status = if (listing.getIsActive()) "Active" else "Closed"
//            holder.status.text = status
//            val chats = if (listing.getChatRoomIDs() != null) String.valueOf(listing.getChatRoomIDs().size()) else "0"
//            holder.chats.text = chats
//        } else {
//            holder.userLayout.visibility = View.GONE
//        }
//
//        holder.mainLayout.setOnClickListener(listener(listing))
//    }
//
//    private fun listener(listing: Listing): View.OnClickListener {
//        return View.OnClickListener {
//            try {
//                // Navigate to the activity
//                val userId = UserModel.uid
//                val createrId = listing.getCreatorId()
//                Log.d(TAG, "userId: $userId, creatorId: $createrId")
//                val isOwner = createrId == userId
//                val intent = Intent(mContext, ListingDetailsActivity::class.java)
//                intent.putExtra(ListingDetailsActivity.LISTING, listing.getId())
//                intent.putExtra(ListingDetailsActivity.IS_OWNER, isOwner)
//                mContext!!.startActivity(intent)
//                Log.d(TAG, "listing: " + listing.getTitle())
//            } catch (e: NullPointerException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun setList(list: List<Listing>) {
//        mListings = list
//        notifyDataSetChanged()
//    }
//
//
//    override fun getItemCount(): Int {
//        return mListings!!.size
//    }
//
//    companion object {
//
//        private val TAG = ListingRecyclerAdapter::class.java.simpleName
//
//        @Throws(IOException::class)
//        private fun decodeFromFirebaseBase64(image: String): Bitmap {
//            val decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT)
//            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
//        }
//    }
//}