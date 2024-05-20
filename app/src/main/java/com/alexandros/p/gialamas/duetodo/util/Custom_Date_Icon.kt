package com.alexandros.p.gialamas.duetodo.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val DAY_TEXT = "MONDAY"
const val DATE_TEXT = "12"
const val MONTH_TEXT = "NOVEMBER"

@Composable
fun CustomDateIcon(
    modifier: Modifier = Modifier,
    iconSize : Dp,
    cornerRadius: Dp = 3.dp,
    strokeWidthPercent: Float = 0.05f,
    myContentColor : Color,
){
    Box(
        contentAlignment = Alignment.Center,
        content = {
            Column (
                modifier.padding(vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    CustomDateIconTextItem(text = DAY_TEXT, fontSize = 3.sp, myContextColor = myContentColor)
                    CustomDateIconTextItem(text = DATE_TEXT, fontSize = 7.sp, myContextColor = myContentColor)
                    CustomDateIconTextItem(text = MONTH_TEXT, fontSize = 2.sp, myContextColor = myContentColor)
                }
            )
            Canvas(
                modifier = modifier
                    .size(iconSize),
                onDraw = {
                    val strokeWidth = size.width * strokeWidthPercent
                    val rectWidth = size.width - strokeWidth
                    val rectHeight = (size.height) - strokeWidth
                    val rectLeft = (size.width - rectWidth) / 2
                    val rectTop = (size.height - rectHeight) / 2
                    drawRoundRect(
                        color = myContentColor,
                        topLeft = Offset(rectLeft, rectTop),
                        size = Size(rectWidth, rectHeight),
                        cornerRadius = CornerRadius(cornerRadius.toPx()),
                        style = Stroke(strokeWidth)
                    )
                }
            )
        }
    )
}
@Composable
private fun CustomDateIconTextItem(
    modifier: Modifier = Modifier,
    text : String,
    fontWeight: FontWeight = FontWeight.ExtraBold,
    fontSize : TextUnit,
    myContextColor : Color,
){
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        text = text,
        color = myContextColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = 1.sp,
        textAlign = TextAlign.Center
    )
}