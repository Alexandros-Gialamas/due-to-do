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

    @Query("SELECT * FROM task_table WHERE taskId=:taskId")
    suspend fun getTaskForSchedule(taskId : Int) : TaskTable


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


    @Query("SELECT DISTINCT category FROM task_table WHERE category IS NOT NULL AND category != ''")
    suspend fun getAllUsedCategories(): List<String>


    @Query(
        """
       SELECT * FROM task_table 
        WHERE (:category = 'allOrNoCategories' AND categoryNone = 1) 
        OR (:category = 'tasksWithDueDates' AND categoryReminders = 1) 
        OR (category = :category AND :category != 'allOrNoCategories' AND :category != 'tasksWithDueDates')
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'N%' THEN 1
        END,
        createdDate ASC
        """
    )
    fun sortByCategoryDateASC(category : String) : Flow<List<TaskTable>>

    @Query(
        """
        SELECT * FROM task_table 
        WHERE (:category = 'allOrNoCategories' AND categoryNone = 1) 
        OR (:category = 'tasksWithDueDates' AND categoryReminders = 1) 
        OR (category = :category AND :category != 'allOrNoCategories' AND :category != 'tasksWithDueDates')
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'N%' THEN 1
        END,
        createdDate DESC
        """
    )
    fun sortByCategoryDateDESC(category : String) : Flow<List<TaskTable>>

    @Query(
        """
        SELECT * FROM task_table 
        WHERE (:category = 'allOrNoCategories' AND categoryNone = 1) 
        OR (:category = 'tasksWithDueDates' AND categoryReminders = 1) 
        OR (category = :category AND :category != 'allOrNoCategories' AND :category != 'tasksWithDueDates')
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'L%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'H%' THEN 3
        END,
        createdDate ASC
        """
    )
    fun sortByCategoryLowPriorityDateASC(category : String) : Flow<List<TaskTable>>

    @Query(
        """
       SELECT * FROM task_table 
        WHERE (:category = 'allOrNoCategories' AND categoryNone = 1) 
        OR (:category = 'tasksWithDueDates' AND categoryReminders = 1) 
        OR (category = :category AND :category != 'allOrNoCategories' AND :category != 'tasksWithDueDates')
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'L%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'H%' THEN 3
        END,
        createdDate DESC
        """
    )
    fun sortByCategoryLowPriorityDateDESC(category : String) : Flow<List<TaskTable>>

    @Query(
        """
       SELECT * FROM task_table 
        WHERE (:category = 'allOrNoCategories' AND categoryNone = 1) 
        OR (:category = 'tasksWithDueDates' AND categoryReminders = 1) 
        OR (category = :category AND :category != 'allOrNoCategories' AND :category != 'tasksWithDueDates')
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'H%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'L%' THEN 3
        END,
        createdDate ASC
        """
    )
    fun sortByCategoryHighPriorityDateASC(category : String) : Flow<List<TaskTable>>

    @Query(
        """
        SELECT * FROM task_table 
        WHERE (:category = 'allOrNoCategories' AND categoryNone = 1) 
        OR (:category = 'tasksWithDueDates' AND categoryReminders = 1) 
        OR (category = :category AND :category != 'allOrNoCategories' AND :category != 'tasksWithDueDates') 
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'H%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'L%' THEN 3
        END,
        createdDate DESC
        """
    )
    fun sortByCategoryHighPriorityDateDESC(category : String): Flow<List<TaskTable>>


    @Query(
        """
        SELECT * FROM task_table 
        WHERE dueDate <= :currentDate
        ORDER BY
        CASE
            WHEN taskPriority LIKE 'H%' THEN 1
            WHEN taskPriority LIKE 'M%' THEN 2
            WHEN taskPriority LIKE 'L%' THEN 3
        END,
        dueDate ASC
        """
    )
    fun getOverDueTasks(currentDate: Long): Flow<List<TaskTable>>


}