package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alexandros.p.gialamas.duetodo.util.Constants.TODO_TASK_TABLE

@Entity(tableName = TODO_TASK_TABLE,
    foreignKeys = [ ForeignKey(
        entity = TaskCategoryTable::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId"),
        onDelete = ForeignKey.SET_NULL)])
data class ToDoTaskTable (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val description : String,
    val taskPriority : TaskPriority,
    val isCompleted : Boolean = false, //TODO { maybe here needs var }
    val dueDate : Long? = null,
    val isPopAlarmSelected : Boolean = false, //TODO { maybe here needs var }
    val categoryId: Int? = null // foreign key to Category
    )