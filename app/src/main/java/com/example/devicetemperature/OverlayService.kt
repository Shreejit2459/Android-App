package com.example.devicetemperature

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi

class OverlayService : Service() {

    private var overlayView: TextView? = null
    private lateinit var windowManager: WindowManager

    override fun onCreate() {
        super.onCreate()
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_layout, null) as TextView

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        windowManager.addView(overlayView, params)

        updateTemperature()
    }

    private fun updateTemperature() {
        val bm = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val temp = getBatteryTemperature(bm)
            overlayView?.text = "Temp: ${temp / 10.0} °C"
        } else {
            overlayView?.text = "Not supported"
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBatteryTemperature(bm: BatteryManager): Int {
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overlayView != null) {
            windowManager.removeView(overlayView)
            overlayView = null
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
