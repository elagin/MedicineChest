package ru.crew4dev.medicinechest.presentation.widgets

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.crew4dev.medicinechest.R

class CustomSnackBar @JvmOverloads constructor(
    view: View,
    @StringRes textResId: Int,
    duration: Int = 1_000)  {

    private var mSnackbar: Snackbar = Snackbar.make(view, textResId, duration)

    init {
        mSnackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.gainsboro))
        (mSnackbar.view.findViewById(R.id.snackbar_text) as TextView).setTextColor(
            ContextCompat.getColor(view.context, R.color.crimson_glory))
    }

    fun show() {
        mSnackbar.show()
    }
}