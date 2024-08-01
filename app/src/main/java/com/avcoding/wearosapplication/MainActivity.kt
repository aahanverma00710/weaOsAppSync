package com.avcoding.wearosapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataItem
import com.google.android.gms.wearable.DataMap
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private val TAG = "SendDataFromPhone"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun OnClickOfView(view: View) {
        // sendDataToWearable()
      //  sendNotification(this@MainActivity)
        sendMessageToWearableDevice("Hey Guys how are you?")
    }

    private fun sendMessageToWearableDevice(s: String) {
        lifecycleScope.launch {
            val dataClient: DataClient = Wearable.getDataClient(this@MainActivity)
            val putDataReq: PutDataRequest = PutDataMapRequest.create("/auth").apply {
                dataMap.putString("message", "Hello from phone!")
                dataMap.putLong("timestamp", System.currentTimeMillis())
            }.asPutDataRequest()
                .setUrgent()
           val result = dataClient.putDataItem(putDataReq).await()
            Log.d(TAG, "DataItem saved: $result")

            /**/
        }

    }


}

