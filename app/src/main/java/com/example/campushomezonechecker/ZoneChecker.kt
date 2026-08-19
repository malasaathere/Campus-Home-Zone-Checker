package com.group2.campuszonechecker

import android.location.Location

/**
 * ZoneChecker - Member 4's responsibility.
 * Handles distance calculation and zone-decision logic.
 */
class ZoneChecker {

    companion object {
        // Define the fixed reference location constants
        // These can be easily changed as needed.
        const val REFERENCE_LATITUDE = 1.2966 // Example: Near a university campus
        const val REFERENCE_LONGITUDE = 103.7764
        const val ZONE_RADIUS_METERS = 200.0
    }

    /**
     * Result object providing distance and zone status.
     */
    data class ZoneResult(
        val distanceMeters: Float,
        val isInside: Boolean
    )

    // Reference Location object
    private val referenceLocation = Location("reference").apply {
        latitude = REFERENCE_LATITUDE
        longitude = REFERENCE_LONGITUDE
    }

    /**
     * Calculates the distance from the current location to the reference point
     * and determines if it's within the specified radius.
     */
    fun checkZone(currentLocation: Location): ZoneResult {
        // Calculate distance using currentLocation.distanceTo(referenceLocation)
        val distance = currentLocation.distanceTo(referenceLocation)
        
        // Determine if inside the zone
        val isInside = distance <= ZONE_RADIUS_METERS
        
        return ZoneResult(distance, isInside)
    }
}
