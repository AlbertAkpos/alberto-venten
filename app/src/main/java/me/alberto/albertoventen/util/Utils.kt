package me.alberto.albertoventen.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


//Download state
sealed class LoadingState
object LoadingDone : LoadingState()
data class LoadingError(val error: String) : LoadingState()
data class Loading(val progress: Float = 0f) : LoadingState()
object FileExist : LoadingState()


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
    PURPLE("Purple")

}
