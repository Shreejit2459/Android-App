package com.example.devicetemperature

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var batteryTempText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        batteryTempText = findViewById(R.id.batteryTempText)

        val bm = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val temp = getBatteryTemperature(bm)
            batteryTempText.text = "Battery Temp: ${temp / 10.0} °C"
        } else {
            batteryTempText.text = "Battery temperature not supported on this device."
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBatteryTemperature(bm: BatteryManager): Int {
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
    }
}
