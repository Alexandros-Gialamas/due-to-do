package com.alexandros.p.gialamas.duetodo.ui.viewmodels

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.DataStoreRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.reminders.ReminderWorker
import com.alexandros.p.gialamas.duetodo.util.Constants.MAX_TASK_TITLE_LENGTH
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_HARD_NOTIFICATION
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_REMINDER
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_REPEAT_FREQUENCY
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_DESCRIPTION
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_ID
import com.alexandros.p.gialamas.duetodo.util.Constants.REMINDER_WORKER_TASK_TITLE
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCategoryRepository: TaskCategoryRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val workManager: WorkManager
) : ViewModel() {


    // Display Task
    var taskId by mutableIntStateOf(0)
        private set

    var title by mutableStateOf("")
        private set

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_TASK_TITLE_LENGTH) {
            title = newTitle
        }
    }

    var description by mutableStateOf("")
        private set

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    var taskPriority by mutableStateOf(TaskPriority.LOW)
        private set

    fun updateTaskPriority(newTaskPriority: TaskPriority) {
        taskPriority = newTaskPriority
    }

    var category by mutableStateOf("")
        private set

    fun updateCategory(newCategory: String) {
        category = newCategory
    }

    var categoryReminders by mutableStateOf(false)   // TODO { check this }
        private set

    fun updateCategoryReminders(newCategoryReminder: Boolean) {
        categoryReminders = newCategoryReminder
    }

    var date by mutableLongStateOf(System.currentTimeMillis())
        private set

    fun updatedate(newdate: Long) {
        date = newdate
    }

    var isCompleted by mutableStateOf(false)
        private set

    fun updateIsCompleted(newIsCompleted: Boolean) {
        isCompleted = newIsCompleted
    }

    var isPinned by mutableStateOf(false)
        private set

    fun updateIsPinned(newIsPinned: Boolean) {
        isPinned = newIsPinned
    }

    var isPopAlarmSelected by mutableStateOf(false)
        private set

    fun updateIsPopAlarmSelected(newIsPopAlarmSelected: Boolean) {
        isPopAlarmSelected = newIsPopAlarmSelected
    }

    var dueDate by mutableStateOf<Long?>(null)
        private set

    fun updateDueDate(newDueDate: Long?) {
        dueDate = newDueDate
    }


    fun updateDisplayTaskFields(selectedTask: TaskTable?) {
        if (selectedTask != null) {
            taskId = selectedTask.taskId
            title = selectedTask.title
            category = selectedTask.category
            description = selectedTask.description
            date = selectedTask.date
            dueDate = selectedTask.dueDate
            taskPriority = selectedTask.taskPriority
            isPinned = selectedTask.isPinned
            isCompleted = selectedTask.isCompleted
            isPopAlarmSelected = selectedTask.isPopAlarmSelected
            categoryReminders = selectedTask.categoryReminders
        } else {
            taskId = 0
            title = ""
            category = ""
            description = ""
            dueDate = null
            taskPriority = TaskPriority.LOW
            isPinned = false
            isCompleted = false
            isPopAlarmSelected = false
            categoryReminders = false
        }
    }

    private val _isGridLayout: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isGridLayout: StateFlow<Boolean> = _isGridLayout
    fun enableGridLayout() {
        _isGridLayout.value = !_isGridLayout.value
    }


    // Task CRUD Operations
    var crudAction by mutableStateOf(CrudAction.NO_ACTION)
        private set

    fun updateAction(newCrudAction: CrudAction) {
        crudAction = newCrudAction
    }

    fun validateFields(): Boolean {
        return title.isNotBlank() || description.isNotBlank()
    } //TODO { Only Title is enough }


    private fun insertTask() {
        viewModelScope.launch(Dispatchers.IO) {

            val categoryName = category.trim()
            val isCategoryExist = categoryName.let { taskCategoryRepository.getCategoryByName(it).toString() }
            if (!isCategoryExist.equals(categoryName, ignoreCase = false)) {
                val newCategory = TaskCategoryTable(categoryName = categoryName)
                taskCategoryRepository.insertCategory(newCategory)
            }
            val systemDate = System.currentTimeMillis()
            dueDate?.let {
                if (dueDate!!.milliseconds <= systemDate.milliseconds) {  // TODO { assert call }
                    updateDueDate(null)
                }
            }

            val task = TaskTable(
                title = title,
                category = category,
                description = description,
                taskPriority = taskPriority,
                categoryReminders = categoryReminders,
                isPinned = isPinned,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
            )

            task.let { taskRepository.insertTask(it) }
            cleanUnusedCategories()
            setReminders(task)
            Log.d("TaskViewModel", "Scheduling reminder for task $taskId with due date $dueDate")


//            if (dueDate != null) {
//                val calendar = Calendar.getInstance()
//                val reminderRequest = when (task.repeatFrequency) {
//                    RepeatFrequency.NONE -> createReminderRequest(dueDate!!, task)
//                    RepeatFrequency.DAILY -> createRecurringReminderRequest(dueDate!!, 1, TimeUnit.DAYS, task)
//                    RepeatFrequency.WEEKLY -> createRecurringReminderRequest(dueDate!!, 7, TimeUnit.DAYS, task)
//                    RepeatFrequency.MONTHLY -> {
//                        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//                        createRecurringReminderRequest(dueDate!!, daysInMonth.toLong(), TimeUnit.DAYS, task)
//                    }
//                    RepeatFrequency.YEARLY -> {
//                        val isLeapYear =
//                            if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) 366 else 365
//                        createRecurringReminderRequest(dueDate!!, isLeapYear.toLong(), TimeUnit.DAYS, task)
//                    }
//                    RepeatFrequency.CUSTOM -> createCustomReminderRequest(task.repeatDate!!, task) // Assuming you store custom details in repeatDate
//                }
//
//                workManager.enqueueUniqueWork(
//                    "${REMINDER_WORKER_REMINDER}_${task.taskId}",
//                    ExistingWorkPolicy.REPLACE,
//                    reminderRequest.build()
//                )
//            }
        }
    }


    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {

            val categoryName = category.trim()
            val isCategoryExist = taskCategoryRepository.getCategoryByName(categoryName).toString()
            if (!isCategoryExist.equals(categoryName, ignoreCase = false)) {
                val newCategory = TaskCategoryTable(categoryName = categoryName)
                taskCategoryRepository.insertCategory(newCategory)
            }
            val systemDate = System.currentTimeMillis()
            dueDate?.let {
                if (dueDate!!.milliseconds <= systemDate.milliseconds) {
                    updateDueDate(null)
                }
            }

            val task = TaskTable(
                taskId = taskId,
                title = title,
                category = category,
                description = description,
                taskPriority = taskPriority,
                isPinned = isPinned,
                date = date,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
                categoryReminders = categoryReminders,
                isCompleted = isCompleted,
            )

            taskRepository.updateTask(task)
            cleanUnusedCategories()
            setReminders(task)
            Log.d("TaskViewModel", "Scheduling reminder for task $taskId with due date $dueDate")
//            if (dueDate != null) {
//                val calendar = Calendar.getInstance()
//                val reminderRequest = when (task.repeatFrequency) {
//                    RepeatFrequency.NONE -> createReminderRequest(dueDate!!, task)
//                    RepeatFrequency.DAILY -> createRecurringReminderRequest(dueDate!!, 1, TimeUnit.DAYS, task)
//                    RepeatFrequency.WEEKLY -> createRecurringReminderRequest(dueDate!!, 7, TimeUnit.DAYS, task)
//                    RepeatFrequency.MONTHLY -> {
//                        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//                        createRecurringReminderRequest(dueDate!!, daysInMonth.toLong(), TimeUnit.DAYS, task)
//                    }
//                    RepeatFrequency.YEARLY -> {
//                        val isLeapYear =
//                            if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) 366 else 365
//                        createRecurringReminderRequest(dueDate!!, isLeapYear.toLong(), TimeUnit.DAYS, task)
//                    }
//                    RepeatFrequency.CUSTOM -> createCustomReminderRequest(task.repeatDate!!, task) // Assuming you store custom details in repeatDate
//                }
//
//                workManager.enqueueUniqueWork(
//                    "${REMINDER_WORKER_REMINDER}_${task.taskId}",
//                    ExistingWorkPolicy.REPLACE,
//                    reminderRequest.build()
//                )
//            }
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId,
                title = title,
                description = description,
                taskPriority = taskPriority,
                date = date,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
                isCompleted = isCompleted,
                isPinned = isPinned,
                category = category,
                categoryReminders = categoryReminders,
            )
            taskRepository.deleteTask(task)
            cleanUnusedCategories()
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteAllTasks()
            cleanUnusedCategories()
        }
    }

    fun handleDatabaseActions(crudAction: CrudAction) {
        when (crudAction) {
            CrudAction.INSERT -> {
                insertTask()
            }

            CrudAction.UPDATE -> {
                updateTask()
            }

            CrudAction.DELETE -> {
                deleteTask()
            }

            CrudAction.DELETE_ALL -> {
                deleteAllTasks()
            }

            CrudAction.UNDO -> {
                insertTask()
            }

            else -> {
                CrudAction.NO_ACTION  // TODO { check if it works }
            }
        }
    }


    // Search Bar
    var searchBarState by mutableStateOf(SearchBarState.CLOSED)
        private set

    fun updateSearchBarState(newSearchBarState: SearchBarState) {
        searchBarState = newSearchBarState
    }


    var searchTextState by mutableStateOf("")
        private set

    fun updateSearchTextState(newSearchTextState: String) {
        searchTextState = newSearchTextState
    }


    private val _searchedTasks =
        MutableStateFlow<RequestState<List<TaskTable>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<TaskTable>>> = _searchedTasks
    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                taskRepository.searchDatabase(searchQuery).collect { searchedTasks ->
                    _searchedTasks.value = RequestState.Success(searchedTasks)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchBarState = SearchBarState.TRIGGERED
    }


    // Sorting Persist
    fun lowTaskPrioritySort(): StateFlow<List<TaskTable>> =
        taskRepository.sortByLowPriority().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun highTaskPrioritySort(): StateFlow<List<TaskTable>> =
        taskRepository.sortByHighPriority().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _sortState =
        MutableStateFlow<RequestState<TaskPriority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<TaskPriority>> = _sortState
    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState.map { TaskPriority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(taskPriority: TaskPriority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(taskPriority = taskPriority)
        }
    }


    // Clean Category List
    fun cleanUnusedCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            taskCategoryRepository.cleanUnusedCategories()
        }
    }

    //Get All Categories
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

    // Set Reminders
    private fun setReminders(task : TaskTable){
        if (dueDate != null) {
            val calendar = Calendar.getInstance()
            val reminderRequest = when (task.repeatFrequency) {
                RepeatFrequency.SNOOZE_10_MINUTES -> createReminderRequest(dueDate!! + 10 * 60 * 1000, task) // 10 minutes
                RepeatFrequency.SNOOZE_1_HOUR -> createReminderRequest(dueDate!! + 60 * 60 * 1000, task) // 1 hour
                RepeatFrequency.SNOOZE_2_HOUR -> createReminderRequest(dueDate!! + 2 * 60 * 60 * 1000, task) // 2 hours
                RepeatFrequency.NONE -> createReminderRequest(dueDate!!, task)
                RepeatFrequency.DAILY -> createRecurringReminderRequest(dueDate!!, 1, TimeUnit.DAYS, task)
                RepeatFrequency.WEEKLY -> createRecurringReminderRequest(dueDate!!, 7, TimeUnit.DAYS, task)
                RepeatFrequency.MONTHLY -> {
                    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    createRecurringReminderRequest(dueDate!!, daysInMonth.toLong(), TimeUnit.DAYS, task)
                }
                RepeatFrequency.YEARLY -> {
                    val isLeapYear =
                        if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) 366 else 365
                    createRecurringReminderRequest(dueDate!!, isLeapYear.toLong(), TimeUnit.DAYS, task)
                }
                RepeatFrequency.CUSTOM -> createCustomReminderRequest(task.repeatDate!!, task) // Assuming you store custom details in repeatDate
            }

            workManager.enqueueUniqueWork(
                "${REMINDER_WORKER_REMINDER}_${task.taskId}",
                ExistingWorkPolicy.REPLACE,
                reminderRequest.build()
            )
        }
    }

    // Create Work Requests
    private fun createReminderRequest(dueDateMillis: Long, task: TaskTable): OneTimeWorkRequest.Builder =
        OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(dueDateMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    REMINDER_WORKER_TASK_ID to task.taskId,
                    REMINDER_WORKER_TASK_TITLE to task.title,
                    REMINDER_WORKER_TASK_DESCRIPTION to task.description,
                    REMINDER_WORKER_HARD_NOTIFICATION to task.isPopAlarmSelected
                )
            )

    private fun createRecurringReminderRequest(
        dueDateMillis: Long,
        repeatInterval: Long,
        repeatTimeUnit: TimeUnit,
        task: TaskTable
    ): OneTimeWorkRequest.Builder =
    OneTimeWorkRequestBuilder<ReminderWorker>()
    .setInitialDelay(repeatInterval, repeatTimeUnit)
    .setInputData(workDataOf(
    REMINDER_WORKER_TASK_ID to task.taskId,
    REMINDER_WORKER_TASK_TITLE to task.title,
    REMINDER_WORKER_TASK_DESCRIPTION to task.description,
    REMINDER_WORKER_HARD_NOTIFICATION to task.isPopAlarmSelected,
    REMINDER_WORKER_REPEAT_FREQUENCY to task.repeatFrequency.name // Add repeat frequency
    ))

    private fun createCustomReminderRequest(repeatDateMillis: Long, task: TaskTable): OneTimeWorkRequest.Builder {

        val initialDelay = repeatDateMillis - System.currentTimeMillis()
        // Ensure the initial delay is positive
        if (initialDelay <= 0) {
            // Handle the case where the repeat date is in the past (e.g., log a warning)
            Log.w(
                "TaskViewModel",
                "Custom reminder repeat date is in the past for task ${task.taskId}"
            )
            return createReminderRequest(
                System.currentTimeMillis(),
                task
            ) // Schedule for immediate execution
        }
        val workData = workDataOf(
            REMINDER_WORKER_TASK_ID to task.taskId,
            REMINDER_WORKER_TASK_TITLE to task.title,
            REMINDER_WORKER_TASK_DESCRIPTION to task.description,
            REMINDER_WORKER_HARD_NOTIFICATION to task.isPopAlarmSelected,
            REMINDER_WORKER_REPEAT_FREQUENCY to task.repeatFrequency.name
        )

        return OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(workData)
    }

    init {
        getAllTasks()
        getAllCategories()
        cleanUnusedCategories()
        readSortState()
    }


}