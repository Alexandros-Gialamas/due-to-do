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


    fun sortByCategoryDateASC(category : String) : Flow<List<TaskTable>> {
        return taskDao.sortByCategoryDateASC(category)
    }

    fun sortByCategoryDateDESC(category : String) : Flow<List<TaskTable>> {
        return taskDao.sortByCategoryDateDESC(category)
    }

    fun sortByCategoryLowPriorityDateASC(category : String) : Flow<List<TaskTable>> {
        return taskDao.sortByCategoryLowPriorityDateASC(category)
    }

    fun sortByCategoryLowPriorityDateDESC(category : String) : Flow<List<TaskTable>> {
        return taskDao.sortByCategoryLowPriorityDateDESC(category)
    }

    fun sortByCategoryHighPriorityDateASC(category : String) : Flow<List<TaskTable>> {
        return taskDao.sortByCategoryHighPriorityDateASC(category)
    }

    fun sortByCategoryHighPriorityDateDESC(category : String) : Flow<List<TaskTable>> {
        return taskDao.sortByCategoryHighPriorityDateDESC(category)
    }

    val getLowPriorityTasks : Flow<List<TaskTable>> = taskDao.getLowPriorityTasks()

    val getMediumPriorityTasks : Flow<List<TaskTable>> = taskDao.getMediumPriorityTasks()

    val getHighPriorityTasks : Flow<List<TaskTable>> = taskDao.getHighPriorityTasks()


    fun getOverDueTasks(currentDate: Long): Flow<List<TaskTable>> {
        return taskDao.getOverDueTasks(currentDate = currentDate)
    }

}