package com.alexandros.p.gialamas.duetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable

@Dao
interface TaskCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //TODO { be aware of the conflict strategy }
    suspend fun insertTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Update
    suspend fun updateTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Delete
    suspend fun deleteTaskCategory(taskCategoryTable: TaskCategoryTable)

}