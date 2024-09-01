package com.alexandros.p.gialamas.duetodo.ui.components.tasks.taskscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.ONE_TAB_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel

@Composable
fun DisplayTask(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel,
    listItemDescription: String,
    onListItemDescriptionChange: (String) -> Unit,
    isCompleted: Boolean,
    title: String,
    onTitleChange: (String) -> Unit,
    isCheckList: Boolean,
    checkListItems: List<CheckListItem>?,
    updateCheckListItems: (List<CheckListItem>) -> Unit,
    onCheckedChange: () -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    taskPriority: TaskPriority,
    onTaskPriorityChange: (TaskPriority) -> Unit,
    // TODO { revisit check parameters }
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(ONE_TAB_PADDING),
        content = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title,
                onValueChange = { onTitleChange(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.Display_Task_Title),
                        style = MaterialTheme.typography.labelLarge,
                    )
                },
                textStyle = MaterialTheme.typography.titleLarge,
                singleLine = true,
                colors = TextFieldDefaults.myTextFieldColors
            )


            Spacer(modifier = Modifier.height(ONE_TAB_PADDING))

            if (isCheckList) {
                if (checkListItems != null) {
                    DisplayChecklist(
                        checkListItemsData = checkListItems,
                        updateCheckListItems = updateCheckListItems
                    )
                }
            } else {

                OutlinedTextField(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent),
                    value = description,
                    onValueChange = { onDescriptionChange(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.Display_Task_Description),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.myTextFieldColors
                )
            }
        }
    )
}
