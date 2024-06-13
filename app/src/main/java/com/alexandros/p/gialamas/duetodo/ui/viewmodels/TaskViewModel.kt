package com.alexandros.p.gialamas.duetodo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.DataStoreRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.ReminderRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.usecases.GetTaskUseCases
import com.alexandros.p.gialamas.duetodo.util.Constants.MAX_TASK_TITLE_LENGTH
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import com.alexandros.p.gialamas.duetodo.util.SelectedCategoryState
import com.vanpra.composematerialdialogs.MaterialDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val taskRepository: TaskRepository,
    private val taskCategoryRepository: TaskCategoryRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val getTaskUseCases: GetTaskUseCases
) : ViewModel() {


    // Displayed Task fields
    var taskId by mutableIntStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var taskPriority by mutableStateOf(TaskPriority.LOW)
        private set
    var category by mutableStateOf("")
        private set
    var createdDate by mutableLongStateOf(System.currentTimeMillis())
        private set
    var dueDate by mutableStateOf<Long?>(null)
        private set
    var reScheduleDate by mutableStateOf<Long?>(null)
        private set
    var repeatFrequency by mutableStateOf(RepeatFrequency.NONE)
        private set
    var dialogNotification by mutableStateOf(false)
        private set
    var isChecked by mutableStateOf(false)
        private set
    var isPinned by mutableStateOf(false)
        private set
    var categoryReminders by mutableStateOf(false)
        private set

    // Update Task values
    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_TASK_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updateTaskPriority(newTaskPriority: TaskPriority) {
        taskPriority = newTaskPriority
    }

    fun updateCategory(newCategory: String) {
        category = newCategory
    }

    fun updateCreatedDate(newDate: Long) {
        createdDate = newDate
    }

    fun updateDueDate(newDueDate: Long?) {
        dueDate = newDueDate
    }

    fun updateReScheduleDate(newReScheduleDate: Long?) {
        reScheduleDate = newReScheduleDate
    }

    fun updateRepeatFrequency(newRepeatFrequency: RepeatFrequency) {
        repeatFrequency = newRepeatFrequency
    }

    fun updateDialogNotification(newDialogNotification: Boolean) {
        dialogNotification = newDialogNotification
    }

    fun updateIsChecked(isCompleted: Boolean) {
        isChecked = isCompleted
    }

    fun updateIsPinned(pinned: Boolean) {
        isPinned = pinned
    }

    fun updateCategoryReminders(newCategoryReminder: Boolean) {
        categoryReminders = newCategoryReminder
    }


    // Update Displayed Task fields
    fun updateDisplayTaskFields(selectedTask: TaskTable?) {
        if (selectedTask != null) {
            taskId = selectedTask.taskId
            title = selectedTask.title
            description = selectedTask.description
            taskPriority = selectedTask.taskPriority
            category = selectedTask.category
            createdDate = selectedTask.createdDate
            dueDate = selectedTask.dueDate
            reScheduleDate = selectedTask.reScheduleDate
            repeatFrequency = selectedTask.repeatFrequency
            dialogNotification = selectedTask.dialogNotification
            isPinned = selectedTask.isPinned
            isChecked = selectedTask.isChecked
            categoryReminders = selectedTask.categoryReminders

        } else {
            taskId = 0
            title = ""
            description = ""
            taskPriority = TaskPriority.LOW
            category = ""
            dueDate = null
            reScheduleDate = null
            repeatFrequency = RepeatFrequency.NONE
            dialogNotification = false
            isChecked = false
            isPinned = false
            categoryReminders = false
        }
    }


    //  UI Visual Operations
    private val _isGridLayout: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isGridLayout: StateFlow<Boolean> = _isGridLayout

    fun enableGridLayout() {
        _isGridLayout.value = !_isGridLayout.value
    }


    // Task CRUD Operations
    var databaseAction by mutableStateOf(DatabaseAction.NO_ACTION)
        private set

    fun updateAction(newDatabaseAction: DatabaseAction) {
        databaseAction = newDatabaseAction
    }

    fun validateFields(): Boolean {
        return title.isNotBlank() || description.isNotBlank()
    } //TODO { Only Title is enough }

    private fun insertTask() {
        viewModelScope.launch(Dispatchers.IO) {

            getTaskUseCases.insertTaskUseCase(
                viewModel = this@TaskViewModel,
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
                isChecked = isChecked
            )
        }
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {

            val updatedTask =
                getTaskUseCases.updateTaskUseCase(
                    viewModel = this@TaskViewModel,
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
                    isChecked = isChecked
                )
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId,
                title = title,
                description = description,
                taskPriority = taskPriority,
                createdDate = createdDate,
                dueDate = dueDate,
                dialogNotification = dialogNotification,
                isChecked = isChecked,
                isPinned = isPinned,
                category = category,
                categoryReminders = categoryReminders,
            )
            task.let { taskRepository.deleteTask(it) }
            cleanUnusedCategories()
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteAllTasks()
            cleanUnusedCategories()
        }
    }

    // Clean Unused Categories
    fun cleanUnusedCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            taskCategoryRepository.cleanUnusedCategories()
        }
    }

    fun handleDatabaseActions(databaseAction: DatabaseAction) {
        when (databaseAction) {
            DatabaseAction.INSERT -> {
                insertTask()
            }

            DatabaseAction.UPDATE -> {
                updateTask()
//                selectedTask.value?.let { reminderRepository.scheduleReminder(it, viewModelScope,this) }
            }

            DatabaseAction.DELETE -> {
                deleteTask()
            }

            DatabaseAction.DELETE_ALL -> {
                deleteAllTasks()
            }

            DatabaseAction.UNDO -> {
                insertTask()
            }

            else -> {
                DatabaseAction.NO_ACTION  // TODO { check if it works } }
            }
        }
    }


    // Notification Permission
    var notificationPermission = MutableStateFlow(false)
        private set

    fun isNotificationPermissionGranted(isGranted: Boolean) {
        notificationPermission.value = isGranted
    }

    var notificationPermissionDialogState by mutableStateOf<MaterialDialogState>(
        MaterialDialogState(
            false
        )
    )
        private set

    fun updateNotificationPermissionDialogState(isShowed: Boolean) {
        notificationPermissionDialogState = MaterialDialogState(isShowed)
    }

    var isNotificationPermissionDialogDisplayed by mutableStateOf(false)
        private set

    fun showNotificationPermissionDialog(isShowed: Boolean) {
        isNotificationPermissionDialogDisplayed = isShowed
    }


    // Search Bar
    var searchBarState by mutableStateOf(SearchBarState.UNFOCUSED)
        private set

    fun updateSearchBarState(newSearchBarState: SearchBarState) {
        searchBarState = newSearchBarState
    }


    var searchTextState by mutableStateOf("")
        private set

    fun updateSearchTextState(newSearchTextState: String) {
        searchTextState = newSearchTextState
    }


//    private val _searchedTasks =
//        MutableStateFlow<RequestState<List<TaskTable>>>(RequestState.Idle)
//    val searchedTasks: StateFlow<RequestState<List<TaskTable>>> = _searchedTasks
//    fun searchDatabase(searchQuery: String) {
//        _searchedTasks.value = RequestState.Loading
//        try {
//            viewModelScope.launch {
//                taskRepository.searchDatabase(searchQuery).collect { searchedTasks ->
//                    _searchedTasks.value = RequestState.Success(searchedTasks)
//                }
//            }
//        } catch (e: Exception) {
//            _searchedTasks.value = RequestState.Error(e)
//        }
//        searchBarState = SearchBarState.TRIGGERED
//    }


    // Sorting Tasks and Persist state
    fun sortByCategoryLowPriorityDateASC(categoryState: RequestState<String>): Flow<List<TaskTable>> =
        when (categoryState) {
            is RequestState.Success -> taskRepository.sortByCategoryLowPriorityDateASC(categoryState.data)
            else -> flowOf(emptyList())
        }

    fun sortByCategoryLowPriorityDateDESC(categoryState: RequestState<String>): Flow<List<TaskTable>> =
        when (categoryState) {
            is RequestState.Success -> taskRepository.sortByCategoryLowPriorityDateDESC(categoryState.data)
            else -> flowOf(emptyList())
        }

    fun sortByCategoryHighPriorityDateASC(categoryState: RequestState<String>): Flow<List<TaskTable>> =
        when (categoryState) {
            is RequestState.Success -> taskRepository.sortByCategoryHighPriorityDateASC(categoryState.data)
            else -> flowOf(emptyList())
        }

    fun sortByCategoryHighPriorityDateDESC(categoryState: RequestState<String>): Flow<List<TaskTable>> =
        when (categoryState) {
            is RequestState.Success -> taskRepository.sortByCategoryHighPriorityDateDESC(categoryState.data)
            else -> flowOf(emptyList())
        }

    fun sortByCategoryDateASC(categoryState: RequestState<String>): Flow<List<TaskTable>> =
        when (categoryState) {
            is RequestState.Success -> taskRepository.sortByCategoryDateASC(categoryState.data)
            else -> flowOf(emptyList())
        }

    fun sortByCategoryDateDESC(categoryState: RequestState<String>): Flow<List<TaskTable>> =
        when (categoryState) {
            is RequestState.Success -> taskRepository.sortByCategoryDateDESC(categoryState.data)
            else -> flowOf(emptyList())
        }

    private val _prioritySortState =
        MutableStateFlow<RequestState<TaskPriority>>(RequestState.Idle)
    val prioritySortState: StateFlow<RequestState<TaskPriority>> = _prioritySortState

    private val _dateSortState =
        MutableStateFlow<RequestState<DateSortOrder>>(RequestState.Idle)
    val dateSortState: StateFlow<RequestState<DateSortOrder>> = _dateSortState

    private val _categoryState =
        MutableStateFlow<RequestState<String>>(RequestState.Idle)
    val categoryState: StateFlow<RequestState<String>> = _categoryState

    var customCategoryName by mutableStateOf("")
        private set

    fun updateCustomCategoryName(customName: String) {
        customCategoryName = customName
    }

    private fun readPrioritySortState() {
        _prioritySortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readPrioritySortState.map { TaskPriority.valueOf(it) }
                    .collect { priorityOrder ->
                        _prioritySortState.value = RequestState.Success(priorityOrder)
                    }
            }
        } catch (e: Exception) {
            _prioritySortState.value = RequestState.Error(e)
        }
    }

    private fun readDateSortState() {
        _dateSortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readDateSortState.map { DateSortOrder.valueOf(it) }
                    .collect { dateSortOrder ->
                        _dateSortState.value = RequestState.Success(dateSortOrder)
                    }
            }
        } catch (e: Exception) {
            _dateSortState.value = RequestState.Error(e)
        }
    }

    private fun readCategorySortState() {
        _categoryState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readCategoryState.map { categoryName ->
                    when (categoryName) {
                        SelectedCategoryState.NONE.categoryName -> SelectedCategoryState.NONE.categoryName
                        SelectedCategoryState.REMINDERS.categoryName -> SelectedCategoryState.REMINDERS.categoryName
                        else -> categoryName
                    }
                }
                    .collect { categoryName ->
                        _categoryState.value = RequestState.Success(categoryName)
                    }
            }
        } catch (e: Exception) {
            _categoryState.value = RequestState.Error(e)
        }
    }


    fun persistPrioritySortState(taskPriority: TaskPriority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistPrioritySortState(taskPriority = taskPriority)
        }
    }

    fun persistDateSortState(dateSortOrder: DateSortOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistDateSortState(dateSortOrder = dateSortOrder)
        }
    }

    fun persistCategoryState(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistCategoryState(category = category)
        }
    }


    // Get All Categories
    private val _allCategories =
        MutableStateFlow<RequestState<List<TaskCategoryTable>>>(RequestState.Idle)
    val allCategories: StateFlow<RequestState<List<TaskCategoryTable>>> = _allCategories
    private fun getAllCategories() {
        _allCategories.value = RequestState.Loading
        try {
            viewModelScope.launch {
                taskCategoryRepository.getAllCategories.collect { categories ->
                    _allCategories.value = RequestState.Success(categories)
                }
            }
        } catch (e: Exception) {
            _allCategories.value = RequestState.Error(e)
        }
    }


    // Get Selected Category
    private val _selectedCategory: MutableStateFlow<TaskCategoryTable?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<TaskCategoryTable?> = _selectedCategory
    fun getSelectedCategory(categoryId: Int) {
        viewModelScope.launch {
            taskCategoryRepository.getSelectedCategory(categoryId).collect { category ->
                _selectedCategory.value = category
            }
        }
    }


    // Get All Tasks
    private val _allTasks =
        MutableStateFlow<RequestState<List<TaskTable>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<TaskTable>>> = _allTasks
    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                taskRepository.getAllTasks.collect { tasks ->
                    _allTasks.value = RequestState.Success(tasks)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }


    // Get Selected Task
    private val _selectedTask: MutableStateFlow<TaskTable?> = MutableStateFlow(null)
    val selectedTask: StateFlow<TaskTable?> = _selectedTask
    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.getSelectedTask(taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }


    init {
        getAllTasks()
        getAllCategories()
        cleanUnusedCategories()
        readPrioritySortState()
        readDateSortState()
        readCategorySortState()
    }

}