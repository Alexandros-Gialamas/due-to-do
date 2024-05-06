package com.alexandros.p.gialamas.duetodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.EMPTY_CONTENT_SCREEN_ICON_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun EmptyContent(
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Icon(
                modifier = Modifier
                    .size(EMPTY_CONTENT_SCREEN_ICON_SIZE),
                painter = painterResource(id = R.drawable.ic_sad_face),
                contentDescription = stringResource(R.string.Sad_face_Description),
                tint = myContentColor.copy(alpha = 0.6f)
            )
            Text(
                text = stringResource(id = R.string.No_Tasks_Found),
                color = myTextColor.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold,
                fontSize = typography.headlineLarge.fontSize,
            )
        }
    )
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent(
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor.copy(alpha = 0.6f),
        myTextColor = MaterialTheme.colorScheme.myTextColor.copy(alpha = 0.6f)
    )
}