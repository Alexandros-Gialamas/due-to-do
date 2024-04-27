package com.alexandros.p.gialamas.duetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alexandros.p.gialamas.duetodo.data.models.ToDoTaskTable
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoTaskDao {

    @Query("SELECT * FROM todo_task_table ORDER BY id ASC")
    fun getAllTasks() : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table WHERE id=:taskId")
    fun getSelectedTask(taskId : Int) : Flow<ToDoTaskTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE) //TODO { be aware of the conflict strategy }
    suspend fun addTask(toDoTaskTable: ToDoTaskTable)

    @Update
    suspend fun updateTask(toDoTaskTable: ToDoTaskTable)

    @Delete
    suspend fun deleteTask(toDoTaskTable: ToDoTaskTable)

    @Query("DELETE FROM todo_task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_task_table WHERE " +
            "LOWER(title) LIKE '%' || LOWER(:searchQuery) || '%' OR " +
            "LOWER(description) LIKE '%' || LOWER(:searchQuery) || '%'")
    fun searchDatabase(searchQuery : String) : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table ORDER BY CASE " +
            "WHEN taskPriority LIKE 'L%' THEN 1" +
            "WHEN taskPriority LIKE 'M%' THEN 2" +
            "WHEN taskPriority LIKE 'H%'THEN 3 END")
    fun sortByLowPriority() : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table ORDER BY CASE " +
            "WHEN taskPriority LIKE 'H%' THEN 1" +
            "WHEN taskPriority LIKE 'M%' THEN 2" +
            "WHEN taskPriority LIKE 'L%'THEN 3 END")
    fun sortByHighPriority() : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table WHERE taskPriority LIKE 'L%'")
    fun getLowPriorityTasks() : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table WHERE taskPriority LIKE 'M%'")
    fun getMediumPriorityTasks() : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table WHERE taskPriority LIKE 'H%'")
    fun getHighPriorityTasks() : Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table WHERE dueDate > :currentTime ORDER BY dueDate ASC")
    fun getTasksDueAfter(currentTime: Long): Flow<List<ToDoTaskTable>>

    @Query("SELECT * FROM todo_task_table WHERE dueDate BETWEEN :startDate AND :endDate")
    fun getTasksDueBetween(startDate: Long, endDate: Long): Flow<List<ToDoTaskTable>>
}