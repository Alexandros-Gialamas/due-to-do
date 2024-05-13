package com.alexandros.p.gialamas.duetodo.util

enum class CrudAction {

    INSERT,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION

}

fun String?.toAction() : CrudAction {
    return if (this.isNullOrBlank()) CrudAction.NO_ACTION else CrudAction.valueOf(this)
}