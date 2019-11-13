package ru.crew4dev.medicinechest.presentation.base.dialog

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

object AlertDialogFactory {

    fun create(
        context: Context,
        @StringRes title: Int? = null,
        @DrawableRes icon: Int? = null,
        @StringRes text: Int? = null,
        @StringRes positive: Int? = null,
        @StringRes negative: Int? = null,
        onClick: ((isPositive: Boolean) -> Unit)? = null
    ): AlertDialog {
        return AlertDialog.Builder(context).apply {
            title?.let { setTitle(it) }
            icon?.let { setIcon(it) }
            text?.let { setMessage(it) }
            positive?.let {
                setPositiveButton(it) { dialog, _ ->
                    onClick?.invoke(true)
                    dialog.dismiss()
                }
            }
            negative?.let {
                setNegativeButton(it) { dialog, _ ->
                    onClick?.invoke(false)
                    dialog.dismiss()
                }
            }
        }.create()
    }
}