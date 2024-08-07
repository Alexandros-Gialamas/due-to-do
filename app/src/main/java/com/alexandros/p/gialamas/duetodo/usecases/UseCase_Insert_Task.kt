package com.alexandros.p.gialamas.duetodo.usecases

import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
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
        checkListItem: List<CheckListItem>
//        createdDate: Long,
//        taskId: Int,
//        categoryNone: Boolean,
//        categoryId: Int?
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
            isChecklist = isChecked,
            checkListItem = checkListItem
//            createdDate = createdDate,
//            taskId = taskId,
//            categoryNone = categoryNone,
//            categoryId = categoryId

        )

        newTask.let { taskRepository.insertTask(it) }
        // Clean Unused Categories
        taskCategoryRepository.cleanUnusedCategories()
        // Clear Past Due Dates
//        clearPastDueDatesUseCase(viewModel,dueDate)


        newTask.dueDate?.let {
            val taskForSchedule = newTask.taskId.let { taskRepository.getTaskForSchedule(it) }
            reminderRepository.scheduleReminder(taskForSchedule,viewModel.viewModelScope,viewModel)
        }

    }
}