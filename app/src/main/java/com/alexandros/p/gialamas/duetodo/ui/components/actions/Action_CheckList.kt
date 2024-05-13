package com.alexandros.p.gialamas.duetodo.ui.components.actions

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun ActionCheckList(
    onNewCheckListClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    IconButton(
        onClick = { onNewCheckListClicked() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check_list),
            contentDescription = stringResource(id = R.string.BottomBar_Action_CheckList_Description),
            tint = myContentColor
        )
    }
}

@Composable
@Preview
private fun ActionCheckListPreview() {
    ActionCheckList(
        onNewCheckListClicked = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}