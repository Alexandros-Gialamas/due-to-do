package com.alexandros.p.gialamas.duetodo.reminders

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.Constants
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_HARD_NOTIFICATION
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_REPEAT_FREQUENCY
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_DESCRIPTION
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_ID
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_TITLE
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_REMINDER_CHANNEL
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import java.util.concurrent.TimeUnit


class ReminderWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {


        val taskId = inputData.getInt(REMINDER_WORKER_TASK_ID, -1)
        val taskTitle = inputData.getString(REMINDER_WORKER_TASK_TITLE)
        val taskDescription = inputData.getString(REMINDER_WORKER_TASK_DESCRIPTION)
        val isPopAlarmSelected = inputData.getBoolean(REMINDER_WORKER_HARD_NOTIFICATION, false)
        val repeatFrequency =
            RepeatFrequency.valueOf(
                inputData.getString(REMINDER_WORKER_REPEAT_FREQUENCY) ?: RepeatFrequency.NONE.name
            )

        if (taskId != -1 && taskTitle != null && taskDescription != null) {
            showNotification(taskId, taskTitle, taskDescription, isPopAlarmSelected, context)

            if (repeatFrequency != RepeatFrequency.NONE) {
                scheduleReminder(taskId, isPopAlarmSelected, repeatFrequency)
            }
            Log.d("ReminderWorker", "Reminder triggered for task $taskId")
            return Result.success()
        }
        return Result.failure()
    }

    private fun showNotification(
        taskId: Int,
        taskTitle: String,
        taskDescription: String,
        hardNotification: Boolean,
        context: Context
    ) {

        val channelId = TASK_REMINDER_CHANNEL
        val channelName = "Task Reminders" // You can customize this
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_add_notification)
            .setContentTitle("Task Reminder")
            .setContentText(taskTitle ?: "Reminder")  // TODO { use elvis for null }
            .setSubText(taskDescription ?: "") // TODO { use elvis for null }
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // ... Add any additional actions, styles, etc. ...

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(taskId, notificationBuilder.build())

    }

    private fun scheduleReminder(
        taskId: Int,
        isPopAlarmSelected: Boolean,
        repeatFrequency: RepeatFrequency
    ) {

        val delay = when (repeatFrequency) {
            RepeatFrequency.SNOOZE_10_MINUTES,
            RepeatFrequency.SNOOZE_1_HOUR,
            RepeatFrequency.SNOOZE_2_HOUR -> return
            RepeatFrequency.DAILY -> 1
            RepeatFrequency.WEEKLY -> 7  // TODO { fix delays }
            RepeatFrequency.MONTHLY -> 30 // Approximate
            RepeatFrequency.YEARLY -> 365 // Approximate
            else -> return // No need to reschedule for non-recurring tasks
        }

        val workData = workDataOf(
            REMINDER_WORKER_TASK_ID to taskId,
            REMINDER_WORKER_HARD_NOTIFICATION to isPopAlarmSelected,
            REMINDER_WORKER_REPEAT_FREQUENCY to repeatFrequency.name
        )

        val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay.toLong(), TimeUnit.DAYS)
            .setInputData(workData)
            .build()


        WorkManager.getInstance(context).enqueue(reminderWorkRequest)
        // Enqueue the work request with WorkManager
//        WorkManager.getInstance(MyDueToDoApplication.instance.getAppContext()).enqueue(reminderWorkRequest) //TODO { work with Context }
    }
}




