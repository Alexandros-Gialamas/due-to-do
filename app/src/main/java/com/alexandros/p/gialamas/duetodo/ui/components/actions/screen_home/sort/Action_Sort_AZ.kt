package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.sort

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun ActionSortAtoZ(

){
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = null,
        modifier = Modifier.scale(scaleX = -1f, scaleY = 1f)
    )
}
