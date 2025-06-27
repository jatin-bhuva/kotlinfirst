package com.example.kotliin1.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DemoWorker(context:Context, params: WorkerParameters):Worker(context, params) {
    override fun doWork(): Result {
        performWork()
        return Result.success()
    }
    private fun performWork(){
        Thread.sleep(3000)
        Log.d("TEST WORKER", "Task Completed")
    }
}