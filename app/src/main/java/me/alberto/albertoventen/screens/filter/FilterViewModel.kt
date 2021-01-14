package me.alberto.albertoventen.screens.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import me.alberto.albertoventen.model.Filters
import me.alberto.albertoventen.model.FilterItem
import me.alberto.albertoventen.network.ApiNetwork
import me.alberto.albertoventen.util.Loading
import me.alberto.albertoventen.util.LoadingDone
import me.alberto.albertoventen.util.LoadingError
import me.alberto.albertoventen.util.LoadingState
import java.io.IOException

class FilterViewModel : ViewModel() {

    init {
        Log.d("slash", "init of viewmodel")
    }

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<LoadingState>()
    val status: LiveData<LoadingState>
        get() = _status

    private val _filterList = MutableLiveData<List<FilterItem>>()
    val filterList: LiveData<List<FilterItem>>
        get() = _filterList

    init {
        getFilters()
    }

    private fun getFilters() {
        _status.value = Loading()
        viewModelScope.launch {
            try {
                _filterList.value = fetchFilters()
                _status.value = LoadingDone
            } catch (error: IOException) {
                error.printStackTrace()
                _status.value = LoadingError("Network error")
            }
        }
    }

    private suspend fun fetchFilters(): Filters {
        return withContext(Dispatchers.IO) {
            ApiNetwork.apiService.getFilter()
        }.await()
    }

    fun refresh() {
        _status.value = null
        getFilters()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
                return FilterViewModel() as T
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }

}