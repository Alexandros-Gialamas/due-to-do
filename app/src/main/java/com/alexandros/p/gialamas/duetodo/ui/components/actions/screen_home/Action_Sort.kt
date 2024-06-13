package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.ONE_TAB_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.util.CustomDateIcon
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RequestState

@Composable
fun ActionSort(
    modifier: Modifier = Modifier,
    onPrioritySortClicked: (taskPriority: TaskPriority) -> Unit,
    onDateSortClicked: (dateSortOrder : DateSortOrder) -> Unit,
    prioritySortState: RequestState<TaskPriority>,
    dateSortOrder: RequestState<DateSortOrder>,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {
    var expanded by remember { mutableStateOf(false) }
    val notActiveFilters =
        (prioritySortState is RequestState.Success && prioritySortState.data == TaskPriority.NONE) &&
                (dateSortOrder is RequestState.Success && dateSortOrder.data == DateSortOrder.ASCENDING)
    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 1900 / 1f)


    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_tasks),
            contentDescription = stringResource(id = R.string.BottomBar_Action_Sort_Description),
            tint = if (notActiveFilters) myContentColor else myActivatedColor
        )

        DropdownMenu(
            modifier = modifier
                .clip(HOME_SCREEN_ROUNDED_CORNERS)
                .background(dropDownBackgroundColor)
                .fillMaxWidth(0.85f)
                .border(
                    width = FIRST_BORDER_STROKE,
                    color = myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA),
                    shape = HOME_SCREEN_ROUNDED_CORNERS
                )
                .border(
                    width = SECOND_BORDER_STROKE,
                    color = myBackgroundColor,
                    shape = HOME_SCREEN_ROUNDED_CORNERS
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            content = {

                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {

                        Column(
                            modifier = modifier
                                .weight(1f),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.Start,
                            content = {

                                DropdownMenuItem(
                                    leadingIcon = {
                                        IconButton(
                                            onClick = {
                                                  if (dateSortOrder is RequestState.Success) {
                                                        when {
                                                            dateSortOrder.data == DateSortOrder.DESCENDING -> {
                                                                onDateSortClicked(DateSortOrder.ASCENDING)
                                                            }
                                                            else ->  onDateSortClicked(DateSortOrder.DESCENDING)
                                                        }
                                                }
                                                expanded = !expanded
                                    }
                                        ) {
                                            CustomDateIcon(
                                                hasArrowUp = false,
                                                hasArrowDown = true,
                                                myContentColor = if (dateSortOrder is RequestState.Success) {
                                                    when {
                                                        dateSortOrder.data == DateSortOrder.DESCENDING -> myActivatedColor
                                                        else -> myContentColor
                                                    }
                                                } else myContentColor
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = if (dateSortOrder is RequestState.Success) {
                                                when{
                                                    dateSortOrder.data == DateSortOrder.DESCENDING -> {
                                                        stringResource(id = R.string.Ascending_Text)
                                                    }
                                                    else -> stringResource(id = R.string.Descending_Text)
                                                }
                                            } else  stringResource(id = R.string.Ascending_Text),
                                            color = myTextColor,
                                            style = MaterialTheme.typography.bodySmall,
                                            softWrap = true,
                                            maxLines = 1,
                                            textAlign = TextAlign.Start
                                        )
                                    },
                                    onClick = {
                                        if (dateSortOrder is RequestState.Success) {
                                            when {
                                                dateSortOrder.data == DateSortOrder.DESCENDING -> {
                                                    onDateSortClicked(DateSortOrder.ASCENDING)
                                                }
                                                else ->  onDateSortClicked(DateSortOrder.DESCENDING)
                                            }
                                        }
                                        expanded = !expanded
                                    }
                                )

                                Spacer(modifier = modifier.padding(top = ONE_TAB_PADDING))

                                Divider(
                                    modifier = modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(start = ONE_TAB_PADDING),
                                    thickness = 1.dp,
                                    color = myContentColor)

                                Spacer(modifier = modifier.padding(bottom = ONE_TAB_PADDING))

                                DropdownMenuItem(
                                    leadingIcon = {
                                        IconButton(
                                            onClick = {
                                                onDateSortClicked(DateSortOrder.ASCENDING)
                                                onPrioritySortClicked(TaskPriority.NONE)
                                                expanded = !expanded
                                            }
                                        ) {
                                            Icon(imageVector = Icons.Filled.Close, 
                                                contentDescription = stringResource(id = R.string.SearchBar_Action_Clear_Description),
                                                tint = myContentColor)
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.SearchBar_Action_Clear_Description),
                                            color = myTextColor,
                                            style = MaterialTheme.typography.bodySmall,
                                            softWrap = true,
                                            maxLines = 1,
                                            textAlign = TextAlign.Start
                                        )
                                    },
                                    onClick = {
                                        onDateSortClicked(DateSortOrder.ASCENDING)
                                        onPrioritySortClicked(TaskPriority.NONE)
                                        expanded = !expanded
                                    }
                                )

                            }
                        )



                        Box (
                            modifier = modifier
                                .height(110.dp)  // TODO { fixed height value }
                                .width(1.dp)
                                .background(myContentColor)
                        )




                        Column(
                            modifier = modifier
                                .weight(1f),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.Start,
                            content = {
                                TaskPriority.entries.filter { it != TaskPriority.MEDIUM  }.forEach{ priority ->

                                    val isSelected = prioritySortState is RequestState.Success && prioritySortState.data == priority
                                    val borderWidth = if (isSelected) 1.5.dp else 0.dp
                                    val borderColor = if (isSelected) myActivatedColor else Color.Transparent


                                    DropdownMenuItem(
                                        leadingIcon = {
                                            IconButton(
                                                onClick = {
                                                onPrioritySortClicked(priority)
                                                expanded = !expanded }
                                            ) {
                                                Canvas(
                                                    modifier = modifier
                                                        .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE)
                                                        .border(
                                                            width = borderWidth,
                                                            shape = RoundedCornerShape(
                                                                ONE_TAB_PADDING * 2),
                                                            color = borderColor),
//                                                        .padding(2.dp),
                                                    onDraw = {
                                                        drawCircle(
                                                            color = priority.color,
                                                            radius = size.minDimension / 2
                                                            )
                                                    }
                                                )
                                            }
                                        },
                                        text = {
                                            Text(
                                                text =
                                                priority.name,
                                                color = myTextColor,
                                                style = MaterialTheme.typography.bodySmall,
                                                softWrap = true,
                                                textAlign = TextAlign.Start
                                            )
                                        },
                                        onClick = {
                                            onPrioritySortClicked(priority)
                                            expanded = !expanded
                                        }
                                    )
                                }
                            }
                        )

                    }
                )
            }
        )
    }

}






