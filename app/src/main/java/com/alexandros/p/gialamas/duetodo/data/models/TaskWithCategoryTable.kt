package com.alexandros.p.gialamas.duetodo.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.alexandros.p.gialamas.duetodo.util.Constants.TODO_WITH_CATEGORY_TASK_TABLE

@Entity(tableName = TODO_WITH_CATEGORY_TASK_TABLE)
data class TaskWithCategoryTable(
    @Embedded
    val task : ToDoTaskTable,
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    val category : TaskCategoryTable?

)
