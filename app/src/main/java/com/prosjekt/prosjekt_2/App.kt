package com.prosjekt.prosjekt_2

import android.app.Application
import android.content.Context

class App:Application() {
    companion object{
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}