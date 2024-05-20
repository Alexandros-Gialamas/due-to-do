package com.alexandros.p.gialamas.duetodo.ui.components.dialogs

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.DIALOG_BUTTON_SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.DIALOG_BUTTON_WIDTH
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HIGH_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myDatePickerColors
import com.alexandros.p.gialamas.duetodo.ui.theme.myTimePickerColors
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(
    timeState: TimePickerState,
    dateState: DatePickerState,
    layoutConfiguration: Configuration,
    isDatePickerShowed: Boolean,
    closeDialog: () -> Unit,
    onSetDateConfirm: () -> Unit,
    onSetTimeConfirm: () -> Unit,
    showToastPickADate: () -> Unit,
    showToastInvalidDate: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    val currentCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
    }.timeInMillis

    val timeDialogBackgroundColor = Brush.myBackgroundBrush(radius = 1800f / 1f)
    val dateDialogBackgroundColor = Brush.myBackgroundBrush(radius = 2500f / 1f)

    Dialog(
        onDismissRequest = { closeDialog() },
        content = {
            Surface(
                color = Color.Transparent,
                shape = TASK_ITEM_ROUNDED_CORNERS,
                modifier = Modifier
//                    .animateContentSize(TweenSpec(durationMillis = 400, easing = EaseInOutSine))
                    .fillMaxWidth(if (isDatePickerShowed) 1f else 0.94f)
                    .wrapContentHeight(Alignment.Bottom),
                content = {
                    Box(
                        modifier = Modifier
                            .animateContentSize(TweenSpec(durationMillis = 150, easing = { 1f }))
                            .background(
                                if (isDatePickerShowed) dateDialogBackgroundColor else timeDialogBackgroundColor
                            )
                            .border(
                                BorderStroke(
                                    width = FIRST_BORDER_STROKE,
                                    color = myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA)
                                ),
                                shape = TASK_ITEM_ROUNDED_CORNERS
                            )
                            .border(
                                BorderStroke(
                                    width = SECOND_BORDER_STROKE,
                                    color = myBackgroundColor
                                ),
                                shape = TASK_ITEM_ROUNDED_CORNERS
                            ),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = if (!isDatePickerShowed) 24.dp else 0.dp,
                                        vertical = if (!isDatePickerShowed) 12.dp else 0.dp
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                                content = {

                                    when {
                                        isDatePickerShowed -> {
                                            DatePicker(
                                                modifier = Modifier
                                                    .wrapContentSize(align = Alignment.BottomStart),
                                                state = dateState,
                                                colors = DatePickerDefaults.myDatePickerColors,
                                                title = { Text(text = "Pick a date!") },
                                                headline = { Text(text = "Pick a date!") },
                                                showModeToggle = true,
                                                dateValidator = { dateInMillis ->
                                                    dateInMillis >= currentCalendar
                                                }
                                            )
                                        }

                                        else -> {
                                            TimePicker(
                                                state = timeState,
                                                colors = TimePickerDefaults.myTimePickerColors,
                                                layoutType =
                                                if (layoutConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                                    TimePickerLayoutType.Horizontal
                                                } else {
                                                    TimePickerLayoutType.Vertical
                                                }
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                                        content = {
                                            OutlinedButton(
                                                modifier = Modifier
                                                    .clip(shape = TASK_ITEM_ROUNDED_CORNERS)
                                                    .width(DIALOG_BUTTON_WIDTH)
                                                    .border(
                                                        BorderStroke(
                                                            FIRST_BORDER_STROKE,
                                                            myContentColor.copy(
                                                                alpha = HIGH_BORDER_STROKE_ALPHA
                                                            )
                                                        ),
                                                        shape = TASK_ITEM_ROUNDED_CORNERS
                                                    )
                                                    .border(
                                                        BorderStroke(
                                                            DIALOG_BUTTON_SECOND_BORDER_STROKE,
                                                            timeDialogBackgroundColor
                                                        ),
                                                        shape = TASK_ITEM_ROUNDED_CORNERS
                                                    ),
                                                colors = ButtonDefaults.buttonColors(
                                                    Color.Transparent
                                                ),
                                                onClick = {
                                                    closeDialog()
                                                }) {
                                                Text(text = stringResource(id = R.string.Cancel_No))
                                            }

                                            OutlinedButton(
                                                modifier = Modifier
                                                    .clip(shape = TASK_ITEM_ROUNDED_CORNERS)
                                                    .width(DIALOG_BUTTON_WIDTH)
                                                    .border(
                                                        BorderStroke(
                                                            FIRST_BORDER_STROKE,
                                                            myContentColor.copy(
                                                                alpha = HIGH_BORDER_STROKE_ALPHA
                                                            )
                                                        ),
                                                        shape = TASK_ITEM_ROUNDED_CORNERS
                                                    )
                                                    .border(
                                                        BorderStroke(
                                                            DIALOG_BUTTON_SECOND_BORDER_STROKE,
                                                            timeDialogBackgroundColor
                                                        ),
                                                        shape = TASK_ITEM_ROUNDED_CORNERS
                                                    ),
                                                colors = ButtonDefaults.buttonColors(
                                                    Color.Transparent
                                                ),
                                                onClick = {
                                                    when {
                                                        isDatePickerShowed -> {
                                                            val selectedDateMillis =
                                                                dateState.selectedDateMillis
                                                            if (selectedDateMillis != null && selectedDateMillis >= currentCalendar) {
                                                                onSetDateConfirm()
                                                            } else {
                                                                showToastPickADate()
                                                            }
                                                        }

                                                        else -> {
                                                            val selectedTime = LocalTime.of(
                                                                timeState.hour,
                                                                timeState.minute
                                                            )
                                                            if (selectedTime.isAfter(LocalTime.now())) {
                                                                onSetTimeConfirm()
                                                            } else {
                                                                showToastInvalidDate()
                                                            }
                                                        }
                                                    }
                                                }) {
                                                Text(text = stringResource(id = R.string.Confirm_Yes))  // TODO { change buttons }
                                            }
                                        }
                                    )

                                }

                            )
                        }
                    )
                }
            )
        }
    )
}