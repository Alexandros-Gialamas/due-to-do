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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
fun ActionSortByDate(
    modifier: Modifier = Modifier,
    onDateSortClicked: (dateSortOrder: DateSortOrder) -> Unit,
    dateSortOrder: RequestState<DateSortOrder>,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
) {

    IconButton(
        onClick = {
            if (dateSortOrder is RequestState.Success) {
                when {
                    dateSortOrder.data == DateSortOrder.DESCENDING -> {
                        onDateSortClicked(DateSortOrder.ASCENDING)
                    }

                    else -> onDateSortClicked(DateSortOrder.DESCENDING)
                }
            }
        },
    ) {
        if (dateSortOrder is RequestState.Success) {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center,
                content = {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_today),
                        contentDescription = stringResource(id = R.string.BottomBar_Action_Sort_By_Date_Description),
                        tint = myContentColor
                    )

                    Icon(
                        modifier = modifier
                            .offset(y = (2.5).dp)
                            .size(16.dp),
                        imageVector = if (dateSortOrder.data == DateSortOrder.DESCENDING)
                            Icons.Default.KeyboardArrowDown else
                            Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(id = R.string.BottomBar_Action_Sort_By_Date_Description),
                        tint = if (dateSortOrder.data == DateSortOrder.DESCENDING) myActivatedColor else myContentColor
                    )
                }
            )
        }
    }
}






