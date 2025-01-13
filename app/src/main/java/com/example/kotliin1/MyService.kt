package com.example.kotliin1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class MyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service started",Toast.LENGTH_LONG).show()
        return START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service stopped",Toast.LENGTH_LONG).show()
        super.onDestroy()
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}