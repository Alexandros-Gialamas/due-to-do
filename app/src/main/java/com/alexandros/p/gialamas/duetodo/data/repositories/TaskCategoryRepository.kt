package com.alexandros.p.gialamas.duetodo.data.repositories

import com.alexandros.p.gialamas.duetodo.data.TaskCategoryDao
import com.alexandros.p.gialamas.duetodo.data.TaskDao
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TaskCategoryRepository@Inject constructor(
    private val taskCategoryDao : TaskCategoryDao,
    private val taskDao : TaskDao
){

    val getAllCategories : Flow<List<TaskCategoryTable>> = taskCategoryDao.getAllCategories()

    fun getSelectedCategory(categoryId : Int) : Flow<TaskCategoryTable> {
        return taskCategoryDao.getSelectedCategory(categoryId = categoryId)
    }

    suspend fun getCategoryByName(name: String): TaskCategoryTable? {
        return taskCategoryDao.getCategoryByName(name)
    }

    suspend fun cleanUnusedCategories(){
        val usedCategories = taskDao.getAllUsedCategories()
        taskCategoryDao.deleteUnusedCategories(usedCategories)
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