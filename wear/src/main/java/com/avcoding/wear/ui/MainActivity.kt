package com.avcoding.wear.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.avcoding.wear.databinding.ActivityMainBinding
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity(), DataClient.OnDataChangedListener {

    lateinit var binding: ActivityMainBinding
    private val dataClient by lazy { Wearable.getDataClient(this) }

    private val TAG = "WearOSTag"

    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkWatchConnection()

    }
    fun checkWatchConnection() {
        scope.launch {
            val capabilityInfo: CapabilityInfo = capabilityClient
                .getCapability("flynas_capability_testing", CapabilityClient.FILTER_REACHABLE)
                .await()

            val isConnected = capabilityInfo.nodes.isNotEmpty()
            if (isConnected) {
                // Watch is connected
                Log.d("Wearable", "Watch is connected")
                // Perform actions that require a connected watch
            } else {
                // Watch is not connected
                Log.d("Wearable", "Watch is not connected")
                // Handle the case where the watch is not connected
            }
        }
    }
    override fun onResume() {
        super.onResume()
        dataClient.addListener(this@MainActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataClient.removeListener(this@MainActivity)
    }
    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            Log.d(TAG, "DataEvent received: type=${event.type}, uri=${event.dataItem.uri}")

        }
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataItem = event.dataItem
                if (dataItem.uri.path == "/auth") {
                    val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                    val message = dataMap.getString("message")
                    val timeStamp = dataMap.getLong("timestamp")
                    binding.tvMessage.text = message + "$timeStamp"
                    // Handle the received message
                }
            }
        }
    }


    /*  override fun onCapabilityChanged(p0: CapabilityInfo) {
          Log.e(TAG, "onCapabilityChanged ${p0.nodes.size}")
      }

      override fun onDataChanged(dataEventBuffer: DataEventBuffer) {
          Log.e(TAG, "onDataChanged called")
          for (event in dataEventBuffer) {
              if (event.type == DataEvent.TYPE_CHANGED) {
                  val dataItem = event.dataItem
                  if (dataItem.uri.path == "/message_path") {
                      val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                      val message = dataMap.getString("message")
                      // Handle the received message
                  }
              }
          }
      }

      override fun onDestroy() {
          super.onDestroy()
          Wearable.getDataClient(this).removeListener(this)
      }*/
}