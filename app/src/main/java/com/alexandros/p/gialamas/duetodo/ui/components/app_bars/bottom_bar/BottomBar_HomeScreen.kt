package com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionTaskCategorySelect
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionOverDueTasks
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionSortByPriority
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionSortByDate
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionSwitchLayout
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarHomeScreen(
    modifier: Modifier = Modifier,
    taskCategories: RequestState<List<TaskCategoryTable>>,
    onCategoryClicked: (selectedCategory: String) -> Unit,
    onPrioritySortClicked: (taskPriority: TaskPriority) -> Unit,
    onDateSortClicked: (dateSortOrder : DateSortOrder) -> Unit,
    prioritySortState: RequestState<TaskPriority>,
    dateSortOrder: RequestState<DateSortOrder>,
    categoryState: RequestState<String>,
    isGridLayout: RequestState<Boolean>,
    showOverdueTasksState: RequestState<Boolean>,
    areOverdueTasksState: Boolean,
    onLayoutClicked: (Boolean) -> Unit,
    onShowOverdueTasksClicked: (Boolean) -> Unit,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
) {
    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.3f)

    TopAppBar(
        modifier = modifier
            .background(editedBackgroundColor)
            .height(Dp.Unspecified),
        title = { Text(text = "") },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {

//                    Spacer(modifier = modifier.padding(start = MEDIUM_PADDING))

//                    ActionCheckList(
//                        onCheckListClicked = onNewCheckListClicked,
//                        myContentColor = myContentColor,
//                    )

                    Spacer(modifier = modifier.padding(start = MEDIUM_PADDING))

                    if (isGridLayout is RequestState.Success) {
                        ActionSwitchLayout(
                            onLayoutClicked = { onLayoutClicked(!isGridLayout.data) },
                            isGridLayout = isGridLayout.data,
                            myContentColor = myContentColor,
                        )
                    }

                    Spacer(modifier = modifier.padding(start = MEDIUM_PADDING))

                    ActionSortByDate(
                        dateSortOrder = dateSortOrder,
                        onDateSortClicked = onDateSortClicked
                    )

                    Spacer(modifier = modifier.padding(start = MEDIUM_PADDING))

                    ActionSortByPriority(
                        onPrioritySortClicked = onPrioritySortClicked,
                        prioritySortState = prioritySortState,
                    )

                    Spacer(modifier = modifier.padding(start = MEDIUM_PADDING))

                    if (taskCategories is RequestState.Success) {
                        ActionTaskCategorySelect(
                            taskCategories = taskCategories.data,
                            onCategoryClicked = onCategoryClicked,
                            categoryState = categoryState
                        )
                    }

                    Spacer(modifier = modifier.padding(start = MEDIUM_PADDING))

                    if (showOverdueTasksState is RequestState.Success){
                        ActionOverDueTasks(
                            onShowOverdueTasksClicked = { onShowOverdueTasksClicked(!showOverdueTasksState.data) },
                            areOverdueTasksState = areOverdueTasksState,
                            showOverdueTasksState = showOverdueTasksState.data
                        )
                }


                }
            )
        },
        actions = {}
    )
}
