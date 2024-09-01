package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionClear
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HIGH_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SEARCH_BAR_ICON_ALPHA_VALUE
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import com.alexandros.p.gialamas.duetodo.util.Constants.KEYBOARD_DELAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ActionCategory(
    modifier: Modifier = Modifier,
    category: String,
    taskCategoryList: List<TaskCategoryTable>,
    onCategoryClicked: (selectedCategory: String) -> Unit,
    scope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {
    var parentHeight by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableStateOf(0.dp) }
    var expanded by remember { mutableStateOf(false) }
    var newCategory by remember { mutableStateOf("") }
    val dropDownBackgroundColor = myBackgroundColor.copy(alpha = 0.9f)


    IconButton(
        onClick = {
            scope.launch {
                keyboardController?.hide()
                delay(KEYBOARD_DELAY)
                expanded = !expanded
            }
        }
    ) {
        Icon(
            painter =
            if (category.isBlank()) {
                painterResource(id = R.drawable.ic_label)
            } else {
                painterResource(id = R.drawable.ic_label_added)
            },
            contentDescription = stringResource(id = R.string.Category_Task_Icon_Description),
            tint = if (category.isBlank()) myContentColor else myActivatedColor
        )
    }

    DropdownMenu(
        modifier = DropDownMenuModifier(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    parentWidth = coordinates.size.width.dp
                    parentHeight = coordinates.size.height.dp
                },
            background = dropDownBackgroundColor,
            borderOne = myContentColor,
            borderTwo = myBackgroundColor
        ),
        offset = DpOffset(x = (-6).dp, y = (0).dp),
        expanded = expanded,
        onDismissRequest = { expanded = false },
        content = {


            ShowNewCategoryField(
                newCategory = newCategory,
                onNewCategoryChanged = { updateCategory -> newCategory = updateCategory },
                onDoneClicked = {
                    scope.launch {
                        keyboardController?.hide()
                        delay(KEYBOARD_DELAY)
                        onCategoryClicked(newCategory)
                        expanded = false
                    }
                }
            )


            LazyColumn(
                modifier = modifier
                    .height(
                        if (parentHeight <= 300.dp) parentHeight else 220.dp
                    )
                    .width(if (parentWidth <= 250.dp) parentWidth else 250.dp)
                    .background(Color.Transparent),
                content = {

                    item {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_label_off),
                                    contentDescription = stringResource(id = R.string.Category_Off_Icon_Description),
                                    tint = myContentColor
                                )
                            },
                            text = {
                                Text(
                                    modifier = modifier,
                                    text = stringResource(id = R.string.Category_None_Text),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis,
                                    color = myTextColor
                                )
                            },
                            onClick = {
                                scope.launch {
                                    keyboardController?.hide()
                                    delay(KEYBOARD_DELAY)
                                    newCategory = ""
                                    onCategoryClicked(newCategory)
                                    expanded = false
                                }
                            }
                        )
                    }

                    items(
                        taskCategoryList.size
                    ) { index ->
                        val taskCategory = taskCategoryList[index]

                        DropdownMenuItem(
                            leadingIcon = {
                                val pointCategory =
                                    category == taskCategory.categoryName
                                Icon(
                                    painter =
                                    if (pointCategory) {
                                        painterResource(id = R.drawable.ic_label_added)
                                    } else {
                                        painterResource(id = R.drawable.ic_label)
                                    },
                                    contentDescription = stringResource(id = R.string.Category_Task_Icon_Description),
                                    tint =
                                    if (pointCategory) myActivatedColor else myContentColor
                                )
                            },
                            text = {
                                Text(
                                    modifier = modifier,
                                    text = taskCategory.categoryName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    color = myTextColor // TODO { revisit }
                                )
                            },
                            onClick = {
                                scope.launch {
                                    keyboardController?.hide()
                                    delay(KEYBOARD_DELAY)
                                    onCategoryClicked(taskCategory.categoryName)
                                    expanded = false
                                }
                            },
                        )
                    }
                }
            )
        }
    )
}

@Composable
private fun ShowNewCategoryField(
    modifier: Modifier = Modifier,
    newCategory: String,
    onDoneClicked: () -> Unit,
    onNewCategoryChanged: (String) -> Unit,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor,
    myBorderOneColor: Color = MaterialTheme.colorScheme.myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA),
    myBorderTwoColor: Color = MaterialTheme.colorScheme.myContentColor.copy(alpha = HIGH_BORDER_STROKE_ALPHA)
) {
    var newCategoryName by remember { mutableStateOf(newCategory) }

    OutlinedTextField(
        modifier = modifier
            .widthIn(max = 250.dp)
            .clip(HOME_SCREEN_ROUNDED_CORNERS)
            .background(myBackgroundColor)
            .border(
                BorderStroke(
                    width = FIRST_BORDER_STROKE,
                    color = myBorderOneColor
                ),
                shape = HOME_SCREEN_ROUNDED_CORNERS
            )
            .border(
                BorderStroke(
                    width = SECOND_BORDER_STROKE,
                    color = myBorderTwoColor
                ),
                shape = HOME_SCREEN_ROUNDED_CORNERS
            ),
        value = newCategoryName,
        onValueChange = {
            newCategoryName = it
            onNewCategoryChanged(it)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_new_label),
                contentDescription = stringResource(id = R.string.Category_Task_Icon_Description),
                tint = myContentColor
            )
        },
        placeholder = {
            Text(
                modifier = modifier
                    .alpha(SEARCH_BAR_ICON_ALPHA_VALUE),
                text = stringResource(id = R.string.New_Category_Placeholder),
                style = typography.bodySmall.copy(
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    ),
                fontWeight = FontWeight.Light,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                color = myTextColor
            )
        },
        trailingIcon = {
            Row(
                content = {
                    ActionClear(
                        textState = newCategoryName,
                        onClearClicked = { newCategoryName = "" },
                        myContentColor = myContentColor,
                    )
                    if (newCategoryName.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onDoneClicked()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(id = R.string.Add_Category_Icon_Description),
                                tint = myContentColor
                            )
                        }
                    }
                }
            )
        },
        textStyle = TextStyle(
            color = myTextColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Justify,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClicked()
            }
        ),
        colors = TextFieldDefaults.myTextFieldColors
    )
}


@Composable
private fun DropDownMenuModifier(
    modifier: Modifier = Modifier,
    background: Color,
    borderOne: Color,
    borderTwo: Color
): Modifier = modifier
    .height(IntrinsicSize.Min)
    .width(IntrinsicSize.Min)
    .clip(HOME_SCREEN_ROUNDED_CORNERS)
    .background(background)
    .border(
        width = FIRST_BORDER_STROKE,
        color = borderOne.copy(alpha = LIGHT_BORDER_STROKE_ALPHA),
        shape = HOME_SCREEN_ROUNDED_CORNERS
    )
    .border(
        width = SECOND_BORDER_STROKE,
        color = borderTwo,
        shape = HOME_SCREEN_ROUNDED_CORNERS
    )
    .padding(LARGE_PADDING)



