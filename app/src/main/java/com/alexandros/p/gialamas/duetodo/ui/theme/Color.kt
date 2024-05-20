package com.alexandros.p.gialamas.duetodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val TaskPriorityColor_Low = Color(0xFF00C980)
val TaskPriorityColor_Medium = Color(0xFFFFC114)
val TaskPriorityColor_High = Color(0xFFFF4646)
val TaskPriorityColor_None = MyTheme.MyGraphite


object MyTheme {
    val MyGreen = Color(0xFF495E57)
    val SecondGreen = Color(0xFF66B17A)
    val MyYellow = Color(0xFFF4CE14)
    val MyPink = Color(0xFFFBDABB)
    val MyCloud = Color(0xFFEDEFEE)
    val MyCharcoal = Color(0xFF333333)
    val MyBrown = Color(0xFF5e5049)
    val YInMnBlue = Color(0xFF26547C)
    val BrightPink = Color(0xFFEF476F)
    val MyCharcoal2 = Color(0xFF353642)
    val MyGraphite = Color(0xFF4b4d5e)
    val NewBlue = Color(0xFF283593)
}


val Color.statusBarColor: Color  // TODO { check Theme }
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple40


val ColorScheme.dropDownMenuColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) colorScheme.onSurface.copy(alpha = 0.38f) else colorScheme.onSurface.copy(
        alpha = 0.38f
    )

// Tasks
val ColorScheme.taskItemBackgroundColor: Color // TODO { check This }
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.YInMnBlue.copy(0.5f) else MyTheme.MyGreen.copy(0.5f)


val ColorScheme.myTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.MyCloud else MyTheme.MyCloud


val ColorScheme.myContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.MyCloud else MyTheme.MyCloud

val ColorScheme.myBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.YInMnBlue else MyTheme.MyGreen


// FAB Theme Colors
val ColorScheme.fabBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.BrightPink else MyTheme.MyYellow

val ColorScheme.fabContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.MyBrown else MyTheme.MyCharcoal


// TextFields Theme Colors
val TextFieldDefaults.myTextFieldColors: TextFieldColors
    @Composable
    get() = colors(
        cursorColor = MaterialTheme.colorScheme.myContentColor,

        selectionColors = TextSelectionColors(
            handleColor = MyTheme.SecondGreen,
            backgroundColor = MyTheme.SecondGreen

        ),

        focusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,

        disabledLabelColor = MaterialTheme.colorScheme.myTextColor,
        focusedLabelColor = MyTheme.SecondGreen,
        unfocusedLabelColor = MaterialTheme.colorScheme.myTextColor,

        focusedTextColor = MaterialTheme.colorScheme.myContentColor,
        disabledTextColor = MaterialTheme.colorScheme.myContentColor,
        unfocusedTextColor = MaterialTheme.colorScheme.myContentColor,

        focusedIndicatorColor = MyTheme.SecondGreen,
        disabledIndicatorColor = MaterialTheme.colorScheme.myBackgroundColor,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.myBackgroundColor,
        focusedLeadingIconColor = MaterialTheme.colorScheme.myBackgroundColor,

        )

@OptIn(ExperimentalMaterial3Api::class)
val TimePickerDefaults.myTimePickerColors: TimePickerColors
    @Composable
    get() = colors(
        clockDialColor = MaterialTheme.colorScheme.myContentColor,
        selectorColor = MyTheme.SecondGreen,
        containerColor = MaterialTheme.colorScheme.myBackgroundColor.copy(alpha = 0.8f),
//        periodSelectorBorderColor = Color.Transparent,
//        clockDialSelectedContentColor = Color.Transparent,
//        clockDialUnselectedContentColor = Color.Transparent,
//        periodSelectorSelectedContainerColor = Color.Transparent,
//        periodSelectorUnselectedContainerColor = Color.Transparent,
//        periodSelectorSelectedContentColor = Color.Transparent,
//        periodSelectorUnselectedContentColor = Color.Transparent,
//        timeSelectorSelectedContainerColor = Color.Transparent,
//        timeSelectorUnselectedContainerColor = Color.Transparent,
//        timeSelectorSelectedContentColor = Color.Transparent,
//        timeSelectorUnselectedContentColor = Color.Transparent,
    )

@OptIn(ExperimentalMaterial3Api::class)
val DatePickerDefaults.myDatePickerColors: DatePickerColors
    @Composable
    get() = colors(
        containerColor = MaterialTheme.colorScheme.myBackgroundColor.copy(alpha = 0.85f),
        titleContentColor = MaterialTheme.colorScheme.myTextColor,
        headlineContentColor = MaterialTheme.colorScheme.myTextColor,
        weekdayContentColor = MaterialTheme.colorScheme.myTextColor,
        subheadContentColor = MaterialTheme.colorScheme.myTextColor,
        yearContentColor = MaterialTheme.colorScheme.myTextColor,
        currentYearContentColor = MaterialTheme.colorScheme.myTextColor,
        selectedYearContentColor = MaterialTheme.colorScheme.myTextColor,
//        selectedYearContainerColor = MaterialTheme.colorScheme.myTextColor,
        dayContentColor = MaterialTheme.colorScheme.myTextColor,
        disabledDayContentColor = MaterialTheme.colorScheme.myTextColor.copy(alpha = 0.5f),
        selectedDayContentColor = MaterialTheme.colorScheme.myContentColor,
//        disabledSelectedDayContentColor = MaterialTheme.colorScheme.myTextColor,
        selectedDayContainerColor = MyTheme.SecondGreen,
//        disabledSelectedDayContainerColor = Color.Transparent,
        todayContentColor = MaterialTheme.colorScheme.myTextColor,
        todayDateBorderColor = MyTheme.SecondGreen,
        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.myTextColor,
        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.myTextColor,
    )


@Composable
fun Brush.Companion.myBackgroundBrush(radius: Float): Brush {
    return if (isSystemInDarkTheme()) {
        Brush.radialGradient(

            colors = listOf(
                Color(0x85FFFFFF).copy(alpha = 0.7f),
                Color(0xFD000000),
                Color(0x12010A01),

                MaterialTheme.colorScheme.onSurface,

                ),
            Offset.Unspecified,
            radius = radius
        )
    } else {
        Brush.radialGradient(

            colors = listOf(
                Color(0xFF43614B).copy(alpha = 0.7f),
                Color(0xFF495E57),
                Color(0xFFFFFFFF),

                MaterialTheme.colorScheme.onSurface,

                ),
            Offset.Unspecified,
            radius = radius
        )
    }

}

