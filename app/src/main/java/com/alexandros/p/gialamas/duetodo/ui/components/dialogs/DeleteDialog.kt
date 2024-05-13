package com.alexandros.p.gialamas.duetodo.ui.components.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.DIALOG_BUTTON_SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.DIALOG_BUTTON_WIDTH
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HIGH_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    val dialogBackgroundColor = Brush.myBackgroundBrush(radius = 1800f / 0.9f)
    if (openDialog) {

        Dialog(
            onDismissRequest = { closeDialog() },
            content = {
                Surface(
                    color = Color.Transparent,
                    shape = TASK_ITEM_ROUNDED_CORNERS,
                    modifier = Modifier
                        .fillMaxWidth(0.94f)
                        .height(IntrinsicSize.Max),
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(dialogBackgroundColor)
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
                                        .padding(horizontal = 24.dp, vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 4.dp, end = 4.dp, top = 14.dp),
                                            text = title,  // TODO { Delete Task Title }
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            color = myTextColor,
                                            textAlign = TextAlign.Left
                                        )

                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 4.dp, vertical = 12.dp),
                                            text = message,
                                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                            fontWeight = FontWeight.Normal,
                                            color = myTextColor
                                        )

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
                                                                dialogBackgroundColor
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
                                                                dialogBackgroundColor
                                                            ),
                                                            shape = TASK_ITEM_ROUNDED_CORNERS
                                                        ),
                                                    colors = ButtonDefaults.buttonColors(
                                                        Color.Transparent
                                                    ),
                                                    onClick = {
                                                        onYesClicked()
                                                        closeDialog()
                                                    }) {
                                                    Text(text = stringResource(id = R.string.Confirm_Yes))
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
}


@Composable
@Preview
private fun DisplayAlertDialogPreview() {
    DeleteDialog(
        title = "Delete A",
        message = "Are you sure to delete A",
        openDialog = true,
        closeDialog = { },
        onYesClicked = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}