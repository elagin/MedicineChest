package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.crew4dev.medicinechest.BuildConfig
import ru.crew4dev.medicinechest.R

class AppVersion @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        text = resources.getString(R.string.app_version, BuildConfig.VERSION_NAME)
    }

}