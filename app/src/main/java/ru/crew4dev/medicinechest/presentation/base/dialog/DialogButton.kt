package ru.crew4dev.medicinechest.presentation.base.dialog

sealed class DialogButton {
    object Positive : DialogButton()
    object Negative : DialogButton()
}
