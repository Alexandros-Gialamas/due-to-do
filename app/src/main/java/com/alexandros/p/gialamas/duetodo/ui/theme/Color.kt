package com.alexandros.p.gialamas.duetodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)


val TaskPriorityColor_Low = Color(0xFF00C980)
val TaskPriorityColor_Medium = Color(0xFFFFC114)
val TaskPriorityColor_High = Color(0xFFFF4646)
val TaskPriorityColor_None = MyTheme.MyGraphite

object MyTheme {
    val MyGreen = Color(0xFF495E57)
    val MyYellow = Color(0xFFF4CE14)
    val MyPink = Color(0xFFFBDABB)
    val MyCloud = Color(0xFFEDEFEE)
    val MyCharcoal = Color(0xFF333333)
    val MyBrown = Color(0xFF5e5049)
    val YInMnBlue = Color(0xFF26547C)
    val BrightPink = Color(0xFFEF476F)
    val MyCharcoal2 = Color(0xFF353642)
    val MyGraphite = Color(0xFF4b4d5e)

}

val Color.statusBarColor: Color  // TODO { check Theme }
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple40


// Tasks
val ColorScheme.taskItemBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.YInMnBlue.copy(0.5f) else MyTheme.MyGreen.copy(0.5f)
val ColorScheme.taskItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.MyCloud else MyTheme.MyCloud


// App Top Bar Theme Colors
val ColorScheme.topAppBarrContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.MyCloud else MyTheme.MyCloud

val ColorScheme.topAppBarrBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.YInMnBlue else MyTheme.MyGreen




// FAB Theme Colors
val ColorScheme.fabBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.BrightPink else MyTheme.MyYellow

val ColorScheme.fabContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MyTheme.MyBrown else MyTheme.MyCharcoal





