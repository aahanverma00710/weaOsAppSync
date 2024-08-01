package com.avcoding.wearosapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "YOUR_CHANNEL_ID"
        val channelName = "Your Channel Name"
        val channelDescription = "Your Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun sendNotification(context: Context) {
    val channelId = "YOUR_CHANNEL_ID"

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher) // Replace with your notification icon
        .setContentTitle("Hello World")
        .setContentText("This is a notification synced with Wear OS.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(1, notification)
}

