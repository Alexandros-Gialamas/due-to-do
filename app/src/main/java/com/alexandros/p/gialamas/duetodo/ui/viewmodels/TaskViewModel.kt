package com.alexandros.p.gialamas.duetodo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {



    // Display Task
    val taskId: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val dueDate: MutableState<Long?> = mutableStateOf(null)
    val taskPriority: MutableState<TaskPriority> = mutableStateOf(TaskPriority.LOW)
    val isCompleted : MutableState<Boolean> = mutableStateOf(false)
    val isPopAlarmSelected : MutableState<Boolean> = mutableStateOf(false)

    fun updateDisplayTaskFields(selectedTask: TaskTable?) {
        if (selectedTask != null) {
            taskId.value = selectedTask.taskId
            title.value = selectedTask.title
            description.value = selectedTask.description
            dueDate.value = selectedTask.dueDate
            taskPriority.value = selectedTask.taskPriority
            isCompleted.value = selectedTask.isCompleted
            isPopAlarmSelected.value = selectedTask.isPopAlarmSelected
        }else {
            taskId.value = 0
            title.value = ""
            description.value = ""
            dueDate.value = null
            taskPriority.value = TaskPriority.LOW
            isCompleted.value = false
            isPopAlarmSelected.value = false
        }
    }

    // Task CRUD Operations
    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    fun validateFields() : Boolean {
        return title.value.isNotBlank() && description.value.isNotBlank()
    }

    private fun insertTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                title = title.value,
                description = description.value,
                taskPriority = taskPriority.value,
                dueDate = dueDate.value,
                isPopAlarmSelected = isPopAlarmSelected.value,
            )
            taskRepository.insertTask(task)
        }
        searchBarState.value = SearchBarState.CLOSED
    }
    private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId.value,
                title = title.value,
                description = description.value,
                taskPriority = taskPriority.value,
                dueDate = dueDate.value,
                isPopAlarmSelected = isPopAlarmSelected.value,
            )
            taskRepository.updateTask(task)
        }
    }
    private fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskTable(
                taskId = taskId.value,
                title = title.value,
                description = description.value,
                taskPriority = taskPriority.value,
                dueDate = dueDate.value,
                isPopAlarmSelected = isPopAlarmSelected.value,
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

            }
        }
        this.action.value = Action.NO_ACTION
    }


    // Search Bar
    val searchBarState: MutableState<SearchBarState> =
        mutableStateOf(SearchBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")
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
        searchBarState.value = SearchBarState.TRIGGERED
    }


    // Get All Tasks
    private val _allTasks =
        MutableStateFlow<RequestState<List<TaskTable>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<TaskTable>>> = _allTasks

    fun getAllTasks() {
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
}