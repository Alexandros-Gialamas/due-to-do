package com.alexandros.p.gialamas.duetodo.ui.viewmodels

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.DataStoreRepository
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.ui.theme.taskItemBackgroundColor
import com.alexandros.p.gialamas.duetodo.util.Action
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
    private val dataStoreRepository : DataStoreRepository
) : ViewModel() {



    // Display Task
    var taskId by mutableStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    fun updateTitle(newTitle : String){
        if (newTitle.length <= MAX_TASK_TITLE_LENGTH) {
            title = newTitle
        }
    }
    var description by mutableStateOf("")
        private set
    fun updateDescription(newDescription : String){
        description = newDescription
    }
    var dueDate by mutableStateOf(null)
        private set
    fun updateDueDate(newDueDate : Long?){
        dueDate = newDueDate as Nothing? // TODO { Be careful with that too }
    }
    var taskPriority by mutableStateOf(TaskPriority.LOW)
        private set
    fun updateTaskPriority(newTaskPriority : TaskPriority){
        taskPriority = newTaskPriority
    }
    var isCompleted by mutableStateOf(false)
        private set
    fun updateIsCompleted(newIsCompleted : Boolean){
        isCompleted = newIsCompleted
    }
    var isPopAlarmSelected by mutableStateOf(false)
        private set
    fun updateIsPopAlarmSelected(newIsPopAlarmSelected : Boolean){
        isPopAlarmSelected = newIsPopAlarmSelected
    }

    fun updateDisplayTaskFields(selectedTask: TaskTable?) {
        if (selectedTask != null) {
            taskId = selectedTask.taskId
            title = selectedTask.title
            description = selectedTask.description
            dueDate = selectedTask.dueDate as Nothing?  // TODO { Be careful with that }
            taskPriority = selectedTask.taskPriority
            isCompleted = selectedTask.isCompleted
            isPopAlarmSelected = selectedTask.isPopAlarmSelected
        }else {
            taskId = 0
            title = ""
            description = ""
            dueDate = null
            taskPriority = TaskPriority.LOW
            isCompleted = false
            isPopAlarmSelected = false
        }
    }



    // Task CRUD Operations
//    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    var action by mutableStateOf(Action.NO_ACTION)
        private set
    fun updateAction(newAction : Action) {
        action = newAction
    }

    fun validateFields() : Boolean {
        return title.isNotBlank() && description.isNotBlank()
    } //TODO { Only Title is enough }

    private fun insertTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                title = title,
                description = description,
                taskPriority = taskPriority,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
            )
            taskRepository.insertTask(task)
        }
        searchBarState = SearchBarState.CLOSED
    }
    private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId,
                title = title,
                description = description,
                taskPriority = taskPriority,
                dueDate = dueDate,
                isPopAlarmSelected = isPopAlarmSelected,
            )
            taskRepository.updateTask(task)
        }
    }
    private fun deleteTask(){
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

    private fun deleteAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteAllTasks()
        }
    }

    fun handleDatabaseActions(action : Action){
        when (action) {
            Action.INSERT -> {
                insertTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                insertTask()
            }
            else -> {
                Action.NO_ACTION  // TODO { check if it works }
            }
        }
    }





    // Search Bar
    var searchBarState by mutableStateOf(SearchBarState.CLOSED)
        private set
    fun updateSearchBarState(newSearchBarState : SearchBarState){
        searchBarState = newSearchBarState
    }
    var searchTextState by mutableStateOf("")
        private set
    fun updateSearchTextState(newSearchTextState : String){
        searchTextState = newSearchTextState
    }
    private val _searchedTasks =
        MutableStateFlow<RequestState<List<TaskTable>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<TaskTable>>> = _searchedTasks

    fun searchDatabase(searchQuery : String) {
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
    val lowTaskPrioritySort : StateFlow<List<TaskTable>> =
        taskRepository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val highTaskPrioritySort : StateFlow<List<TaskTable>> =
        taskRepository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _sortState =
        MutableStateFlow<RequestState<TaskPriority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<TaskPriority>> = _sortState

    private fun readSortState(){
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

    fun persistSortState(taskPriority: TaskPriority){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(taskPriority = taskPriority)
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
        readSortState()
    }


}