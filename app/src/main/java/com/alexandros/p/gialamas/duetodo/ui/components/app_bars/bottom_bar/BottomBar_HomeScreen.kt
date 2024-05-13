package com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionTaskCategorySelect
import com.alexandros.p.gialamas.duetodo.ui.components.actions.ActionCheckList
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.sort.ActionSort
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionSwitchLayout
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarHomeScreen(
    taskCategories: RequestState<List<TaskCategoryTable>>,
    onCategoryClicked: (category: String) -> Unit,
    onSortClicked: (taskPriority: TaskPriority) -> Unit,
    isGridLayout: Boolean,
    onLayoutClicked: () -> Unit,
    onNewCheckListClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.3f)

    TopAppBar(
        modifier = Modifier
            .background(editedBackgroundColor)
            .height(40.dp),  // TODO { hardcode value }
        title = { Text(text = "") },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    ActionCheckList(
                        onNewCheckListClicked = onNewCheckListClicked,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    ActionSwitchLayout(
                        onLayoutClicked = onLayoutClicked,
                        isGridLayout = isGridLayout,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    ActionSort(
                        onSortClicked = onSortClicked,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    if (taskCategories is RequestState.Success) {
                        ActionTaskCategorySelect(
                            taskCategories = taskCategories.data,
                            onCategoryClicked = onCategoryClicked,
                            myBackgroundColor = myBackgroundColor,
                            myContentColor = myContentColor,
                            myTextColor = myTextColor
                        )
                    }
                }
            )
        },
        actions = {}
    )
}

//@Composable
//@Preview
//private fun BottomBarHomeScreenPreview() {
//    BottomBarHomeScreen(
//        taskCategories = RequestState<listOf(
//            TaskCategoryTable(
//                categoryId = 0,
//                categoryName = "DELIVERIES"
//            )
//        )>,
//        onCategoryClicked = {},
//        onSortClicked = {},
//        onNewCheckListClicked = {},
//        onLayoutClicked = {},
//        isGridLayout = true,
//        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
//        myContentColor = MaterialTheme.colorScheme.myContentColor,
//        myTextColor = MaterialTheme.colorScheme.myTextColor
//    )
//}
