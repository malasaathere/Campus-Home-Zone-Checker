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

class MainActivity : AppCompatActivity() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var tvStatus: TextView
    private lateinit var tvDistance: TextView
    private lateinit var btnCheckZone: Button
    private val zoneChecker = ZoneChecker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements (Member 1's work)
        tvStatus = findViewById(R.id.tvStatus)
        tvDistance = findViewById(R.id.tvDistance)
        btnCheckZone = findViewById(R.id.btnCheckZone)

        // Initialize LocationHelper (Member 3's responsibility)
        locationHelper = LocationHelper(this)

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
     * (Member 3's integration)
     */
    private fun retrieveLocation() {
        tvStatus.text = "Checking..."
        
        locationHelper.getCurrentLocation { location: Location? ->
            if (location != null) {
                // Member 4: Calculate distance and zone status
                val result = zoneChecker.checkZone(location)
                
                // Member 4: Determine status text
                val statusText = if (result.isInside) "Status: Inside Zone" else "Status: Outside Zone"
                tvStatus.text = statusText
                
                // Member 4: Display distance in meters
                val distanceInfo = "Distance: ${"%.2f".format(result.distanceMeters)} meters"
                tvDistance.text = distanceInfo
            } else {
                tvStatus.text = "Status: Error"
                tvDistance.text = "Distance: N/A"
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
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
