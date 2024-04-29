package com.alexandros.p.gialamas.duetodo.data.models

import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.TaskPriorityColor_High
import com.alexandros.p.gialamas.duetodo.ui.theme.TaskPriorityColor_Low
import com.alexandros.p.gialamas.duetodo.ui.theme.TaskPriorityColor_Medium
import com.alexandros.p.gialamas.duetodo.ui.theme.TaskPriorityColor_None


enum class TaskPriority(val color : Color) {
    HIGH(TaskPriorityColor_High),
    MEDIUM(TaskPriorityColor_Medium),
    LOW(TaskPriorityColor_Low),
    NONE(TaskPriorityColor_None)
}


