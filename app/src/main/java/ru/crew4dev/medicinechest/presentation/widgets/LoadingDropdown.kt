package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_loading_dropdown.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isGone
import ru.crew4dev.medicinechest.presentation.base.isVisible

class LoadingDropdown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var isLoading: Boolean = false
        set(isLoading) {
            loading_progress.isVisible = isLoading
            loading_dropdown_arrow.isGone = isLoading
            field = isLoading
        }

    var text: String = ""
        set(text) {
            loading_dropdown.text = text
            field = text
        }

    init {
        inflateViewAsRoot(R.layout.view_loading_dropdown)
    }

}