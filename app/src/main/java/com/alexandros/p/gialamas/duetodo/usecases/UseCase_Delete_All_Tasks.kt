package com.alexandros.p.gialamas.duetodo.usecases

import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke() {
        taskRepository.deleteAllTasks()
        reminderRepository.cancelAllScheduledTask()
    }
}