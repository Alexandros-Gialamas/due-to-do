package com.alexandros.p.gialamas.duetodo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.DataStoreRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskCategoryRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import com.alexandros.p.gialamas.duetodo.util.Constants.MAX_TASK_TITLE_LENGTH
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCategoryRepository: TaskCategoryRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val keyboardController = LocalSoftwareKeyboardController

    // Display Task
    var taskId by mutableStateOf(0)
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

    var category by mutableStateOf("")
        private set

    fun updateCategory(newCategory: String) {
        category = newCategory
    }

    var dueDate by mutableStateOf<Long?>(null)
        private set

    fun updateDueDate(newDueDate: Long?) {
        dueDate = newDueDate
    }

    var taskPriority by mutableStateOf(TaskPriority.LOW)
        private set

    fun updateTaskPriority(newTaskPriority: TaskPriority) {
        taskPriority = newTaskPriority
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

    fun updateDisplayTaskFields(selectedTask: TaskTable?) {
        if (selectedTask != null) {
            taskId = selectedTask.taskId
            title = selectedTask.title
            category = selectedTask.category
            description = selectedTask.description
            dueDate = selectedTask.dueDate
            taskPriority = selectedTask.taskPriority
            isPinned = selectedTask.isPinned
            isCompleted = selectedTask.isCompleted
            isPopAlarmSelected = selectedTask.isPopAlarmSelected
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
        }
    }

    private val _isGridLayout: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isGridLayout: StateFlow<Boolean> = _isGridLayout
    fun enableGridLayout() {
        _isGridLayout.value = !_isGridLayout.value
    }


    // Task CRUD Operations
//    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)
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
            val task = TaskTable(
                title = title,
                category = category,
                description = description,
                taskPriority = taskPriority,
                isPinned = isPinned,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
            )
            val category = TaskCategoryTable(
                categoryName = category
            )
            taskRepository.insertTask(task)
            taskCategoryRepository.insertCategory(category)
        }
        searchBarState = SearchBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId,
                title = title,
                category = category,
                description = description,
                taskPriority = taskPriority,
                isPinned = isPinned,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
            )
            val category = TaskCategoryTable(
                categoryName = category
            )
            taskRepository.updateTask(task)
            taskCategoryRepository.updateCategory(category)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId,
                title = title,
                description = description,
                taskPriority = taskPriority,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
            )
            taskRepository.deleteTask(task)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteAllTasks()
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


    init {
        getAllTasks()
        getAllCategories()
        readSortState()
    }


}