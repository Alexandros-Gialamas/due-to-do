package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_TABLE

@Entity(tableName = TASK_TABLE,
    foreignKeys = [ ForeignKey(
        entity = TaskCategoryTable::class,
        parentColumns = arrayOf("categoryId"), // TaskCategoryTable
        childColumns = arrayOf("categoryId"), // TaskTable
        onDelete = ForeignKey.RESTRICT)])
data class TaskTable (
    @PrimaryKey(autoGenerate = true)
    val taskId : Int = 0,
    val title : String,
    val description : String,
    val taskPriority : TaskPriority,
    var isCompleted : Boolean = false,
    val dueDate : Long? = null,
    var isPopAlarmSelected : Boolean = false,
    val categoryId: Int? = null // foreign key to TaskCategoryTable
    )