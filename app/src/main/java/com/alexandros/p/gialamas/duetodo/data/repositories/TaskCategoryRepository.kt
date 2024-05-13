package com.alexandros.p.gialamas.duetodo.data.repositories

import com.alexandros.p.gialamas.duetodo.data.TaskCategoryDao
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TaskCategoryRepository@Inject constructor(private val taskCategoryDao : TaskCategoryDao){

    val getAllCategories : Flow<List<TaskCategoryTable>> = taskCategoryDao.getAllCategories()

    fun getSelectedCategory(categoryId : Int) : Flow<TaskCategoryTable> {
        return taskCategoryDao.getSelectedCategory(categoryId = categoryId)
    }

    suspend fun insertCategory(category: TaskCategoryTable) {
        taskCategoryDao.insertTaskCategory(category)
    }

    suspend fun updateCategory(category: TaskCategoryTable) {
        taskCategoryDao.updateTaskCategory(category)
    }

    suspend fun deleteCategory(category: TaskCategoryTable) {
        taskCategoryDao.deleteTaskCategory(category)
    }

}