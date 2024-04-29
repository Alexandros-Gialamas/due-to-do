package com.alexandros.p.gialamas.duetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//@Dao
//interface TaskWithCategoryDao {
//
//    @Query("SELECT * FROM todo_task_table LEFT JOIN category_task_table ON todo_task_table.categoryId = category_task_table.categoryId")
//    fun getAllTasksWithCategories(): Flow<List<TaskWithCategoryTable>>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE) //TODO { be aware of the conflict strategy }
//    suspend fun insertTaskWithCategory(taskWithCategoryTable: TaskWithCategoryTable)
//
//    @Update
//    suspend fun updateTaskWithCategory(taskWithCategoryTable: TaskWithCategoryTable)
//
//    @Delete
//    suspend fun deleteTaskWithCategory(taskWithCategoryTable: TaskWithCategoryTable)
//
//
//}