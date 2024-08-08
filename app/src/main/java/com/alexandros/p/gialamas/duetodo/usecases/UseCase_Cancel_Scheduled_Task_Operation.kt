package com.alexandros.p.gialamas.duetodo.usecases

import android.content.Context
import androidx.work.WorkManager
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CancelScheduledTaskOperationUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
     operator fun invoke(
        taskId : Int
    ) {
         reminderRepository.cancelScheduledTask(taskId)
    }
}