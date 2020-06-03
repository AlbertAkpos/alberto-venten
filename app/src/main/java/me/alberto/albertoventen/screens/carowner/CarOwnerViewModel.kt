package me.alberto.albertoventen.screens.carowner

import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.siegmar.fastcsv.reader.CsvReader
import kotlinx.coroutines.*
import me.alberto.albertoventen.model.CarOwner
import me.alberto.albertoventen.model.FilterItem
import me.alberto.albertoventen.util.FILE_NAME
import me.alberto.albertoventen.util.FOLDER_NAME
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
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


    init {
        readFile(predicate)
    }

    private fun readFile(predicate: FilterItem) {

        if (!file.exists()) {

            return
        }

        uiScope.launch {
            try {
                val listOfCarOwners = fetchFile()
                _filterResult.value = filterCarOwners(listOfCarOwners, predicate)
            } catch (error: IOException) {

            }
        }
    }

    private suspend fun filterCarOwners(
        listOfCarOwners: List<CarOwner>,
        predicate: FilterItem
    ): List<CarOwner> {
        val list = ArrayList<CarOwner>()
        withContext(Dispatchers.IO) {
            for (item in listOfCarOwners) {
                if (item.carColor.toLowerCase() in predicate.colors.map { it.toLowerCase() }
                    || predicate.colors.isEmpty()
                ) {
                    if (item.year.toLong() in predicate.startYear..predicate.endYear) {
                        if (item.country.toLowerCase() in predicate.countries.map { it.toLowerCase() } ||
                            predicate.countries.isEmpty()
                        ) {

                            list.add(
                                CarOwner(
                                    item.id,
                                    item.firstName,
                                    item.lastName,
                                    item.email,
                                    item.country,
                                    item.carModel,
                                    item.year,
                                    item.carColor,
                                    item.gender,
                                    item.jobTitle,
                                    item.bio
                                )
                            )

                        }
                    }
                }
            }
        }

        return list
    }

    private suspend fun fetchFile(): List<CarOwner> {
        val list = ArrayList<CarOwner>()
        withContext(Dispatchers.IO) {
            try {
                val reader = CsvReader()
                reader.setFieldSeparator(',')
                reader.setContainsHeader(true)
                reader.setSkipEmptyRows(true)
                reader.parse(BufferedReader(FileReader(file.absolutePath)))
                    .use { parser ->
                        while (true) {
                            val row = parser.nextRow() ?: break
                            list.add(
                                CarOwner(
                                    row.getField(0).toLong(),
                                    row.getField(1),
                                    row.getField(2),
                                    row.getField(3),
                                    row.getField(4),
                                    row.getField(5),
                                    row.getField(6),
                                    row.getField(7),
                                    row.getField(8),
                                    row.getField(9),
                                    row.getField(10)
                                )
                            )
                        }
                    }
            } catch (error: Exception) {
                error.printStackTrace()
            }
        }
        return list
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