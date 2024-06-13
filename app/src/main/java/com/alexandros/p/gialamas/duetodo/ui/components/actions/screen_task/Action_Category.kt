package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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

//    var popupPosition by remember { mutableStateOf(IntOffset.Zero) }
//    var popupHeight by remember { mutableStateOf(0.dp) }
//    val configuration = LocalConfiguration.current

    Column(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                parentWidth = coordinates.size.width.dp
                parentHeight = coordinates.size.height.dp
            },
        horizontalAlignment = Alignment.Start,
        content = {

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
                        .fillMaxWidth(0.9f),
                    background = dropDownBackgroundColor,
                    borderOne = myContentColor,
                    borderTwo = myBackgroundColor
                ),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {


                    OutlinedTextField(
                        modifier = OutlinedTextFieldModifier(
                            background = myBackgroundColor,
                            borderOne = myContentColor,
                            borderTwo = myContentColor
                        ),
                        value = newCategory,
                        onValueChange = { newCategory = it },
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
                                color = myTextColor
                            )
                        },
                        trailingIcon = {

                            Row(
                                content = {

                                    ActionClear(
                                        textState = newCategory,
                                        onClearClicked = { newCategory = "" },
                                        myContentColor = myContentColor,
                                    )
                                    if (newCategory.isNotBlank()) {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    keyboardController?.hide()
                                                    delay(KEYBOARD_DELAY)
                                                    onCategoryClicked(newCategory)
                                                    expanded = false
                                                }
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
                        ),
                        singleLine = true,

                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                scope.launch {
                                    keyboardController?.hide()
                                    delay(KEYBOARD_DELAY)
                                    onCategoryClicked(newCategory)
                                    expanded = false
                                }
                            }
                        ),
                        colors = TextFieldDefaults.myTextFieldColors
                    )


                    LazyVerticalStaggeredGrid(
                        modifier = modifier
                            .height(
                                if (parentHeight <= 300.dp) parentHeight else parentHeight / 2
                            )
                            .width(parentWidth * 3)
                            .background(Color.Transparent),
                        columns = StaggeredGridCells.Adaptive(minSize = 120.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                        contentPadding = PaddingValues(LARGE_PADDING), // TODO { paddings }

                        content = {
                            items(taskCategoryList.size) { index ->
                                val taskCategory = taskCategoryList[index]

                                var itemAppear by remember { mutableStateOf(false) }

                                LaunchedEffect(key1 = true) {
                                    itemAppear = true
                                }


                                AnimatedVisibility(
                                    visible = itemAppear && expanded,
                                    enter = expandVertically(
                                        animationSpec = tween(400, easing = EaseInOutQuart)
                                    ),
                                    exit = shrinkVertically(
                                        animationSpec = tween(400, easing = EaseInOutQuart)
                                    )
                                ) {
                                    Box(
                                        contentAlignment = Alignment.BottomCenter
                                    ) {
                                        DropdownMenuItem(
                                            modifier = modifier
                                                .fillMaxWidth(),
                                            leadingIcon = {
                                                val pointCategory =
                                                    category == taskCategory.categoryName
                                                IconButton(
                                                    onClick = {
                                                        scope.launch {
                                                            keyboardController?.hide()
                                                            delay(KEYBOARD_DELAY)
                                                            onCategoryClicked(taskCategory.categoryName)
                                                            expanded = false
                                                        }
                                                    }
                                                ) {
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
                                                }
                                            },
                                            text = {
                                                Text(
                                                    modifier = modifier,
                                                    text = taskCategory.categoryName,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    textAlign = TextAlign.Justify,
                                                    softWrap = true,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 3,
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
                            }
                        }
                    )





                    DropdownMenuItem(
                        modifier = modifier
                            .align(alignment = Alignment.End)
                            .padding(start = parentWidth),
                        text = {
                            Text(
                                modifier = modifier,
                                text = stringResource(id = R.string.Category_None_Text),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start,
                                color = myTextColor
                            )
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        keyboardController?.hide()
                                        delay(KEYBOARD_DELAY)
                                        newCategory = ""
                                        onCategoryClicked(newCategory)
                                        expanded = false
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_label_off),
                                    contentDescription = stringResource(id = R.string.Category_Off_Icon_Description),
                                    tint = myContentColor
                                )
                            }
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
            )
        }
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
    .width(IntrinsicSize.Max)
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

@Composable
private fun OutlinedTextFieldModifier(
    modifier: Modifier = Modifier,
    background: Color,
    borderOne: Color,
    borderTwo: Color
): Modifier = modifier
    .clip(HOME_SCREEN_ROUNDED_CORNERS)
    .fillMaxWidth()
    .background(background)
    .border(
        BorderStroke(
            width = FIRST_BORDER_STROKE,
            color = borderOne.copy(alpha = LIGHT_BORDER_STROKE_ALPHA)
        ),
        shape = HOME_SCREEN_ROUNDED_CORNERS
    )
    .border(
        BorderStroke(
            width = SECOND_BORDER_STROKE,
            color = borderTwo.copy(alpha = HIGH_BORDER_STROKE_ALPHA)
        ),
        shape = HOME_SCREEN_ROUNDED_CORNERS
    )

@Composable
private fun columnModifier(
    modifier: Modifier = Modifier,
    background: Color,
    borderOne: Color,
    borderTwo: Color
): Modifier = modifier
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


