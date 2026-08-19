package com.group2.campuszonechecker

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class MainActivity : Activity() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var zoneChecker: ZoneChecker
    private lateinit var tvStatus: TextView
    private lateinit var tvDistance: TextView
    private lateinit var btnCheckZone: Button

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI Setup
        tvStatus = findViewById(R.id.tvStatus)
        tvDistance = findViewById(R.id.tvDistance)
        btnCheckZone = findViewById(R.id.btnCheckZone)

        // Initialize helpers
        locationHelper = LocationHelper(this)
        zoneChecker = ZoneChecker()

        // Set button listener to initiate the complete workflow
        btnCheckZone.setOnClickListener {
            checkPermissionAndFetchLocation()
        }
    }

    /**
     * Checks if location permission is granted.
     * If not, requests it. If yes, proceeds to fetch location.
     */
    private fun checkPermissionAndFetchLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission from user
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, proceed
            performLocationCheck()
        }
    }

    /**
     * Handles the result of the permission request.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted by user
                performLocationCheck()
            } else {
                // Permission denied
                tvStatus.text = "Permission Denied"
                tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                Toast.makeText(this, "Location permission is required to check zone", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * The core workflow: Fetch location -> Calculate distance -> Update UI
     */
    private fun performLocationCheck() {
        tvStatus.text = "Fetching..."
        tvStatus.setTextColor(getColor(android.R.color.darker_gray))
        
        locationHelper.fetchCurrentLocation { location ->
            if (location != null) {
                // Member 4's logic: Check if location is inside the radius
                val result = zoneChecker.checkZone(location)
                
                if (result.isInside) {
                    tvStatus.text = "Inside Zone"
                    tvStatus.setTextColor(getColor(android.R.color.holo_green_dark))
                } else {
                    tvStatus.text = "Outside Zone"
                    tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                }
                
                // Display distance clearly in meters
                tvDistance.text = String.format(Locale.getDefault(), "%.1f m", result.distanceMeters)
            } else {
                // Handle location unavailable/null or request errors
                tvStatus.text = "Error"
                tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                tvDistance.text = "N/A"
                Toast.makeText(this, "Location unavailable. Please check GPS.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
