package com.alexandros.p.gialamas.duetodo.reminders

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexandros.p.gialamas.duetodo.BaseApplication
import com.alexandros.p.gialamas.duetodo.MainActivity
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_HARD_NOTIFICATION
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_REPEAT_FREQUENCY
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_DESCRIPTION
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_ID
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_TITLE
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ReminderWorker @Inject constructor(
    @ApplicationContext private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {


        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val taskId =
            inputData.getInt(REMINDER_WORKER_TASK_ID, -1)   // TODO { revisit reminder values }
        val taskTitle = inputData.getString(REMINDER_WORKER_TASK_TITLE)
        val taskDescription = inputData.getString(REMINDER_WORKER_TASK_DESCRIPTION)
        val isPopAlarmSelected = inputData.getBoolean(REMINDER_WORKER_HARD_NOTIFICATION, false)
        val repeatFrequency =
            RepeatFrequency.valueOf(
                inputData.getString(REMINDER_WORKER_REPEAT_FREQUENCY) ?: RepeatFrequency.NONE.name
            )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, BaseApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setContentTitle(taskTitle)
                .setContentText(taskDescription)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
//            .setStyle(
//                NotificationCompat.BigTextStyle().bigText(taskDescription))
//            .setLargeIcon(
//                BitmapFactory.decodeResource(
//                applicationContext.resources,
//                R.drawable.ic_account_profile))
//            .setStyle(
//                NotificationCompat.BigPictureStyle().bigPicture(
//                    BitmapFactory.decodeResource(
//                    applicationContext.resources,
//                    R.drawable.ic_sad_face)))
//            .addAction(
//                NotificationCompat.Action(
//                    R.drawable.ic_repeat,
//                    "Action",
//                    pendingIntent))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(taskId, notificationBuilder.build())
            } else {
                return Result.failure()
            }
        } else {
            notificationManager.notify(taskId, notificationBuilder.build())
        }
        return Result.success()
    }
}
//        Log.d("ReminderWorker", "Starting work for task ID: $taskId")
//        notificationManager.notify(taskId, notificationBuilder.build())
//
//        return Result.success()

//        with(NotificationManagerCompat.from(applicationContext)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                if (ActivityCompat.checkSelfPermission(
//                        applicationContext,
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    notify(taskId, notificationBuilder.build()) // Notify only if permission granted
//                    Log.d("ReminderWorker", "Notification sent for task ID: $taskId")
//                } else {
//                    Log.d("ReminderWorker", "Notification permission not granted for task ID: $taskId")
//                    // Handle the case where permission is not granted (e.g., log an error)
//                    // You should not try to request permission here, as it's not a UI context
//                    return Result.failure() // Or retry if it makes sense for your app logic
//                }
//            }
//            return Result.success()
//        }

//        with(NotificationManagerCompat.from(applicationContext)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//
////                if (ActivityCompat.checkSelfPermission(
////                        applicationContext,
////                        Manifest.permission.POST_NOTIFICATIONS
////                    ) != PackageManager.PERMISSION_GRANTED
////                ) {
////                    // TODO: Consider calling
////                    //    ActivityCompat#requestPermissions
////                    // here to request the missing permissions, and then overriding
////                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                    //                                          int[] grantResults)
////                    // to handle the case where the user grants the permission. See the documentation
////                    // for ActivityCompat#requestPermissions for more details.
//////                    return ListenableWorker.Result.retry()
////                    val permissionRequestIntent = Intent(applicationContext, BaseApplication::class.java).apply {
////                        action = BaseApplication.ACTION_REQUEST_NOTIFICATION_PERMISSION
////                    }
////
////                    permissionRequestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////
////                    applicationContext.startActivity(permissionRequestIntent)
////
////                    return Result.retry()
////                }
//                notify(Random.nextInt(), notificationBuilder.build())
//            }
//            return Result.success()
//        }
//    }
//}





