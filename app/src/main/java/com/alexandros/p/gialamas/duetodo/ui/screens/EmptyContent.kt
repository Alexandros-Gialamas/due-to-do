package com.alexandros.p.gialamas.duetodo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme

@Composable
fun EmptyContent(){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
                    Icon(
                        modifier = Modifier
                        .size(120.dp),
                        painter = painterResource(id = R.drawable.ic_sad_face),
                        contentDescription = stringResource(R.string.Sad_face_Description),
                        tint = MyTheme.MyCloud.copy(alpha = 0.6f)
                    )
            Text(
                text = stringResource(id = R.string.No_Tasks_Found),
                color = MyTheme.MyCloud.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold,
                fontSize = typography.headlineLarge.fontSize,
                )
                }
    )
}

@Composable
@Preview
fun EmptyContentPreview(){
    EmptyContent()
}