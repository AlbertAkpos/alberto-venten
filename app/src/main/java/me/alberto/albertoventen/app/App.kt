package me.alberto.albertoventen.app

import android.app.Application
import com.downloader.PRDownloader
import me.alberto.albertoventen.di.mainModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        PRDownloader.initialize(this)
        startKoin(this, arrayListOf(mainModule))
    }
}