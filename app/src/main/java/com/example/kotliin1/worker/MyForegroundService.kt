package com.example.kotliin1.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent

import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.kotliin1.Lession1_S3
import com.example.kotliin1.R

class MyForegroundService : Service() {


    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val stopIntent = Intent(this, MyForegroundService::class.java).apply {
            action = "STOP_CUSTOM"
        }

        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        if (intent?.action == "STOP_CUSTOM") {
            stopSelf()
            return START_NOT_STICKY
        }
        val channelId = "12"
        createNotificationChannel(channelId)

        val notifyIntent = Intent(this, Lession1_S3::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("My Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.login, "Stop", stopPendingIntent)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        // Do work
        return START_STICKY
    }

        private fun createNotificationChannel(channelId: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "My Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                val manager = getSystemService(NotificationManager::class.java)
                manager?.createNotificationChannel(channel)
            }
    }

    override fun onBind(intent: Intent?) = null
}