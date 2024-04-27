package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexandros.p.gialamas.duetodo.util.Constants.CATEGORY_TASK_TABLE

@Entity(tableName = CATEGORY_TASK_TABLE)
data class TaskCategoryTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
