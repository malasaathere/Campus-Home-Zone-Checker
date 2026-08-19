package com.group2.campuszonechecker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

/**
 * LocationHelper - Member 3's responsibility.
 * A beginner-friendly helper to retrieve the current device location.
 */
class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Gets the current device location using FusedLocationProviderClient.
     * Uses HIGH_ACCURACY priority as requested.
     */
    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(callback: (Location?) -> Unit) {
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Return the Location object to the caller
                    callback(location)
                } else {
                    // Handle location == null
                    callback(null)
                }
            }
            .addOnFailureListener {
                // Handle failure
                callback(null)
            }
    }
}
