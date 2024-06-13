package com.alexandros.p.gialamas.duetodo.ui.components.drawer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import kotlinx.coroutines.CoroutineScope

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable () -> Unit,
    onThemeSelected: (String) -> Unit,
    onSignInToGoogle: () -> Unit,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    val transition = updateTransition(targetState = drawerState.targetValue, label = "")
    val alpha by transition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) } // Customize easing
    ) { targetState ->
        if (targetState == DrawerValue.Open) 0.95f else 0f
    }

    val alphaSize by transition.animateFloat(
        label = "alphaSize",
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) } // Customize easing
    ) { targetState ->
        if (targetState == DrawerValue.Open) 0.90f else 0f
    }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LazyColumn(
                modifier = modifier
                    .clip(RoundedCornerShape(
                        bottomEnd = 16.dp,
                        topEnd = 16.dp))
                    .background(myBackgroundColor.copy( alpha = alpha) ,
                        shape = RoundedCornerShape(
                        bottomEnd = 16.dp,
                        topEnd = 16.dp))
                    .fillMaxWidth(alphaSize)
                    .fillMaxHeight()
                    .padding(32.dp),
                content = {

                    item {
                        AccountProfileItem(modifier = modifier)
                    }
                    item {
                        Text(
                            "Theme",
                            style = MaterialTheme.typography.headlineSmall,
                            color = myTextColor
                        )
                        Divider(
                            modifier = modifier
                                .padding(vertical = 8.dp),
                            color = myContentColor
                        )
                    }
                    items(3) {
                        ThemeItem(theme = "Theme", myTextColor = myTextColor)
                    }

                    item {
                        Spacer(modifier = modifier.height(16.dp))
                        Text(
                            "Import/Export",
                            style = MaterialTheme.typography.headlineSmall,
                            color = myTextColor
                        )
                        Divider(modifier = modifier.padding(vertical = 8.dp))
                    }

                }
            )
        },
        content = { content() }
    )
}

@Composable
private fun AccountProfileItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        content = {
            val userImageUrl = ""
            Image(
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_account_profile),
                contentDescription = stringResource(id = R.string.User_Profile_Image_Description),
            )
            Spacer(modifier = modifier.height(24.dp))
        }
    )
}

@Composable
private fun ThemeItem(
    modifier: Modifier = Modifier,
    theme: String,
    myTextColor: Color
) {
    Text(
        text = theme,
        color = myTextColor,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun ExportImportItem(
    modifier: Modifier = Modifier
) {
    Text("Import/Export", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun GoogleSignInItem(
    modifier: Modifier = Modifier,
    onSignInToGoogle: () -> Unit
) {
    OutlinedButton(onClick = onSignInToGoogle) {
        Icon(
            painterResource(id = R.drawable.ic_account_profile),
            contentDescription = "Google Sign-In"
        ) // Replace with actual resource ID
        Spacer(modifier = modifier.width(8.dp))
        Text("Sign in to Google")
    }
}
