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
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.ONE_TAB_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun DisplayTask(
    title: String,
    onTitleChange: (String) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    taskPriority: TaskPriority,
    onTaskPriorityChange: (TaskPriority) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color   // TODO { remove parameters }
) {
    Column(
        modifier = Modifier
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


            OutlinedTextField(
                modifier = Modifier
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
    )
}

@Composable
@Preview
fun DisplayTaskPreview() {
    DisplayTask(
        title = "Go to the Doctor",
        onTitleChange = {},
        category = "",
        onCategoryChange = {},
        description = "appointment at 12:00pm",
        onDescriptionChange = {},
        taskPriority = TaskPriority.MEDIUM,
        onTaskPriorityChange = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}