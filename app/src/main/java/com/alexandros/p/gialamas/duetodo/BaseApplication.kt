package com.alexandros.p.gialamas.duetodo

import android.app.Application
import androidx.activity.ComponentActivity
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.alexandros.p.gialamas.duetodo.reminders.ReminderWorker
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_REMINDER_CHANNEL_DESCRIPTION
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_REMINDER_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        const val CHANNEL_ID = "dueToDo_reminder_id"
        const val ACTION_REQUEST_NOTIFICATION_PERMISSION =   // TODO { delete this }
            "com.alexandros.p.gialamas.duetodo.REQUEST_NOTIFICATION_PERMISSION"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = TASK_REMINDER_CHANNEL_NAME
            val descriptionText = TASK_REMINDER_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("BaseApplication", "Notification channel created: $CHANNEL_ID")
        }
    }
}


