package me.alberto.albertoventen.screens.carowner

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
import me.alberto.albertoventen.util.Loading
import me.alberto.albertoventen.util.LoadingDone
import me.alberto.albertoventen.util.LoadingError
import me.alberto.albertoventen.util.LoadingState
import me.alberto.albertoventen.util.helper.CSVFileHelper
import java.io.IOException

class CarOwnerViewModel(private val csvFileHelper: CSVFileHelper) :
    ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _filterResult = MutableLiveData<List<CarOwner>>()
    val filterResult: LiveData<List<CarOwner>>
        get() = _filterResult


    private val _status = MutableLiveData<LoadingState>()
    val status: LiveData<LoadingState>
        get() = _status


    fun readFile(predicate: FilterItem) {
        if (!csvFileHelper.doesFileExist()) {
            _status.value = LoadingError("Some files are missing")
            return
        }

        uiScope.launch {
            try {
                _status.value = Loading()
                _filterResult.value = csvFileHelper.filter(predicate = predicate)
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
}