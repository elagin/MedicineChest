package ru.crew4dev.medicinechest.presentation.widgets.form

interface FormField {
    var isFilledCallback: ((Boolean) -> Unit)?
    fun getValue(): String?
    fun isFilled(): Boolean
    fun setErrorText(errorText: String?)
    fun clearError()
}