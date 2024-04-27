package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_TASK_TABLE

@Entity(tableName = REMINDER_TASK_TABLE)
data class TaskReminderTable(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val reminderType: ReminderType,
    val reminderDateTime: Long,
)
