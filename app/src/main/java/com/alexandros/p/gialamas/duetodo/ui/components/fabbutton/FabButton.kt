package com.alexandros.p.gialamas.duetodo.ui.components.fabbutton


import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.fabBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.fabContentColor

@Composable
fun FabButton(
    modifier: Modifier = Modifier,
    onFabClicked : (taskId : Int) -> Unit,
    myFabBackgroundColor : Color = MaterialTheme.colorScheme.fabBackgroundColor,
    myFabContentColor : Color = MaterialTheme.colorScheme.fabContentColor,
    myFabIconColor : Color = MaterialTheme.colorScheme.fabContentColor
){
    FloatingActionButton(
        modifier = modifier
            .offset(
                x = (-22).dp,
                y = (75).dp
            ),  // TODO { play with this value }
        onClick = { onFabClicked(-1) },
        contentColor = myFabContentColor,
        containerColor = myFabBackgroundColor,
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        content = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_task_button),
                tint = myFabIconColor,
                modifier = Modifier
            )
        }
    )
}

@Composable
@Preview
private fun FabButtonPreview() {
    FabButton(
       onFabClicked = {},
        myFabBackgroundColor = MaterialTheme.colorScheme.fabBackgroundColor,
        myFabContentColor = MaterialTheme.colorScheme.fabContentColor,
        myFabIconColor = MyTheme.MyGreen

    )
}

