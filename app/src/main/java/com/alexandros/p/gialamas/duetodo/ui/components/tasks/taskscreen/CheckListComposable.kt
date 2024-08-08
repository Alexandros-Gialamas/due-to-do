package com.alexandros.p.gialamas.duetodo.ui.components.tasks.taskscreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.ReorderableLazyListState
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun Checklist(
    modifier: Modifier = Modifier,
    checkListItemsData: List<CheckListItem>,
    updateCheckListItems: (List<CheckListItem>) -> Unit
) {

    val checkListItems = remember { mutableStateOf(checkListItemsData) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        checkListItems.value = checkListItems.value.toMutableList().also {
            it.add(to.index, it.removeAt(from.index))
        }
        updateCheckListItems(checkListItems.value)
    },)

    LaunchedEffect(checkListItemsData) {
        checkListItems.value = checkListItemsData
    }

    Column {
        LazyColumn(
            modifier = modifier
                .reorderable(state)
                .weight(1f),
            state = state.listState
        ) {
            itemsIndexed(checkListItems.value) { index, checkListItem ->
                ChecklistItemRow(
                    state = state,
                    index = checkListItems.value.indexOf(checkListItem),
                    checkListItem = checkListItem,
                    removeEntry = {
                        val newList = checkListItems.value.toMutableList()
                        newList.removeAt(index)
                        checkListItems.value = newList
                        updateCheckListItems(newList)
                    },
                    onItemChanged = { taskDescription, isCompleted ->
                        val newList = checkListItems.value.toMutableList()
                        newList[index] = CheckListItem(taskDescription, isCompleted)
                        checkListItems.value = newList
                        updateCheckListItems(newList)
                    },
                )
            }
        }

        Button(
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally),
            onClick = {
                val newList = checkListItems.value + CheckListItem("", false)
                checkListItems.value = newList
                updateCheckListItems(newList)
            }
        ) {
            Text(text = "Add Item")
        }
    }
}


@Composable
fun ChecklistItemRow(
    modifier: Modifier = Modifier,
    checkListItem: CheckListItem,
    state: ReorderableLazyListState,
    onItemChanged: (String, Boolean) -> Unit,
    removeEntry: () -> Unit,
    index: Int,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    val isDragging = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            ReorderableItem(
                state = state,
                key = index
            ) { dragging ->
                isDragging.value = dragging
                val elevation = animateDpAsState(
                    targetValue = if (dragging) 24.dp else 0.dp,
                    label = "Animate_checklistItem"
                )

                if (dragging){
                    focusRequester.requestFocus()
                }else{
                    focusManager.clearFocus()
                }

                Row(
                    modifier = modifier
                        .shadow(elevation.value),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        IconButton(
                            modifier = modifier
                                .detectReorder(state),
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_drag_indicator),
                                contentDescription = stringResource(id = R.string.CheckList_Drag_Icon_Description)
                            )
                        }


//            Checkbox(
//                checked = checkListItem.isCompleted,
//                onCheckedChange = { onItemChanged(checkListItem.taskDescription, it) },
//                colors = CheckboxDefaults.myCheckBoxColors
//            )


                        TextField(
                            modifier = modifier
                                .weight(1f)
                                .background(color = Color.Transparent),
                            value = checkListItem.taskDescription,
                            onValueChange = { onItemChanged(it, checkListItem.isCompleted) },
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textDecoration = if (checkListItem.isCompleted) TextDecoration.LineThrough else null,
                                color = if (isDragging.value) myActivatedColor else myTextColor
                            ),
                            maxLines = 2,
                            colors = TextFieldDefaults.myTextFieldColors
                        )


                        IconButton(
                            onClick = { removeEntry() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(id = R.string.CheckList_Delete_Item_Icon_Description)
                            )
                        }
                    }
                )
            }
        }
    )
}

