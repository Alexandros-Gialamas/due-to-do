package com.alexandros.p.gialamas.duetodo.usecases

import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class ClearPastDueDatesUseCase @Inject constructor(){

     operator fun invoke(viewModel: TaskViewModel,dueDate : Long?){
        val systemDate = System.currentTimeMillis()
        dueDate?.let {
            if (dueDate!!.milliseconds <= systemDate.milliseconds) {
                viewModel.updateDueDate(null)
            }
        }
    }

}