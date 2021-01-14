package me.alberto.albertoventen.util.helper

import android.content.Context
import android.os.Environment
import android.util.Log
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import de.siegmar.fastcsv.reader.CsvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alberto.albertoventen.model.CarOwner
import me.alberto.albertoventen.model.FilterItem
import me.alberto.albertoventen.screens.splash.SplashScreenViewModel.ProgressListener
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CSVFileHelper(context: Context) {
    private val FOLDER_NAME = "Venten"
    private val FILE_NAME = "car_owners.csv"
    private val FILE_DOWNLOAD_URL =
        "https://drive.google.com/u/0/uc?id=1giBv3pK6qbOPo0Y02H-wjT9ULPksfBCm&export=download"
    private val folder =
        File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), FOLDER_NAME)
    private val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
        FOLDER_NAME.plus("/$FILE_NAME")
    )

    private val TAG = "CSVFileHelper"

    fun doesFileExist(): Boolean = folder.exists() && file.exists()

    fun downloadFile(listener: ProgressListener) {
        if (!folder.exists()) folder.mkdir()
        PRDownloader.download(FILE_DOWNLOAD_URL, folder.absolutePath, FILE_NAME)
            .build()
            .setOnProgressListener { progress ->
                val percent = ((progress.currentBytes / progress.totalBytes) / 100f)
                listener.progress(percent)
            }.start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                   listener.downloadComplete()
                }

                override fun onError(error: Error?) {
                    listener.onError(error)
                }
            })
    }

    suspend fun filter(file: File = this.file, predicate: FilterItem): List<CarOwner> {
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
        return filterCarOwners(list, predicate)
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
                            if (item.gender.equals(predicate.gender, ignoreCase = true) || predicate.gender.isEmpty()) {
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
        }

        return list
    }
}