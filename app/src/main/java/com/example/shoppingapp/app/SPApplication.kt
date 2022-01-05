package com.example.shoppingapp.app

import android.app.Application
import android.content.Context

class SPApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SPApplication.appContext = applicationContext
    }

    companion object {
        lateinit  var appContext: Context
            private set
    }
}