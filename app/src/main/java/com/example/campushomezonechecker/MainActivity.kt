package com.group2.campuszonechecker

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var tvStatus: TextView
    private lateinit var tvDistance: TextView
    private lateinit var btnCheckZone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Member 1: Initial UI setup
        tvStatus = findViewById(R.id.tvStatus)
        tvDistance = findViewById(R.id.tvDistance)
        btnCheckZone = findViewById(R.id.btnCheckZone)

        // Member 3: Initialize the location helper
        locationHelper = LocationHelper(this)

        // Member 3: Set button listener to fetch location
        btnCheckZone.setOnClickListener {
            tvStatus.text = "Status: Fetching location..."
            
            locationHelper.fetchCurrentLocation { location ->
                if (location != null) {
                    // Display coordinates for testing purposes
                    val lat = location.latitude
                    val lon = location.longitude
                    tvStatus.text = "Status: Location Retrieved"
                    tvDistance.text = "Lat: $lat\nLon: $lon"
                } else {
                    tvStatus.text = "Status: Failed to get location"
                    tvDistance.text = "Distance: N/A"
                }
            }
        }
    }
}
