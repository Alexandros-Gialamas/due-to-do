package com.alexandros.p.gialamas.duetodo.util

enum class DatabaseAction {

    INSERT,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION

}

fun String?.toAction() : DatabaseAction {
    return if (this.isNullOrBlank()) DatabaseAction.NO_ACTION else DatabaseAction.valueOf(this)
}