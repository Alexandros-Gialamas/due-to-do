package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.ONE_TAB_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.SMALL_PADDING
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
fun ActionSortByPriority(
    modifier: Modifier = Modifier,
    onPrioritySortClicked: (taskPriority: TaskPriority) -> Unit,
    prioritySortState: RequestState<TaskPriority>,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {
    var expanded by remember { mutableStateOf(false) }
    val notActiveFilters =
        (prioritySortState is RequestState.Success && prioritySortState.data == TaskPriority.NONE)
    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 850f)

    Column {


        IconButton(
            onClick = { expanded = !expanded },
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_filter_tasks),
                contentDescription = stringResource(id = R.string.BottomBar_Action_Sort_By_Priority_Description),
                tint = if (notActiveFilters) myContentColor else myActivatedColor
            )

            DropdownMenu(
                modifier = modifier
                    .clip(HOME_SCREEN_ROUNDED_CORNERS)
                    .background(dropDownBackgroundColor)
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
                offset = DpOffset(x = (-40).dp, y = (0).dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {

                    TaskPriority.entries.filter { it != TaskPriority.MEDIUM }
                        .forEach { priority ->

                            val isSelected = prioritySortState is RequestState.Success && prioritySortState.data == priority
                            val borderWidth = if (isSelected) 1.5.dp else 0.dp
                            val borderColor = if (isSelected) myActivatedColor else Color.Transparent

                            DropdownMenuItem(
                                modifier = modifier
                                    .padding(SMALL_PADDING)
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Canvas(
                                        modifier = modifier
                                            .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE)
                                            .border(
                                                width = borderWidth,
                                                shape = RoundedCornerShape(
                                                    ONE_TAB_PADDING * 2),
                                                color = borderColor),
                                        onDraw = {
                                            drawCircle(
                                                color = priority.color,
                                                radius = size.minDimension / 2,
                                            )
                                        }
                                    )
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
    }
}






