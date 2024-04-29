package com.alexandros.p.gialamas.duetodo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.data.repositories.TaskRepository
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    // Search Bar
    val searchBarState : MutableState<SearchBarState> =
        mutableStateOf(SearchBarState.CLOSED)
    val searchTextState : MutableState<String> = mutableStateOf("")


    // Get All Tasks
    private val _allTasks =
        MutableStateFlow<List<TaskTable>>(emptyList())
    val allTasks : StateFlow<List<TaskTable>> = _allTasks

    fun getAllTasks(){
        viewModelScope.launch {
            taskRepository.getAllTasks.collect{
                    _allTasks.value = it
            }
        }
    }


}