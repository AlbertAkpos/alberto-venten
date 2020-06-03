package me.alberto.albertoventen.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


const val FOLDER_NAME = "Venten"
const val FILE_NAME = "car_owners.csv"
const val FILE_DOWNLOAD_URL =
    "https://drive.google.com/u/0/uc?id=1giBv3pK6qbOPo0Y02H-wjT9ULPksfBCm&export=download"


//Download state
sealed class LoadingState
object LoadingDone : LoadingState()
data class LoadingError(val error: String) : LoadingState()
object Loading : LoadingState()


fun checkNetwork(context: Context): Boolean {

    var isConnected = false

    val connectionManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectionManager.activeNetwork
        val activeNetwork = connectionManager.getNetworkCapabilities(network) ?: return false
        isConnected = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectionManager.run {
            connectionManager.activeNetworkInfo?.run {
                isConnected = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }

    return isConnected
}

enum class Color(var color: String) {
    GREEN("Green"),
    VIOLET("Violet"),
    YELLOW("Yellow"),
    BLUE("Blue"),
    TEAL("Teal"),
    MAROON("Maroon"),
    RED("Red"),
    AQUAMARINE("Aquamarine"),
    ORANGE("Orange"),
    MAUV("Mauv"),
    PUCE("Puce"),
    INDIGO("Indigo"),
    TURQUOISE("Turquoise"),
    GOLDENROD("Goldenrod"),
    PINK("Pink"),
    FUSCIA("Fuscia"),
    CRIMSON("Crimson"),
    KHAKI("Khaki"),

}
