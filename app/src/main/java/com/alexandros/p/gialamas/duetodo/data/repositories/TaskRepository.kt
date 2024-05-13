package com.alexandros.p.gialamas.duetodo.data.repositories

import com.alexandros.p.gialamas.duetodo.data.TaskDao
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TaskRepository @Inject constructor(private val taskDao : TaskDao){

    val getAllTasks : Flow<List<TaskTable>> = taskDao.getAllTasks()

    fun getSelectedTask(taskId : Int) : Flow<TaskTable> {
        return taskDao.getSelectedTask(taskId = taskId)
    }


    suspend fun insertTask(taskTable: TaskTable) {
        taskDao.insertTask(taskTable = taskTable)
    }

    suspend fun updateTask(taskTable: TaskTable) {
        taskDao.updateTask(taskTable = taskTable)
    }

    suspend fun deleteTask(taskTable: TaskTable) {
        taskDao.deleteTask(taskTable = taskTable)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }


    fun searchDatabase(searchQuery : String) : Flow<List<TaskTable>>{
        return taskDao.searchDatabase(searchQuery = searchQuery)
    }


    fun sortByLowPriority() : Flow<List<TaskTable>> {
        return taskDao.sortByLowPriority()
    }

    fun sortByHighPriority() : Flow<List<TaskTable>> {
        return taskDao.sortByHighPriority()
    }

    val getLowPriorityTasks : Flow<List<TaskTable>> = taskDao.getLowPriorityTasks()

    val getMediumPriorityTasks : Flow<List<TaskTable>> = taskDao.getMediumPriorityTasks()

    val getHighPriorityTasks : Flow<List<TaskTable>> = taskDao.getHighPriorityTasks()

    fun getTasksDueAfter(currentTime: Long): Flow<List<TaskTable>>{
        return taskDao.getTasksDueAfter(currentTime = currentTime)
    }


    fun getTasksDueBetween(startDate: Long, endDate: Long): Flow<List<TaskTable>> {
        return taskDao.getTasksDueBetween(startDate = startDate, endDate = endDate)
    }

}