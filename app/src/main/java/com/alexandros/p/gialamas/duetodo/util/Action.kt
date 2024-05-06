package com.alexandros.p.gialamas.duetodo.util

enum class Action {

    INSERT,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

fun String?.toAction() : Action {
    return if (this.isNullOrBlank()) Action.NO_ACTION else Action.valueOf(this)
}