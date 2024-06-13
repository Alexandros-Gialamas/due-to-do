package com.alexandros.p.gialamas.duetodo.usecases

import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages
import kotlinx.coroutines.delay
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCategoryRepository: TaskCategoryRepository,
    private val reminderRepository: ReminderRepository,
    private val createNewCategoryUseCase: CreateNewCategoryUseCase,
    private val clearPastDueDatesUseCase: ClearPastDueDatesUseCase
) {
    suspend operator fun invoke(
        viewModel: TaskViewModel,
        title: String,
        description: String,
        taskPriority: TaskPriority,
        category: String,
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

        val newTask = TaskTable(
            title = title,
            description = description,
            taskPriority = taskPriority,
            category = category,
            dueDate = dueDate,
            reScheduleDate = reScheduleDate,
            repeatFrequency = repeatFrequency,
            dialogNotification = dialogNotification,
            categoryReminders = categoryReminders,
            isPinned = isPinned,
            isChecked = isChecked,
        )

        newTask.let { taskRepository.insertTask(it) }
        // Clean Unused Categories
        taskCategoryRepository.cleanUnusedCategories()
        // Clear Past Due Dates
        clearPastDueDatesUseCase(viewModel,dueDate)



//        reminderRepository.scheduleReminder(newTask,viewModel.viewModelScope)

//        viewModel.getSelectedTask(newTask.taskId)
//        val taskForScheduling = viewModel.selectedTask.value
//        taskForScheduling?.let {
//            reminderRepository.scheduleReminder(
//                task = taskForScheduling, scope = viewModel.viewModelScope) }
    }
}