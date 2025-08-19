package com.example.devicetemperature

import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val batteryTempText: TextView = findViewById(R.id.batteryTempText)

        val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        val tenths = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
        val celsius = tenths / 10.0

        batteryTempText.text = "Battery Temp: $celsius °C"
    }
}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBatteryTemperature(bm: BatteryManager): Int {
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
    }
}
