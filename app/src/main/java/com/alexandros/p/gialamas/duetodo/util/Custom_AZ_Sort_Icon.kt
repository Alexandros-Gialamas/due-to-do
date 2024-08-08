package com.alexandros.p.gialamas.duetodo.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val AtoZ = "A|Z"
const val ZtoA = "Z|A"
const val A = "A"
const val Z = "Z"
const val verticalLine = "|"

@Composable
fun CustomSortIconAZ(
    modifier: Modifier = Modifier,
    iconSize : Dp = 24.dp,
    isAscending : Boolean,
    hasArrowDown : Boolean,
    cornerRadius: Dp = 3.dp,
    strokeWidthPercent: Float = 0.05f,
    myContentColor : Color,
){
    Box(
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier.padding(vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {

                            val isDescending = if (isAscending) Z else A

                            CustomSortIconTextItem(
                                text = if (isAscending) A else Z,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                myContextColor = myContentColor
                            )
                            CustomSortIconTextItem(
                                text = verticalLine,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                myContextColor = myContentColor
                            )
                            CustomSortIconTextItem(
                                text = isDescending,
                                fontSize = 12.sp,
                                textAlign = TextAlign.End,
                                myContextColor = myContentColor
                            )

//                        CustomDateIconTextItem(
//                            text = verticalLine,
//                            fontSize = 10.sp,
//                            textAlign = TextAlign.End,
//                            myContextColor = myContentColor
//                        )
                    }
                    )
                }
            )

//            if (hasArrowUp){
//                Canvas(
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(bottom = 36.dp) // Adjust padding as needed
//                        .size(iconSize / 2),       // Make the arrow smaller than the icon
//                ) {
//                    val arrowColor = myContentColor
//                    val arrowPath = Path().apply {
//                        moveTo(size.width / 2, 0f)
//                        lineTo(0f, size.height)
//                        lineTo(size.width, size.height)
//                        close()
//                    }
//                    drawPath(
//                        path = arrowPath,
//                        color = arrowColor
//                    )
//                }
//            }


            if (hasArrowDown) {
                Canvas(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = 14.dp) // Adjust padding as needed
                        .size(iconSize / 3),       // Make the arrow smaller than the icon
                ) {
                    val arrowPath = Path().apply {
                        moveTo(size.width / 2, size.height)
                        lineTo(0f, 0f)
                        lineTo(size.width , 0f)
                        close()
                    }
                    drawPath(
                        path = arrowPath,
                        color = myContentColor
                    )
                }
            }

//            Canvas(
//                modifier = modifier
//                    .size(iconSize),
//                onDraw = {
//                    val strokeWidth = size.width * strokeWidthPercent
//                    val rectWidth = size.width - strokeWidth
//                    val rectHeight = (size.height) - strokeWidth
//                    val rectLeft = (size.width - rectWidth) / 2
//                    val rectTop = (size.height - rectHeight) / 2
//                    drawRoundRect(
//                        color = myContentColor,
//                        topLeft = Offset(rectLeft, rectTop),
//                        size = Size(rectWidth, rectHeight),
//                        cornerRadius = CornerRadius(cornerRadius.toPx()),
//                        style = Stroke(strokeWidth)
//                    )
//                }
//            )
        }
    )
}
@Composable
private fun CustomSortIconTextItem(
    modifier: Modifier = Modifier,
    text : String,
    fontWeight: FontWeight = FontWeight.ExtraBold,
    fontSize : TextUnit,
    textAlign : TextAlign,
    myContextColor : Color,
){
    Text(
        modifier = modifier
            .padding(bottom = 8.dp),
        text = text,
        color = myContextColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = 1.sp,
        textAlign = textAlign,

    )
}