package com.alexandros.p.gialamas.duetodo.util

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun BackHandlerInterception(
    backDispatcher : OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed : () -> Unit
){
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed
            }
        }
    }

    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }


}