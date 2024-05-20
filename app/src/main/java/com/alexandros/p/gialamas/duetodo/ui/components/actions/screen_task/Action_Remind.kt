package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.components.dialogs.DateTimePickerDialog
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.alexandros.p.gialamas.duetodo.util.CustomDateIcon
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionReminder(
    onRemindClicked: () -> Unit,
    dueDate: Long?,
    onDueDateChanged: (Long?) -> Unit,
    context: Context,
    showToastPickADate: () -> Unit,
    showToastInvalidDate: () -> Unit,
    onRepeatFrequencyChanged: (RepeatFrequency) -> Unit,
//    onRepeatChange: (String) -> Unit,
//    showRepeat : Boolean = true,
    myActivatedColor: Color,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    val calendar = Calendar.getInstance()
    dueDate?.let { calendar.timeInMillis = it }
    val formattedDate = if (dueDate != null) {
        SimpleDateFormat("dd MMM", Locale.getDefault()).format(calendar.time)
    } else {
        "Set Date"
    }
    val formattedTime = if (dueDate != null) {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
    } else {
        "Set Time"
    }

    var parentHeight by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableStateOf(0.dp) }
    var repeatFrequency by remember { mutableStateOf(RepeatFrequency.NONE) }
    var popupPosition by remember { mutableStateOf(IntOffset.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var isRepeatExpanded by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(LocalTime.MIDNIGHT) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val dropDownBackgroundColor = myBackgroundColor.copy(alpha = 0.9f)

    var isDueDateDiscarded by remember { mutableStateOf(false) }


    datePickerState.selectedDateMillis?.let { selectedDateMillis ->
        selectedDate = Instant.ofEpochMilli(selectedDateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
    selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
    val localDateTime = LocalDateTime.of(selectedDate, selectedTime)
    val zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())


    if (showTimePicker || showDatePicker) {
        DateTimePickerDialog(
            timeState = timePickerState,
            dateState = datePickerState,
            isDatePickerShowed = showDatePicker,
            layoutConfiguration = LocalConfiguration.current,  // TODO { remove it from local }
            showToastInvalidDate = showToastInvalidDate,
            showToastPickADate = showToastPickADate,
            closeDialog = {
                if (showDatePicker) {
                    showDatePicker = false
                } else {
                    showTimePicker = false
                    showDatePicker = true
                }
                isRepeatExpanded = false
            },
            onSetDateConfirm = {
                isRepeatExpanded = false
                showDatePicker = false
                showTimePicker = true
            },
            onSetTimeConfirm = {
                onDueDateChanged(zonedDateTime.toInstant().toEpochMilli())
                showTimePicker = false
            },
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    }


    Column(
        modifier = Modifier,
        content = {
            IconButton(
                onClick = {
                    isRepeatExpanded = false
                    expanded = !expanded
                }
            ) {
                Icon(
                    painter =
                    if (dueDate != null) {
                        painterResource(id = R.drawable.ic_notification_added)
                    } else {
                        painterResource(id = R.drawable.ic_add_notification)
                    },
                    contentDescription = stringResource(id = R.string.Reminder_Task_Icon_Description),
                    tint = if (dueDate != null) myActivatedColor else myContentColor
                )
            }

            DropdownMenu(
                modifier = DropDownMenuModifier(
                    background = dropDownBackgroundColor,
                    borderOne = myContentColor,
                    borderTwo = myBackgroundColor
                ),
                expanded = expanded,
                onDismissRequest = {
                    isRepeatExpanded = false
                    expanded = false
                },
                content = {

                    DropdownMenuItem(
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    isRepeatExpanded = false
                                    showDatePicker = true
                                }) {
                                CustomDateIcon(iconSize = 24.dp, myContentColor = myContentColor)
                            }
                        },
                        text = {
                            Text(
                                text = formattedDate,
                                style = MaterialTheme.typography.titleMedium,
                                color = myTextColor
                            )

                        },
                        trailingIcon = {
                            ArrowIcon(
                                onIconClicked = {
                                    isRepeatExpanded = false
                                    showDatePicker = true
                                },
                                tintColor = myContentColor
                            )
                        },
                        onClick = {
                            isRepeatExpanded = false
                            showDatePicker = true
                        }
                    )

                    DropdownMenuItem(
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    isRepeatExpanded = false
                                    showDatePicker = true
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_schedule),
                                    contentDescription = stringResource(id = R.string.Time_Icon_description),
                                    tint = myContentColor
                                )
                            }
                        },
                        text = {
                            Text(
                                text = formattedTime,
                                style = MaterialTheme.typography.titleMedium,
                                color = myTextColor
                            )
                        },
                        trailingIcon = {
                            ArrowIcon(
                                onIconClicked = {
                                    isRepeatExpanded = false
                                    showDatePicker = true
                                },
                                tintColor = myContentColor
                            )
                        },
                        onClick = {
                            isRepeatExpanded = false
                            showDatePicker = true
                        }
                    )

                    Column(
                        content = {
                            DropdownMenuItem(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        popupPosition = IntOffset(
                                            (coordinates.positionInWindow().x.toInt()
                                                    + (coordinates.size.width * 0.38)).toInt(),
                                            coordinates.positionInWindow().y.toInt()
                                                    + (coordinates.size.height * 7.8).toInt()
                                        )
                                    },
                                leadingIcon = {
                                    IconButton(
                                        onClick = { isRepeatExpanded = !isRepeatExpanded })
                                    {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_repeat),
                                            contentDescription = stringResource(id = R.string.Repeat_Icon_description),
                                            tint = myContentColor
                                        )
                                    }
                                },

                                text = {

                                    Text(
                                        text = stringResource(id = repeatFrequency.displayText),  // TODO { hardcode value }
                                        style = MaterialTheme.typography.titleMedium,
                                        color = myTextColor
                                    )
                                    if (isRepeatExpanded) {
                                        ActionRemindRepeat(
                                            popUpPosition = popupPosition,
                                            onRepeatFrequencySelected = { frequency ->
                                                repeatFrequency = frequency
                                                onRepeatFrequencyChanged(frequency)
                                                isRepeatExpanded = false
                                            },
                                            myBackgroundColor = myBackgroundColor,
                                            myContentColor = myContentColor,
                                            myTextColor = myTextColor
                                        )
                                    }
                                },
                                trailingIcon = {
                                    ArrowIcon(
                                        onIconClicked = { isRepeatExpanded = !isRepeatExpanded },
                                        tintColor = myContentColor
                                    )
                                },
                                onClick = {
                                    isRepeatExpanded = !isRepeatExpanded
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}


@Composable
private fun DropDownMenuModifier(
    background: Color,
    borderOne: Color,
    borderTwo: Color
):
        Modifier = Modifier
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


@Composable
private fun ArrowIcon(
    onIconClicked: () -> Unit,
    tintColor: Color
) {
    IconButton(
        onClick = onIconClicked
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = stringResource(id = R.string.Arrow_Drop_Down_Menu_Icon_description),
            tint = tintColor
        )
    }
}





