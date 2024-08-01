package com.avcoding.wear.ui

import android.util.Log
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService

class MyDataListenerService : WearableListenerService() {

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)
        Log.d("WearDataReceived", "onDataChanged ")
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataItem = event.dataItem
                if (dataItem.uri.path == "/message") { // Match the path from your phone
                    val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                    val value = dataMap.getString("key")
                    Log.d("WearDataReceived", "Received value: $value")

                    // Now you have the 'value' - use it to update your UI or perform actions
                }
            }
        }
    }
}