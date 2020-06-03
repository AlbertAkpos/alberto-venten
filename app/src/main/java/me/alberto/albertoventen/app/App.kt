package me.alberto.albertoventen.app

import android.app.Application
import com.downloader.PRDownloader

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        PRDownloader.initialize(this)
    }
}