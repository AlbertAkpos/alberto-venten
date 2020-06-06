package me.alberto.albertoventen.screens.carowner

import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.alberto.albertoventen.model.CarOwner
import me.alberto.albertoventen.model.FilterItem
import me.alberto.albertoventen.util.*
import java.io.File
import java.io.IOException

class CarOwnerViewModel(private val context: Context, private val predicate: FilterItem) :
    ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _filterResult = MutableLiveData<List<CarOwner>>()
    val filterResult: LiveData<List<CarOwner>>
        get() = _filterResult

    private val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
        FOLDER_NAME.plus("/$FILE_NAME")
    )

    private val _status = MutableLiveData<LoadingState>()
    val status: LiveData<LoadingState>
        get() = _status


    init {
        readFile(predicate)
    }

    private fun readFile(predicate: FilterItem) {

        if (!file.exists()) {
            _status.value = LoadingError("Some files are missing")
            return
        }

        uiScope.launch {
            try {
                _status.value = Loading
                val listOfCarOwners = FilterObject.fetchFile(file)
                _filterResult.value = FilterObject.filterCarOwners(listOfCarOwners, predicate)
                _status.value = LoadingDone
            } catch (error: IOException) {
                _status.value = LoadingError("An error occurred")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    class Factory(private val predicate: FilterItem, private val context: Context) :
        ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarOwnerViewModel::class.java)) {
                return CarOwnerViewModel(context, predicate) as T
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }
}