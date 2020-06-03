package me.alberto.albertoventen.screens

import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import me.alberto.albertoventen.util.*
import java.io.File

class SplashScreenViewModel(context: Context) : ViewModel() {

    private val folder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), FOLDER_NAME)
    private val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), FOLDER_NAME.plus("/${FILE_NAME}"))

    private val _status = MutableLiveData<FileDownloadState>()
    val status: LiveData<FileDownloadState>
        get() = _status

    private val _navigateToFilterFrag = MutableLiveData<Boolean>()
    val navigateToFilterFrag: LiveData<Boolean>
        get() = _navigateToFilterFrag

    private val _downloadPercent = MutableLiveData<Float>()
    val downloadPercent: LiveData<Float>
        get() = _downloadPercent

    init {
        checkFile()
    }

    private fun checkFile() {
       when{
           folder.exists() && file.exists() -> {
               _navigateToFilterFrag.value = true
               _status.value = DownloadSuccess
           }

           !file.exists() -> {
               if (!folder.exists()) folder.mkdir()
               downloadFile()
           }
       }
    }

    private fun downloadFile() {
        _status.value = Downloading
        PRDownloader.download(FILE_DOWNLOAD_URL, folder.absolutePath, FILE_NAME)
            .build()
            .setOnProgressListener { progress ->
                val percent = ((progress.currentBytes / progress.totalBytes) * 100.0F)
                _downloadPercent.value = percent
            }.start(object : OnDownloadListener{
                override fun onDownloadComplete() {
                    _status.value = DownloadSuccess
                }

                override fun onError(error: Error?) {
                    _status.value = DownloadError("Couldn't finish download.")
                }
            })
    }


    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
                return SplashScreenViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }
}