package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_TABLE
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency

@Entity(
    tableName = TASK_TABLE,
    foreignKeys = [ForeignKey(
        entity = TaskCategoryTable::class,
        parentColumns = arrayOf("categoryId"), // TaskCategoryTable
        childColumns = arrayOf("categoryId"), // TaskTable
        onDelete = ForeignKey.RESTRICT
    )
    ]
)
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0,
    val title: String,
    val description: String,
    val taskPriority: TaskPriority,
    var category: String = "",
    val createdDate: Long = System.currentTimeMillis(),
    val dueDate: Long? = null, // schedule task
    val reScheduleDate : Long? = null, // for schedule repetition
    val repeatFrequency: RepeatFrequency = RepeatFrequency.NONE,
    val dialogNotification: Boolean = false,
    var isChecked: Boolean = false, // for checking list
    var isPinned: Boolean = false,
    val categoryNone: Boolean = true, // for sorting purposes
    var categoryReminders: Boolean = false, // for sorting purposes of scheduled tasks
    val categoryId: Int? = null // foreign key to TaskCategoryTable
)
