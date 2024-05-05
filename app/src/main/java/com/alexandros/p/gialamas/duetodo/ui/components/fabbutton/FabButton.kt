package com.alexandros.p.gialamas.duetodo.ui.components.fabbutton


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.fabBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.fabContentColor

@Composable
fun FabButton(
    navController : NavHostController,
    onFabClicked : (taskId : Int) -> Unit
){
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        contentColor = MaterialTheme.colorScheme.fabContentColor,
        containerColor = MaterialTheme.colorScheme.fabBackgroundColor,
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        content = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_task_button),
                tint = MyTheme.MyGreen,
                modifier = Modifier
            )
        }
    )
}

@Composable
@Preview
private fun FabButtonPreview() {
    FabButton(
        navController = NavHostController(LocalContext.current),
       onFabClicked = {}
    )
}

