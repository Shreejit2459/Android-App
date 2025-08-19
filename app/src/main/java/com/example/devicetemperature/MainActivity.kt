
package com.example.devicetemperature

import android.content.Intent
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // returned from settings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TemperatureScreen(
                        readCelsius = { readBatteryTempCelsius() },
                        onEnableOverlay = { checkOverlayPermissionAndStart() },
                        onDisableOverlay = { stopService(Intent(this, OverlayService::class.java)) }
                    )
                }
            }
        }
    }

    private fun readBatteryTempCelsius(): Double {
        val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        val tenths = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
        return if (tenths != Int.MIN_VALUE) tenths / 10.0 else Double.NaN
    }

    private fun checkOverlayPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            overlayPermissionLauncher.launch(intent)
        } else {
            startService(Intent(this, OverlayService::class.java))
        }
    }
}

@Composable
fun TemperatureScreen(
    readCelsius: () -> Double,
    onEnableOverlay: () -> Unit,
    onDisableOverlay: () -> Unit
) {
    var isCelsius by remember { mutableStateOf(true) }
    var temperature by remember { mutableStateOf(readCelsius()) }

    val display = if (isCelsius || temperature.isNaN()) {
        if (temperature.isNaN()) "-- °C" else String.format("%.1f °C", temperature)
    } else {
        val f = temperature * 9 / 5 + 32
        String.format("%.1f °F", f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = display, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { isCelsius = !isCelsius }) {
            Text("Switch to " + if (isCelsius) "Fahrenheit" else "Celsius")
        }
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onEnableOverlay) { Text("Enable Overlay") }
            Button(onClick = onDisableOverlay) { Text("Disable Overlay") }
        }
    }
}
