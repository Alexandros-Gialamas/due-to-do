package com.alexandros.p.gialamas.duetodo.util

import kotlin.time.Duration

object Constants {

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
    const val PREFERENCE_KEY = "sort_state"


    const val REMINDER_WORKER_TASK_ID = "TASK_ID"
    const val REMINDER_WORKER_ACTIVE = "IS_POP_ALARM_SELECTED"

    const val MAX_TASK_TITLE_LENGTH = 20

    const val KEYBOARD_DELAY : Long = 70

    const val ICON_ROTATION : Float = 30f
    const val ICON_NO_ROTATION : Float = 0f

}