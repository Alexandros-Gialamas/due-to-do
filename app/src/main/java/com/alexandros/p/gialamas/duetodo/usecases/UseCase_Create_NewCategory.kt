package com.alexandros.p.gialamas.duetodo.usecases

import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import javax.inject.Inject

class CreateNewCategoryUseCase @Inject constructor(
    private val taskCategoryRepository: TaskCategoryRepository,
) {
    suspend operator fun invoke(
        category: String,
    ){
        // Create a new Category if is not existing already
        val trimmedCategory = category.trim()
        val isCategoryExist =
            trimmedCategory.let { taskCategoryRepository.getCategoryByName(it).toString() }
        if (!isCategoryExist.equals(trimmedCategory, ignoreCase = false)) {
            val newCategory = TaskCategoryTable(categoryName = trimmedCategory)
            taskCategoryRepository.insertCategory(newCategory)
        }
    }
}