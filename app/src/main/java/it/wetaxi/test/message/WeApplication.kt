package it.wetaxi.test.message

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}