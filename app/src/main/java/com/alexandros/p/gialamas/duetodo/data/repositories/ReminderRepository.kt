package com.alexandros.p.gialamas.duetodo.data.repositories

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.reminders.ReminderWorker
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Constants
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ViewModelScoped
class ReminderRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val taskRepository: TaskRepository,
    private val workManager: WorkManager,
){

    internal fun scheduleRepeatRequest(task: TaskTable, scope : CoroutineScope) {

        val checkSdk = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        val instantCalendar = when (task.dueDate) {
            null -> return
            else -> {
                if (checkSdk) Instant.ofEpochMilli(task.dueDate)
                else Calendar.getInstance().apply { timeInMillis = task.dueDate }
            }
        }
        val zonedDateTime = if (instantCalendar is Instant) ZonedDateTime.ofInstant(
            instantCalendar,
            ZoneId.systemDefault()
        ) else return


        val repeatRequest = when (task.repeatFrequency) {
            RepeatFrequency.SECONDS_30 -> when (instantCalendar) { // TODO { for test purposes to be deleted }
                is Instant -> zonedDateTime.plusSeconds(30).toInstant().toEpochMilli()
                is Calendar -> {
                    instantCalendar.add(Calendar.SECOND, 30)
                    instantCalendar.timeInMillis
                }

                else -> return
            }
            RepeatFrequency.MINUTE_1 -> when (instantCalendar) {    // TODO { for test purposes to be deleted }
                is Instant -> zonedDateTime.plusMinutes(1).toInstant().toEpochMilli()
                is Calendar -> {
                    instantCalendar.add(Calendar.MINUTE, 1)
                    instantCalendar.timeInMillis
                }

                else -> return
            }
            RepeatFrequency.DAILY -> when (instantCalendar) {
                is Instant -> zonedDateTime.plusDays(1).toInstant().toEpochMilli()
                is Calendar -> {
                    instantCalendar.add(Calendar.DAY_OF_MONTH, 1)
                    instantCalendar.timeInMillis
                }

                else -> return
            }

            RepeatFrequency.WEEKLY -> when (instantCalendar) {
                is Instant -> zonedDateTime.plusWeeks(1).toInstant().toEpochMilli()
                is Calendar -> {
                    instantCalendar.add(Calendar.DAY_OF_WEEK, 1)
                    instantCalendar.timeInMillis
                }

                else -> return
            }

            RepeatFrequency.MONTHLY -> when (instantCalendar) {
                is Instant -> zonedDateTime.plusMonths(1).toInstant().toEpochMilli()
                is Calendar -> {
//                    val daysInMonth = instantCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    instantCalendar.add(Calendar.DAY_OF_MONTH, 1)
                    instantCalendar.timeInMillis
                }

                else -> return
            }

            RepeatFrequency.YEARLY -> when (instantCalendar) {
                is Instant -> zonedDateTime.plusYears(1).toInstant().toEpochMilli()
                is Calendar -> {
//                    val isLeapYear =
//                        if (instantCalendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) 366 else 365
                    instantCalendar.add(Calendar.DAY_OF_YEAR, 1)
                    instantCalendar.timeInMillis
                }

                else -> return
            }

            RepeatFrequency.CUSTOM -> when (instantCalendar) {
                is Instant -> task.dueDate  // TODO { will implement logic later }
                is Calendar -> task.dueDate // TODO { will implement logic later }
                else -> return
            }

            else -> task.dueDate
        }

        if ((repeatRequest != null) && repeatRequest > task.dueDate) {
            val rescheduleTask = task.copy(reScheduleDate = repeatRequest)
            scope.launch(Dispatchers.IO) {
                rescheduleTask.let { taskRepository.updateTask(it) }.run {
                    SnackToastMessages.RESCHEDULE_REQUEST_ERROR.showToast(context)
                    return@launch
                }
            }
        }
    }



    internal fun scheduleReminder(task: TaskTable, scope : CoroutineScope, viewModel: TaskViewModel) {

        if (task.taskId != -1 &&
            (task.title.isNotBlank() || task.description.isNotBlank()) &&
            (task.dueDate != null || task.reScheduleDate != null)
        ) {

            scope.launch { // Launch coroutine in ViewModelScope
                viewModel.notificationPermission.collect { isGranted ->
                    if (isGranted) {
                        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                            .setInputData(workDataOf(
                                Constants.REMINDER_WORKER_TASK_ID to task.taskId,
                                Constants.REMINDER_WORKER_TASK_TITLE to task.title,
                                Constants.REMINDER_WORKER_TASK_DESCRIPTION to task.description,
                                Constants.REMINDER_WORKER_HARD_NOTIFICATION to task.dialogNotification,
                                Constants.REMINDER_WORKER_REPEAT_FREQUENCY to task.repeatFrequency.name)
                            )
                            .setConstraints(Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                            ).build()

                        WorkManager.getInstance(context).enqueueUniqueWork(
                            "reminder_${task.taskId}",
                            ExistingWorkPolicy.REPLACE,
                            workRequest
                        )
                    } else {
                        viewModel.showNotificationPermissionDialog(true) // Request permission if not granted
                        Log.d(
                            "ReminderRepository",
                            "Notification permission not granted, showing dialog"
                        )
                    }
                }
            }

            val workRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()

            val workData = workDataOf(
                Constants.REMINDER_WORKER_TASK_ID to task.taskId,
                Constants.REMINDER_WORKER_TASK_TITLE to task.title,
                Constants.REMINDER_WORKER_TASK_DESCRIPTION to task.description,
                Constants.REMINDER_WORKER_HARD_NOTIFICATION to task.dialogNotification,
                Constants.REMINDER_WORKER_REPEAT_FREQUENCY to task.repeatFrequency.name
            )

            val duration = if (task.dueDate != null && task.dueDate > System.currentTimeMillis()) {
                task.dueDate.minus(System.currentTimeMillis())
            } else {
                task.reScheduleDate?.minus(System.currentTimeMillis()) ?: return
            }
            workRequestBuilder
                .setInitialDelay(duration!!, TimeUnit.MILLISECONDS)  // TODO { asserted value }
                .setInputData(workData)
            workManager.enqueue(workRequestBuilder.build())


            if (duration < 0) {
                scope.launch(Dispatchers.IO) {
                    val updateTask = task.copy(
                        dueDate = task.reScheduleDate,
                        reScheduleDate = task.reScheduleDate?.let { task.dueDate?.let { it1 ->
                            task.reScheduleDate.plus(
                                it1
                            )
                        } })
                    updateTask.let { taskRepository.updateTask(updateTask) }
                }
            }
                        Log.d("ReminderRepository", "Reminder scheduled for task ID: ${task.taskId}")

        } else {
            run {
                scope.launch(Dispatchers.Main) {
                    SnackToastMessages.RESCHEDULE_REQUEST_ERROR.showToast(context) }
                }
//            return  // TODO { return here might cause issues }
        }

    }

    internal fun cancelScheduledTask(taskId: Int?){
        taskId.let { workManager.cancelUniqueWork("reminder_$it") }
    }

    internal fun cancelAllScheduledTask(){
        workManager.cancelAllWork()
    }

}