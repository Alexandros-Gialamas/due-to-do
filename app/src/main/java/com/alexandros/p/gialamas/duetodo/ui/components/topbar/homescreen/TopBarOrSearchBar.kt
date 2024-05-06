package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.SearchBarState


//@Composable
//fun TopBarOrSearchBar(
//    taskViewModel: TaskViewModel,
//    searchBarState: SearchBarState,
//    searchTextState: String
//) {
//
//
//    when (searchBarState) {
//
//        SearchBarState.CLOSED -> {
//            TopBar(
//                onSearchClicked = { taskViewModel.searchBarState.value = SearchBarState.OPENED },
//                onSortClicked = {},
//                onDeleteAllTasksClicked = { taskViewModel.action.value = Action.DELETE_ALL },
//                onMenuItemClicked = {}
//                )
//        }
//
//        SearchBarState.CLEARED -> {
//            SearchBar(
//                text = searchTextState,
//                onTextChange = { newText ->
//                    taskViewModel.searchTextState.value = newText },
//                onCloseClicked = { /*TODO*/ },
//                onSearchClicked = {})
//        }
//
//        SearchBarState.OPENED -> {
//            SearchBar(
//                text = searchTextState,
//                onTextChange = { newText ->
//                    taskViewModel.searchTextState.value = newText },
//                onCloseClicked = {
//                    when {
//                        searchTextState == "" -> {
//                            taskViewModel.searchBarState.value =
//                                SearchBarState.CLOSED
//                        }
//
//                        searchTextState.isNotBlank() -> {
//                            taskViewModel.searchTextState.value = ""
////                            SearchBarState.TRIGGERED
//                        }
//                    }
//                },
//                onSearchClicked = { searchQuery ->
//                    taskViewModel.searchDatabase(searchQuery)
//                })
//        }
//
//        SearchBarState.TRIGGERED -> {
//            SearchBar(
//                text = searchTextState,
//                onTextChange = { newText ->
//                    taskViewModel.searchTextState.value = newText },
//                onCloseClicked = {
//                    when {
//                        searchTextState == "" -> {
//                            taskViewModel.searchBarState.value =
//                            SearchBarState.CLOSED
//                        }
//
//                        searchTextState.isNotBlank() -> {
//                            taskViewModel.searchTextState.value = ""
//
//                        }
//                    }
//                },
//                onSearchClicked = { searchQuery ->
//                    taskViewModel.searchDatabase(searchQuery)
//                }
//            )
//        }
//    }
//
//}