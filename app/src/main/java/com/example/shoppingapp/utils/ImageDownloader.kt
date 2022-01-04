package com.example.shoppingapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shoppingapp.app.SPApplication

object ImageDownloader {
    fun download(view: ImageView, url: String) {
        Glide.with(SPApplication.appContext).load(url).into(view)
    }
}