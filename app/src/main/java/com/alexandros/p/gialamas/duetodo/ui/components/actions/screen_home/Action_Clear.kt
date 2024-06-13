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
    modifier: Modifier = Modifier,
    textState: String,
    onClearClicked: () -> Unit,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
){

    val iconVisible = textState.isNotBlank()

    IconButton(onClick = { onClearClicked() }) {
        if (iconVisible) {
            Icon(
                modifier = modifier
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
        myContentColor = MaterialTheme.colorScheme.myContentColor,
    )
}