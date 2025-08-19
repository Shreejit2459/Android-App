package com.example.devicetemperature

import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the TextView in your layout
        val batteryTempText = findViewById<TextView>(R.id.batteryTempText)

        // Get battery temperature
        val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        val tenths = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)

        // Show temperature in °C
        val tempC = tenths / 10.0
        batteryTempText.text = "Battery Temp: $tempC °C"
    }
}
