package com.group2.campuszonechecker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

/**
 * LocationHelper is a beginner-friendly class to retrieve the current device location
 * using the Fused Location Provider Client.
 */
class LocationHelper(context: Context) {

    // FusedLocationProviderClient is the main entry point for interacting with the
    // Google Play services location APIs.
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Retrieves the current device location.
     * @param callback A simple callback that returns the Location object or null if retrieval fails.
     */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: (Location?) -> Unit) {
        // CancellationToken is used to cancel the request if needed, though here we just need one for the API call.
        val cancellationTokenSource = CancellationTokenSource()

        // getCurrentLocation is a more modern and efficient way to get a single location fix
        // compared to requesting periodic updates.
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, // Use high accuracy for university demo
            cancellationTokenSource.token
        ).addOnSuccessListener { location: Location? ->
            // addOnSuccessListener is called when the task completes successfully.
            if (location != null) {
                Log.d("LocationHelper", "Location retrieved: ${location.latitude}, ${location.longitude}")
                callback(location)
            } else {
                // location == null can happen if location settings are off or recently reset.
                Log.d("LocationHelper", "Location is null")
                callback(null)
            }
        }.addOnFailureListener { exception ->
            // addOnFailureListener handles any errors that occurred during the asynchronous operation.
            Log.e("LocationHelper", "Error getting location: ${exception.message}")
            callback(null)
        }
    }
}
