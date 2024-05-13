package com.alexandros.p.gialamas.duetodo.ui.components.fabbutton


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.fabBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.fabContentColor

@Composable
fun FabButton(
    onFabClicked : (taskId : Int) -> Unit,
    myFabBackgroundColor : Color,
    myFabContentColor : Color,
    myFabIconColor : Color
){
    FloatingActionButton(
        modifier = Modifier
            .offset(x = (-18).dp, y = (50).dp),  // TODO { play with this value }
        onClick = { onFabClicked(-1) },
        contentColor = myFabContentColor,
        containerColor = myFabBackgroundColor,
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
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

