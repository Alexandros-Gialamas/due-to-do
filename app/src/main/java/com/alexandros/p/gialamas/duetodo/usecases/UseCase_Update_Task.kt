package com.alexandros.p.gialamas.duetodo.usecases

import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import kotlinx.coroutines.delay
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCategoryRepository: TaskCategoryRepository,
    private val reminderRepository: ReminderRepository,
    private val createNewCategoryUseCase: CreateNewCategoryUseCase,
    private val clearPastDueDatesUseCase: ClearPastDueDatesUseCase
) {
    suspend operator fun invoke(
        viewModel: TaskViewModel,
        taskId: Int,
        title: String,
        description: String,
        taskPriority: TaskPriority,
        category: String,
        createdDate : Long,
        dueDate: Long?,
        reScheduleDate: Long?,
        repeatFrequency: RepeatFrequency,
        dialogNotification: Boolean,
        categoryReminders: Boolean,
        isPinned: Boolean,
        isChecked: Boolean,
    ) {

        // Create a new Category if is not existing already
        createNewCategoryUseCase(category)

        val updateTask = TaskTable(
            taskId = taskId,
            title = title,
            description = description,
            taskPriority = taskPriority,
            category = category,
            createdDate = createdDate,
            dueDate = dueDate,
            reScheduleDate = reScheduleDate,
            repeatFrequency = repeatFrequency,
            dialogNotification = dialogNotification,
            categoryReminders = categoryReminders,
            isPinned = isPinned,
            isChecked = isChecked,
        )

        updateTask.let { taskRepository.updateTask(it) }
        // Clean Unused Categories
        taskCategoryRepository.cleanUnusedCategories()
        // Clear Past Due Dates
//        clearPastDueDatesUseCase(viewModel,dueDate)

        val taskForSchedule = taskRepository.getTaskForSchedule(updateTask.taskId)
        reminderRepository.scheduleReminder(taskForSchedule,viewModel.viewModelScope,viewModel)

    }
}
