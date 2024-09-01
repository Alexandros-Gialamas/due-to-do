package com.alexandros.p.gialamas.duetodo.ui.components.tasks.taskscreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myCheckBoxColors
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextFieldColors
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.ReorderableLazyListState
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.util.Objects
import kotlin.math.absoluteValue


@Composable
fun DisplayChecklist(
    modifier: Modifier = Modifier,
    checkListItemsData: List<CheckListItem>,
    updateCheckListItems: (List<CheckListItem>) -> Unit,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor,
) {

    val checkListItems = remember { mutableStateOf(checkListItemsData) }
    val checkedItemsSize by remember(checkListItems.value) {
        derivedStateOf { checkListItems.value.count { it.isCompleted } }
    }
    val uncheckedItems by remember(checkListItems.value) {
        derivedStateOf { checkListItems.value.filter { !it.isCompleted }.toList() }
    }

    val checkedItems by remember(checkListItems.value) {
        derivedStateOf { checkListItems.value.filter { it.isCompleted }.toList() }
    }

    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            checkListItems.value = checkListItems.value.toMutableList().also { list ->
                val item = if (from.index in 0 until list.size) list.removeAt(from.index) else null
                item?.let {
                    val safeToIndex = to.index.coerceIn(0, list.size)
                    list.add(safeToIndex, it)
                }
            }
            updateCheckListItems(checkListItems.value)
        }
    )

    val isDragging = remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(true) }

    LaunchedEffect(checkListItemsData) {
        checkListItems.value = checkListItemsData
    }


    Column(
        modifier = modifier
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = modifier
                .reorderable(state),
            state = state.listState
        ) {

            itemsIndexed(uncheckedItems) { index, checkListItem ->
                UncheckListItemRow(
                    state = state,
                    index = index,
                    checkListItem = checkListItem,
                    isDragging = isDragging.value,
                    removeEntry = {
                        val newList = checkListItems.value.toMutableList()
                        newList.removeAt(index)
                        checkListItems.value = newList
                        updateCheckListItems(newList)
                    },
                    onItemChanged = { taskDescription, isCompleted ->
                        val newList = checkListItems.value.toMutableList()
                        val updatedItem = checkListItem.copy(
                            listItemDescription = taskDescription,
                            isCompleted = isCompleted
                        )
                        val indexOfCheckListItem = newList.indexOf(checkListItem)
                        if (indexOfCheckListItem != -1) {
                            newList[indexOfCheckListItem] = updatedItem
                        }
                        checkListItems.value = newList
                        updateCheckListItems(newList)
                    },
                )
            }


            item { Spacer(modifier = modifier.height(EXTRA_LARGE_PADDING)) }

            item {
                IconButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = {
                        val newList = checkListItems.value + CheckListItem("", false)
                        checkListItems.value = newList
                        updateCheckListItems(newList)
                    }
                ) {
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.CheckList_Add_Item_Icon_Description)
                        )
                        Text(
                            modifier = modifier
                                .padding(horizontal = 4.dp),
                            text = stringResource(id = R.string.CheckList_Add_Item_Icon_Button),
                            color = myTextColor
                        )
                    }

                }
            }

            if (checkedItemsSize > 0) {

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        content = {
                            IconButton(
                                onClick = { isExpanded = !isExpanded }) {
                                Icon(
                                    modifier = modifier.padding(4.dp),
                                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = stringResource(id = R.string.CheckList_Expand_Checked_Items_Description)
                                )
                            }

                            Text(
                                modifier = modifier
                                    .padding(4.dp)
                                    .clickable { isExpanded = !isExpanded },
                                text = "$checkedItemsSize ${stringResource(id = R.string.CheckList_Checked_Items_Count)}",
                                style = typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                color = myTextColor
                            )
                        }
                    )
                }
            }

            if (isExpanded) {
                items(checkedItems) { checkListItem ->
                    CheckListItemRow(
                        checkListItem = checkListItem,
                        removeEntry = {
                            val newList = checkListItems.value.toMutableList()
                            newList.remove(checkListItem)
                            checkListItems.value = newList
                            updateCheckListItems(newList)
                        },
                        onItemChanged = { taskDescription, isCompleted ->
                            val newList = checkListItems.value.toMutableList()
                            val updatedItem = checkListItem.copy(
                                listItemDescription = taskDescription,
                                isCompleted = isCompleted
                            )
                            val indexOfCheckListItem = newList.indexOf(checkListItem)
                            if (indexOfCheckListItem != -1) {
                                newList[indexOfCheckListItem] = updatedItem
                            }
                            checkListItems.value = newList
                            updateCheckListItems(newList)
                        }
                    )
                }
            }
        }


    }
}


@Composable
private fun UncheckListItemRow(
    modifier: Modifier = Modifier,
    checkListItem: CheckListItem,
    state: ReorderableLazyListState,
    onItemChanged: (String, Boolean) -> Unit,
    removeEntry: () -> Unit,
    index: Int,
    isDragging: Boolean,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    val onDragging = remember { mutableStateOf(isDragging) }
//    val focusRequester = remember { FocusRequester() }
//    val focusManager = LocalFocusManager.current


    Row(
        modifier = modifier
            .fillMaxWidth(),
//            .focusRequester(focusRequester),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            ReorderableItem(
                state = state,
                key = index
            ) { dragging ->
                onDragging.value = dragging
                val elevation = animateDpAsState(
                    targetValue = if (dragging) 24.dp else 0.dp,
                    label = "Animate_checklistItem"
                )

//                if (dragging) {
//                    focusRequester.requestFocus()
//                } else {
//                    focusManager.clearFocus(force = false)
//                }

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

                        Checkbox(
                            checked = checkListItem.isCompleted,
                            onCheckedChange = {
                                onItemChanged(
                                    checkListItem.listItemDescription,
                                    it
                                )
                            },
                            colors = CheckboxDefaults.myCheckBoxColors
                        )


                        TextField(
                            modifier = modifier
                                .weight(2f)
                                .background(color = Color.Transparent),
                            value = checkListItem.listItemDescription,
                            onValueChange = {
                                onItemChanged(
                                    it,
                                    checkListItem.isCompleted
                                )
                            },
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textDecoration = if (checkListItem.isCompleted) TextDecoration.LineThrough else null,
                                color = myActivatedColor.takeIf { onDragging.value } ?: myTextColor
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


@Composable
private fun CheckListItemRow(
    modifier: Modifier = Modifier,
    checkListItem: CheckListItem,
    onItemChanged: (String, Boolean) -> Unit,
    removeEntry: () -> Unit,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                content = {

                    Checkbox(
                        checked = checkListItem.isCompleted,
                        onCheckedChange = {
                            onItemChanged(
                                checkListItem.listItemDescription,
                                it
                            )
                        },
                        colors = CheckboxDefaults.myCheckBoxColors
                    )

                    TextField(
                        modifier = modifier
                            .weight(2f)
                            .background(color = Color.Transparent),
                        value = checkListItem.listItemDescription,
                        onValueChange = { onItemChanged(it, checkListItem.isCompleted) },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (checkListItem.isCompleted) TextDecoration.LineThrough else null,
                            color = myContentColor.copy(alpha = 0.7f)
                        ),
                        maxLines = 2,
                        colors = TextFieldDefaults.myTextFieldColors
                    )

                    IconButton(
                        onClick = { removeEntry() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.CheckList_Delete_Item_Icon_Description),
                            tint = myContentColor.copy(alpha = 0.7f)
                        )
                    }
                }
            )
        }
    )
}



