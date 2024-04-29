package com.alexandros.p.gialamas.duetodo.ui.components.topbar

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.searchbar.SearchBar
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.SearchBarState


@Composable
fun TopBarOrSearchBar(
    taskViewModel: TaskViewModel,
    searchBarState: SearchBarState,
    searchTextState : String
){



    when (searchBarState) {
        SearchBarState.CLOSED -> { TopBar (
            onSearchClicked = { taskViewModel.searchBarState.value = SearchBarState.OPENED }) }
        SearchBarState.CLEARED -> { SearchBar(
        text = searchTextState,
        onTextChange = { taskViewModel.searchTextState.value = it },
        onCloseClicked = { /*TODO*/ }) {

    } }
        SearchBarState.OPENED -> { SearchBar(
        text = searchTextState,
        onTextChange = { taskViewModel.searchTextState.value = it},
        onCloseClicked = {
            when {
                searchTextState == "" -> taskViewModel.searchBarState.value = SearchBarState.CLOSED
                searchTextState.isNotBlank() -> taskViewModel.searchTextState.value = ""
            }
        }) {

    } }
        SearchBarState.TRIGGERED -> { TopBar (onSearchClicked = {}) }
    }

}