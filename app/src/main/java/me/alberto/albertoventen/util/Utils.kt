package me.alberto.albertoventen.util

const val FOLDER_NAME = "Venten"
const val FILE_NAME = "car_owners.csv"
const val FILE_DOWNLOAD_URL =
    "https://drive.google.com/u/0/uc?id=1giBv3pK6qbOPo0Y02H-wjT9ULPksfBCm&export=download"


//Download state
sealed class FileDownloadState
object DownloadSuccess : FileDownloadState()
data class DownloadError(val error: String) : FileDownloadState()
object Downloading : FileDownloadState()