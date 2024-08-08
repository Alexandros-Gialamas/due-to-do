package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_TABLE
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
    var isChecklist: Boolean = false, // for checking list
    var isPinned: Boolean = false,
    val categoryNone: Boolean = true, // for sorting purposes
    var categoryReminders: Boolean = false, // for sorting purposes of scheduled tasks
    val categoryId: Int? = null, // foreign key to TaskCategoryTable
    @TypeConverters(CheckListItemConverter::class)
    val checkListItem: List<CheckListItem>
)

data class CheckListItem(
    var taskDescription: String,
    var isCompleted: Boolean = false
)

class CheckListItemConverter {
    @TypeConverter
    fun fromCheckList(checklist: List<CheckListItem>): String {
        val gson = Gson()
        return gson.toJson(checklist)
    }

    @TypeConverter
    fun toCheckList(checklistString: String): List<CheckListItem> {
        val gson = Gson()
        val type = object : TypeToken<List<CheckListItem>>() {}.type
        return gson.fromJson(checklistString, type)
    }
}
