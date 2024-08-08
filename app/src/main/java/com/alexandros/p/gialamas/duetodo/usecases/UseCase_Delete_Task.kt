package com.alexandros.p.gialamas.duetodo.usecases

import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    private val cancelScheduledTaskOperationUseCase: CancelScheduledTaskOperationUseCase
) {
    lateinit var undoTask: TaskTable

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
        categoryNone: Boolean,
        categoryId: Int?,
        checkListItem: List<CheckListItem>
    ) {
        val task = TaskTable(
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
            isChecklist = isChecked,
            isPinned = isPinned,
            categoryNone = categoryNone,
            categoryId = categoryId,
            checkListItem = checkListItem
        )

        undoTask = task.copy()

        cancelScheduledTaskOperationUseCase(taskId)

        task.let { taskRepository.deleteTask(it) }

    }

    suspend fun undoDeletion(viewModel: TaskViewModel){
        undoTask?.let { taskRepository.insertTask(it) }
        undoTask?.dueDate?.let {
            val taskForSchedule = undoTask.taskId.let { taskRepository.getTaskForSchedule(it) }
            reminderRepository.scheduleReminder(taskForSchedule,viewModel.viewModelScope,viewModel)
        }
    }

}