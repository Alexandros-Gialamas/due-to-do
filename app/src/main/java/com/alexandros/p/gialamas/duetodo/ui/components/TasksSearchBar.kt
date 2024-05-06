package com.alexandros.p.gialamas.duetodo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.TOP_APP_BAR_HEIGHT
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor

@Composable
fun TasksSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onClearClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    textState : String
) {

    val iconVisible = textState.isNotBlank()

    Surface(
        modifier = Modifier
            .clip(TASK_ITEM_ROUNDED_CORNERS)
            .height(TOP_APP_BAR_HEIGHT),
        color = Color.Transparent,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center,
                content = {
                    Card(
                        modifier = Modifier,
                        shape = TASK_ITEM_ROUNDED_CORNERS,
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.elevatedCardElevation(),
                        content = {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(0.94f)
                                    .border(
                                        BorderStroke(2.dp, Color.White.copy(alpha = 0.7f)),
                                        shape = TASK_ITEM_ROUNDED_CORNERS
                                    )
                                    .border(
                                        BorderStroke(4.dp, color = MyTheme.MyGreen),
                                        shape = TASK_ITEM_ROUNDED_CORNERS
                                    ),
                                value = text,
                                onValueChange = { onTextChange(it) },
                                placeholder = {
                                    Text(
                                        modifier = Modifier
                                            .alpha(0.8f),
                                        text = stringResource(id = R.string.Search_Bar_Placeholder),
                                        color = MyTheme.MyCloud
                                    )
                                },
                                textStyle = TextStyle(
                                    color = MaterialTheme.colorScheme.topAppBarrContentColor,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                ),
                                singleLine = true,
                                leadingIcon = {
                                    IconButton(
                                        modifier = Modifier
                                            .alpha(0.5f),
                                        onClick = { /*TODO*/ }) {
                                        Icon(
                                            modifier = Modifier
                                                .padding(start = LARGE_PADDING),
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = stringResource(id = R.string.Search_Bar_Search_Description),
                                            tint = MaterialTheme.colorScheme.topAppBarrContentColor
                                        )
                                    }
                                },
                                trailingIcon = {
                                    IconButton(onClick = { onClearClicked() }) {
                                        if (iconVisible){
                                            Icon(
                                                modifier = Modifier
                                                    .padding(end = LARGE_PADDING),
                                                imageVector = Icons.Filled.Close,
                                                contentDescription = stringResource(
                                                    id = R.string.Search_Bar_Clear_Description
                                                ),
                                                tint = MaterialTheme.colorScheme.topAppBarrContentColor
                                            )
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = { onSearchClicked(text) }
                                ),
                                colors = TextFieldDefaults.myTextFieldColors)
                        }
                    )
                }
            )
        }
    )
}

@Composable
@Preview
private fun TasksSearchBarPreview() {
    TasksSearchBar(
        text = "Search",
        onTextChange = {},
        onClearClicked = {},
        onSearchClicked = {},
        textState = ""
    )
}