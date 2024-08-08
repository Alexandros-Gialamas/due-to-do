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
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor

@Composable
fun ActionCheckList(
    onCheckListClicked: () -> Unit,
    isChecklist: Boolean,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
) {
    IconButton(
        onClick = { onCheckListClicked() },
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
        onCheckListClicked = {},
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        isChecklist = false
    )
}