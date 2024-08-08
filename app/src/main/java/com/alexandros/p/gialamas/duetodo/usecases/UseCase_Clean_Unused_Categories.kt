package com.alexandros.p.gialamas.duetodo.usecases

import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CleanUnusedCategoriesUseCase @Inject constructor(
    private val taskCategoryRepository: TaskCategoryRepository
) {
    suspend operator fun invoke() {
        taskCategoryRepository.cleanUnusedCategories()
    }
}