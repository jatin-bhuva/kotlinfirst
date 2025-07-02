package com.example.kotliin1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.kotliin1.worker.DemoWorker
import com.example.kotliin1.worker.MyForegroundService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class Lession1_S3 : AppCompatActivity() {

    private lateinit var  btnTop :LinearLayout
    private lateinit var btnCenter:LinearLayout
    private lateinit var btnBottom :Button

    private lateinit var topTxt :TextView
    private lateinit var L1: LinearLayout
    private lateinit var L2 :LinearLayout
    private lateinit var R1 :LinearLayout
    private lateinit var R2 :LinearLayout

    private val workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lession1_s3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeVariables()
        doWork()

        val serviceIntent = Intent(this, MyForegroundService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        btnTop.setOnClickListener {
            L2.visibility = if (L2.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            R2.visibility =  if (R2.visibility == View.VISIBLE) View.GONE else View.VISIBLE

            val serviceIntent1 = Intent(this, MyForegroundService::class.java)
            stopService(serviceIntent1)
        }

        btnCenter.setOnClickListener{
            L1.visibility = View.VISIBLE;
            L2.visibility = View.VISIBLE;
            R1.visibility = View.VISIBLE;
            R2.visibility = View.VISIBLE
        }
        btnBottom.setOnClickListener{
            L1.visibility = if(L1.visibility == View.VISIBLE) View.GONE else View.VISIBLE;
            R1.visibility = if(R1.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
        lifecycleScope.launch() {
            timerFlow.collect { value ->
                topTxt.text = value.toString()
                Log.d("TIMER", "Countdown: $value")
            }
        }

    }

    private fun initializeVariables(){
         btnTop = findViewById(R.id.btnTop);
         btnCenter = findViewById(R.id.view);
         btnBottom = findViewById(R.id.btnBottom);
         L1 = findViewById(R.id.linearLayout);
         L2 = findViewById(R.id.linearLayout3);
         R1 = findViewById(R.id.linearLayout2);
         topTxt= findViewById(R.id.topTxt)
         R2 = findViewById(R.id.linearLayout4);
    }

   private fun doWork(){
        val request = OneTimeWorkRequest.Builder(DemoWorker::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
        workManager.enqueue(request)

       workManager.getWorkInfoByIdLiveData(request.id).observe(this){
           if(it!=null){
               printStatus(it.state.name)
           }
       }
    }

    private fun printStatus(name: String) {
    Log.d("FILE",name)
    }

    private val timerFlow = flow {
        var count = 10
        while (count>0) {
            emit(count)
            count--
            delay(1000)  // wait 1 second
        }
    }
}