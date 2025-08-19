package com.example.devicetemperature

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.BatteryManager
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView

class OverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: TextView? = null

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        overlayView = TextView(this).apply {
            textSize = 16f
            setPadding(20, 20, 20, 20)
            setBackgroundColor(0x88FFFFFF.toInt())
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 50
            y = 50
        }

        windowManager?.addView(overlayView, params)

        updateTemperature()
    }

    private fun updateTemperature() {
        val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        val tenths = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
        val celsius = tenths / 10.0
        overlayView?.text = "Battery Temp: $celsius °C"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overlayView != null) windowManager?.removeView(overlayView)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
