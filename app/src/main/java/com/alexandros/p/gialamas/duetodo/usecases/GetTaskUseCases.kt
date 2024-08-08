package com.alexandros.p.gialamas.duetodo.usecases

import javax.inject.Inject

class GetTaskUseCases @Inject constructor(
    val insertTaskUseCase: InsertTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val deleteAllTasksUseCase: DeleteAllTasksUseCase,
    val cleanUnusedCategoriesUseCase: CleanUnusedCategoriesUseCase
)
