package me.alberto.albertoventen.util.extension

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.google.android.material.snackbar.Snackbar
import java.io.File

fun Context.getCarOwnersCSV(directory: String, fileName: String): File {
    return File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "$directory/$fileName")
}

fun Context.installGoogleServiceProvider() {
    try {
        ProviderInstaller.installIfNeeded(this)
    } catch (e: GooglePlayServicesRepairableException) {
        // Prompt the user to install/update/enable Google Play services.
        GoogleApiAvailability.getInstance()
            .showErrorNotification(this, e.connectionStatusCode)
    } catch (e: GooglePlayServicesNotAvailableException) {
        Toast.makeText(this, "Need to update google play services", Toast.LENGTH_LONG)
            .show()
    }
}