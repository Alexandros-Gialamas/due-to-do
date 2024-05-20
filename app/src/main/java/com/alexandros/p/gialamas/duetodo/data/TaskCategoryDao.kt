package com.alexandros.p.gialamas.duetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskCategoryDao {

    @Query("SELECT * FROM category_task_table ORDER BY categoryId ASC")
    fun getAllCategories() : Flow<List<TaskCategoryTable>>

    @Query("SELECT * FROM category_task_table WHERE categoryId=:categoryId")
    fun getSelectedCategory(categoryId : Int) : Flow<TaskCategoryTable>

    @Query("DELETE FROM category_task_table WHERE categoryName NOT IN (:usedCategories)")
    suspend fun deleteUnusedCategories(usedCategories: List<String>)

    @Query("SELECT * FROM category_task_table WHERE categoryName = :name")
    suspend fun getCategoryByName(name: String) : TaskCategoryTable?

    @Insert(onConflict = OnConflictStrategy.IGNORE) //TODO { be aware of the conflict strategy }
    suspend fun insertTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTaskCategory(taskCategoryTable: TaskCategoryTable)

    @Delete
    suspend fun deleteTaskCategory(taskCategoryTable: TaskCategoryTable)

}