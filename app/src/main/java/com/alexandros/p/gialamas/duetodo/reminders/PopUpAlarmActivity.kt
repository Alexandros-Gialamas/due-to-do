package com.alexandros.p.gialamas.duetodo.reminders

import android.app.Activity
import android.os.Bundle
import com.alexandros.p.gialamas.duetodo.util.Constants

class PopUpAlarmActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId = intent.getLongExtra(Constants.REMINDER_WORKER_TASK_ID, -1L)
        val isPopAlarmSelected = intent.getBooleanExtra(Constants.REMINDER_WORKER_ACTIVE, false)
        // Retrieve task details and display a custom UI for the pop-up alarm
        // You can include a button to snooze or dismiss the alarm
    }
}