package com.stefan.mykotlinproject.Models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Created by Stefan on 5/24/2017.
 */

open class FeedItem (val id: String) {


    constructor(id: String, message: String="",image: Bitmap? = null) : this(id)

    fun bitmapToBytes(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos)
        bitmap.recycle()
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun bytesToBitmap(bytes: String): Bitmap? {
        try{
            val encodeByte: ByteArray = Base64.decode(bytes, Base64.DEFAULT)
            val bm = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            return bm
        } catch (e: Exception){
            e.stackTrace
            return null
        }
    }

}