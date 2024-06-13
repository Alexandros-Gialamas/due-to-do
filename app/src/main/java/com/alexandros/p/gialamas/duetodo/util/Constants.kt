package com.alexandros.p.gialamas.duetodo.util

import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R

object Constants {

    // Selected Category State
    const val NONE = "allOrNoCategories"
    const val REMINDERS = "tasksWithDueDates"

    const val TASK_TABLE = "task_table"
    const val CATEGORY_TASK_TABLE = "category_task_table"
    const val TODO_WITH_CATEGORY_TASK_TABLE = "todo_with_category_task_table"
    const val REMINDER_TASK_TABLE = "reminder_task_table"
    const val DATABASE_NAME = "todo_database"

    const val HOME_SCREEN = "home/{action}"
    const val HOME_SCREEN_ARGUMENT_KEY = "action"
    const val TASK_SCREEN = "task/{taskId}"
    const val TASK_SCREEN_ARGUMENT_KEY = "taskId"


    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_PRIORITY_KEY = "priority_sort_state"
    const val PREFERENCE_DATE_KEY = "date_sort_state"
    const val PREFERENCE_CATEGORY_KEY = "category_state"


    const val REMINDER_WORKER_TASK_ID = "task_id"
    const val REMINDER_WORKER_TASK_TITLE = "task_title"
    const val REMINDER_WORKER_TASK_DESCRIPTION = "task_description"
    const val REMINDER_WORKER_HARD_NOTIFICATION = "hard_notification"
    const val REMINDER_WORKER_REMINDER = "reminder_worker"
    const val TASK_REMINDER_CHANNEL_NAME = "dueToDo_task_reminders"
    const val TASK_REMINDER_CHANNEL_DESCRIPTION = "dueToDo_notification_channel"
    const val REMINDER_WORKER_REPEAT_FREQUENCY = "repeat_frequency"

    const val MAX_TASK_TITLE_LENGTH = 20

    const val KEYBOARD_DELAY : Long = 70

    const val ICON_ROTATION : Float = 30f
    const val ICON_NO_ROTATION : Float = 0f


    const val SECONDS_30 = 30000L
    const val MINUTES_1 = 1 * 60 * 1000L
    const val MINUTES_5 = 8 * 60 * 1000L
    const val MINUTES_10 = 10 * 60 * 1000L
    const val HOUR_1 = 60 * 60 * 1000L
    const val HOUR_2 = 120 * 60 * 1000L
    const val HOUR_6 = 360 * 60 * 1000L
    const val HOUR_8 = 480 * 60 * 1000L
    const val HOUR_12 = 720 * 60 * 1000L
    const val DAY_1 = 24 * 60 * 60 * 1000L
    const val WEEK_1 = 7 * 24 * 60 * 60 * 1000L
}