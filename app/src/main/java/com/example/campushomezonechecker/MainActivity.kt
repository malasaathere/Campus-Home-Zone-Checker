package com.group2.campuszonechecker

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var zoneChecker: ZoneChecker
    private lateinit var tvStatus: TextView
    private lateinit var tvDistance: TextView
    private lateinit var btnCheckZone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements (Member 1's work)
        tvStatus = findViewById(R.id.tvStatus)
        tvDistance = findViewById(R.id.tvDistance)
        btnCheckZone = findViewById(R.id.btnCheckZone)

        // Initialize Helpers (Member 3 and Member 4 integration)
        locationHelper = LocationHelper(this)
        zoneChecker = ZoneChecker()

        btnCheckZone.setOnClickListener {
            checkLocationPermissionAndGetLocation()
        }
    }

    /**
     * Checks if location permissions are granted. If not, requests them.
     * This fulfills the requirement of having a permission flow before location retrieval.
     * (Member 2's contribution)
     */
    private fun checkLocationPermissionAndGetLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1001
            )
            return
        }

        // Permission already granted, proceed to get location
        retrieveLocation()
    }

    /**
     * Uses LocationHelper to get the current device location and updates the UI.
     * Integrates ZoneChecker for final decision.
     */
    private fun retrieveLocation() {
        tvStatus.text = "Checking..."
        tvStatus.setTextColor(getColor(android.R.color.darker_gray))
        
        locationHelper.getCurrentLocation { location: Location? ->
            if (location != null) {
                // Member 4's integration: Check if location is inside the zone
                val result = zoneChecker.checkZone(location)
                
                if (result.isInside) {
                    tvStatus.text = "Inside Zone"
                    tvStatus.setTextColor(getColor(android.R.color.holo_green_dark))
                } else {
                    tvStatus.text = "Outside Zone"
                    tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                }
                
                // Display distance clearly in meters (Member 5 requirement)
                tvDistance.text = String.format(Locale.getDefault(), "%.1f m", result.distanceMeters)
            } else {
                tvStatus.text = "Location Unavailable"
                tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                tvDistance.text = "N/A"
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                retrieveLocation()
            } else {
                tvStatus.text = "Permission Denied"
                tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
