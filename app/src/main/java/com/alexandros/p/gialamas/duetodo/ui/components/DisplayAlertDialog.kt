package com.alexandros.p.gialamas.duetodo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    if (openDialog) {

        AlertDialog(
            // TODO { color adjustments }
            onDismissRequest = { closeDialog() },
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },

            confirmButton = {
                Row(
                    modifier = Modifier
                        .padding(start = 22.dp),
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth(0.42f),
                        onClick = {
                            onYesClicked()
                            closeDialog()
                        }) {
                        Text(text = stringResource(id = R.string.Confirm_Yes))
                    }
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier
                        .padding(end = 27.dp),
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth(0.42f),
                        onClick = {
                            closeDialog()
                        }) {
                        Text(text = stringResource(id = R.string.Cancel_No))
                    }
                }
            },
        )


    }
}

@Composable
@Preview
private fun DisplayAlertDialogPreview() {
    DisplayAlertDialog(
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