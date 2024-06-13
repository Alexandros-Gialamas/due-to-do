package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.ONE_TAB_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency

@Composable
fun ActionRemindRepeat(
    modifier: Modifier = Modifier,
    popUpPosition: IntOffset,
    onRepeatFrequencySelected: (RepeatFrequency) -> Unit,
    initialRepeatFrequency: RepeatFrequency = RepeatFrequency.NONE,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    var selectedRepeatFrequency by remember { mutableStateOf(initialRepeatFrequency) }

    Surface(
        modifier = modifier,
        color = Color.Transparent,
        contentColor = myContentColor,
        content = {
            Popup(
                alignment = Alignment.BottomCenter,
                offset = popUpPosition,
                content = {
                    Column(
                        modifier = columnModifier(
                            background = myBackgroundColor,
                            borderOne = myContentColor,
                            borderTwo = myBackgroundColor
                        ),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start,
                        content = {

                            Spacer(modifier = modifier.height(ONE_TAB_PADDING))
                            RepeatFrequency.entries.forEach { repeatFrequency ->
                                RepeatTextItem(
                                    text = stringResource(id = repeatFrequency.displayText),
                                    myTextColor = myTextColor,
                                    onClick = {
                                        selectedRepeatFrequency = repeatFrequency
                                        onRepeatFrequencySelected(repeatFrequency)
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


@Composable
private fun RepeatTextItem(
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    text: String,
    myTextColor: Color
){

    Row (
        modifier = modifier
            .fillMaxWidth(0.47f)
            .clickable { onClick() },
        content = {
            Text(
                modifier = modifier
                    .clickable { onClick() }
                    .padding(horizontal = ONE_TAB_PADDING),
                text = text,
                color = myTextColor,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                softWrap = true,
            )
        }
    )

    Spacer(modifier = modifier.height(ONE_TAB_PADDING))
}

@Composable
private fun columnModifier(
    modifier: Modifier = Modifier,
    background: Color,
    borderOne: Color,
    borderTwo: Color
) : Modifier = modifier
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
