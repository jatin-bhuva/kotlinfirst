package com.example.kotliin1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val batteryStatus: Intent? = intent
        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        if (level != -1 && scale != -1) {
            val batteryPercentage: Float = level * 100 / scale.toFloat()
            Toast.makeText(context, "Battery Percentage: $batteryPercentage%", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
