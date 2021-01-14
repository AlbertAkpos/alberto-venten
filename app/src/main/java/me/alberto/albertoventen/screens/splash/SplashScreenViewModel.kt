package me.alberto.albertoventen.screens.splash

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.downloader.Error
import me.alberto.albertoventen.util.*
import me.alberto.albertoventen.util.helper.CSVFileHelper

class SplashScreenViewModel(private val csvFileHelper: CSVFileHelper) : ViewModel() {

    private val TAG = "SplashScreenViewModel"
    private val _status = MutableLiveData<LoadingState>()
    val status: LiveData<LoadingState>
        get() = _status


    private val downloadListener = object : ProgressListener {
        override fun progress(progress: Float) {
            _status.postValue(Loading(progress))
        }

        override fun downloadComplete() {
            _status.postValue(LoadingDone)
        }

        override fun onError(error: Error?) {
            _status.postValue(LoadingError(error?.serverErrorMessage ?: "Some error occurred"))
        }
    }

    init {
        checkFile()
    }

    private fun checkFile() {
        if (csvFileHelper.doesFileExist()){
            _status.postValue(FileExist)
        } else {
            csvFileHelper.downloadFile(downloadListener)
            _status.postValue(Loading(0f))
        }
    }




    interface ProgressListener {
        fun progress(progress: Float)
        fun downloadComplete()
        fun onError(error: Error?)
    }


//    class Factory(private val context: Context) : ViewModelProvider.Factory {
//        @Suppress("unchecked_cast")
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
//                return SplashScreenViewModel(
//                    context
//                ) as T
//            }
//            throw IllegalArgumentException("Unknown viewModel class")
//        }
//    }
}