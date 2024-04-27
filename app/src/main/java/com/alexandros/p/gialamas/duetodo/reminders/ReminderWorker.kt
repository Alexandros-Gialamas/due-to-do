package com.alexandros.p.gialamas.duetodo.reminders

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.alexandros.p.gialamas.duetodo.MyDueToDoApplication
import com.alexandros.p.gialamas.duetodo.util.Constants

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val taskId = inputData.getLong(Constants.REMINDER_WORKER_TASK_ID, -1L)
        val isPopAlarmSelected = inputData.getBoolean(Constants.REMINDER_WORKER_ACTIVE, false)
        if (taskId != -1L) {
            // Logic to handle reminder for the task with 'taskId'
            // This might involve showing a notification and potentially a pop-up alarm
            return Result.success()
        }
        return Result.failure()
    }
    fun scheduleReminder(taskId: Long, isPopAlarmSelected: Boolean) {

//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresBatteryNotLow(true)
//            .build()

        val workData = workDataOf(
            Constants.REMINDER_WORKER_TASK_ID to taskId,
            Constants.REMINDER_WORKER_ACTIVE to isPopAlarmSelected // Add pop-up flag to data
            )
        val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(workData) // This line adds additional data (optional)
//            .setConstraints(constraints)
            .build()
        // Enqueue the work request with WorkManager
        WorkManager.getInstance(MyDueToDoApplication.instance.applicationContext).enqueue(reminderWorkRequest)
    }
}

