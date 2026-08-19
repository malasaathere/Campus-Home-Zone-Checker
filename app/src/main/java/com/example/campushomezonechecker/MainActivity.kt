package com.group2.campuszonechecker

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var zoneChecker: ZoneChecker
    private lateinit var tvStatus: TextView
    private lateinit var tvDistance: TextView
    private lateinit var btnCheckZone: Button

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

        // Set button listener to fetch location and check zone
        btnCheckZone.setOnClickListener {
            tvStatus.text = "Fetching..."
            
            locationHelper.fetchCurrentLocation { location ->
                if (location != null) {
                    val result = zoneChecker.checkZone(location)
                    
                    if (result.isInside) {
                        tvStatus.text = "Inside Zone"
                        tvStatus.setTextColor(getColor(android.R.color.holo_green_dark))
                    } else {
                        tvStatus.text = "Outside Zone"
                        tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                    }
                    
                    tvDistance.text = String.format("%.1f m", result.distanceMeters)
                } else {
                    tvStatus.text = "Error"
                    tvDistance.text = "N/A"
                }
            }
        }
    }
}
