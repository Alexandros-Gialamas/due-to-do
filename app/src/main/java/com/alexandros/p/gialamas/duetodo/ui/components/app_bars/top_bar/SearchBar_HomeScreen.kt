package com.alexandros.p.gialamas.duetodo.ui.components.app_bars.top_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.components.dialogs.DeleteDialog
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionClear
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionMainMenu
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.ActionVerticalMenu
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HIGH_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SCAFFOLD_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.SEARCH_BAR_ICON_ALPHA_VALUE
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.util.SearchBarState

@Composable
fun SearchBarHomeScreen(
    modifier: Modifier = Modifier,
    searchPhrase: String,
    onSearchPhraseChanged: (phrase : String, searchBarState : SearchBarState) -> Unit,
    onMenuClicked: () -> Unit,
    onClearClicked: (searchBarState : SearchBarState) -> Unit,
    onSearchClicked: (String) -> Unit,
    onDeleteAllTasksClicked: () -> Unit,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    var openDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    DeleteDialog(
        title = stringResource(id = R.string.Delete_All_Task),
        message = stringResource(id = R.string.Delete_All_Task_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllTasksClicked() }
    )

    Surface(
        modifier = modifier
            .clip(SCAFFOLD_ROUNDED_CORNERS)
            .height(IntrinsicSize.Max)
            .padding(top = LARGE_PADDING),
        color = Color.Transparent,
        content = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center,
                content = {
                    Card(
                        modifier = modifier,
                        shape = SCAFFOLD_ROUNDED_CORNERS,
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.elevatedCardElevation(),
                        content = {
                            TextField(
                                modifier = modifier
                                    .fillMaxWidth(0.94f) // TODO { Hardcoded fraction }
                                    .background(myBackgroundColor)
                                    .border(
                                        BorderStroke(
                                            width = FIRST_BORDER_STROKE,
                                            color = myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA)
                                        ),
                                        shape = SCAFFOLD_ROUNDED_CORNERS
                                    )
                                    .border(
                                        BorderStroke(
                                            width = SECOND_BORDER_STROKE,
                                            color = myContentColor.copy(alpha = HIGH_BORDER_STROKE_ALPHA)
                                        ),
                                        shape = SCAFFOLD_ROUNDED_CORNERS
                                    ),
                                value = searchPhrase,
                                onValueChange = { phrase ->
                                    if (phrase.isNotBlank())
                                    onSearchPhraseChanged(phrase, SearchBarState.TYPING)
                                    else
                                        onSearchPhraseChanged(phrase, SearchBarState.CLEARED) },
                                placeholder = {
                                    Text(
                                        modifier = modifier
                                            .alpha(SEARCH_BAR_ICON_ALPHA_VALUE),
                                        text = stringResource(id = R.string.SearchBar_Placeholder),
                                        color = myTextColor
                                    )
                                },
                                textStyle = TextStyle(
                                    color = myTextColor,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                ),
                                singleLine = true,
                                leadingIcon = {

                                    ActionMainMenu(
                                        onMenuClicked = { onMenuClicked() }
                                    )
                                },
                                trailingIcon = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {

                                            ActionClear(
                                                textState = searchPhrase,
                                                onClearClicked = {
                                                    onClearClicked(SearchBarState.CLEARED)
                                                }
                                            )

                                            ActionVerticalMenu(
                                                onDeleteAllTasksClicked = { openDialog = true }
                                            )
                                        }
                                    )

                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        onSearchClicked(searchPhrase)
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.myTextFieldColors)
                        }
                    )
                }
            )
        }
    )
}

//@Composable
//@Preview
//private fun SearchBarHomeScreenPreview() {
//    SearchBarHomeScreen(
//        searchPhrase = "Search",
//        onSearchPhraseChanged ={ search, searchBarState -> "search",},
//        onClearClicked = {},
//        onSearchClicked = {},
//        onMenuClicked = {},
//        onDeleteAllTasksClicked = {},
//    )
//}