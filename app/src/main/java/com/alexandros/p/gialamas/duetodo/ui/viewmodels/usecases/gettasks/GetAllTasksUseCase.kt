package com.alexandros.p.gialamas.duetodo.ui.viewmodels.usecases.gettasks

import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() : Flow<List<TaskTable>> = taskRepository.getAllTasks
}