package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun ActionClear(
    textState: String,
    onClearClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
){

    val iconVisible = textState.isNotBlank()

    IconButton(onClick = { onClearClicked() }) {
        if (iconVisible) {
            Icon(
                modifier = Modifier
                    .padding(end = LARGE_PADDING),
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(
                    id = R.string.SearchBar_Action_Clear_Description
                ),
                tint = myContentColor
            )
        }
    }
}

@Composable
@Preview
private fun ActionMenuPreview() {
    ActionClear(
        onClearClicked = {},
        textState = "",
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}