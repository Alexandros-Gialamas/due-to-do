package com.alexandros.p.gialamas.duetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY taskId ASC")
    fun getAllTasks() : Flow<List<TaskTable>>

    @Query("SELECT * FROM task_table WHERE taskId=:taskId")
    fun getSelectedTask(taskId : Int) : Flow<TaskTable>


    @Query("SELECT * FROM task_table WHERE category = :category COLLATE NOCASE")
    fun getTasksByCategory(category: String): Flow<List<TaskTable>>   // TODO { delete that }

    @Insert(onConflict = OnConflictStrategy.IGNORE) //TODO { be aware of the conflict strategy }
    suspend fun insertTask(taskTable: TaskTable)

    @Update
    suspend fun updateTask(taskTable: TaskTable)

    @Delete
    suspend fun deleteTask(taskTable: TaskTable)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task_table WHERE " +
            "LOWER(title) LIKE '%' || LOWER(:searchQuery) || '%' OR " +
            "LOWER(description) LIKE '%' || LOWER(:searchQuery) || '%'")
    fun searchDatabase(searchQuery : String) : Flow<List<TaskTable>>


    @Query("SELECT DISTINCT category FROM task_table WHERE category IS NOT NULL AND category != ''")
    suspend fun getAllUsedCategories(): List<String>

    @Query(
        """
            SELECT * FROM task_table ORDER BY
        CASE
            WHEN taskPriority LIKE 'L%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'H%' THEN 3
        END
        """
    )
    fun sortByLowPriority() : Flow<List<TaskTable>>

    @Query(
        """
            SELECT * FROM task_table ORDER BY
        CASE
            WHEN taskPriority LIKE 'H%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'L%' THEN 3
        END
        """
    )
    fun sortByHighPriority(): Flow<List<TaskTable>>


    @Query("SELECT * FROM task_table WHERE taskPriority LIKE 'L%'")
    fun getLowPriorityTasks() : Flow<List<TaskTable>>

    @Query("SELECT * FROM task_table WHERE taskPriority LIKE 'M%'")
    fun getMediumPriorityTasks() : Flow<List<TaskTable>>

    @Query("SELECT * FROM task_table WHERE taskPriority LIKE 'H%'")
    fun getHighPriorityTasks() : Flow<List<TaskTable>>

    @Query("SELECT * FROM task_table WHERE dueDate > :currentTime ORDER BY dueDate ASC")
    fun getTasksDueAfter(currentTime: Long): Flow<List<TaskTable>>

    @Query("SELECT * FROM task_table WHERE dueDate BETWEEN :startDate AND :endDate")
    fun getTasksDueBetween(startDate: Long, endDate: Long): Flow<List<TaskTable>>
}