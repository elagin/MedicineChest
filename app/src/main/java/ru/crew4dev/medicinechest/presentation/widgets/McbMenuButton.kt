package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.view_mcb_menu_button.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs
import ru.crew4dev.medicinechest.presentation.base.setIcon

class McbMenuButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflateViewAsRoot(R.layout.view_mcb_menu_button)
        obtainAttrs(attrs, R.styleable.McbMenuButton) { array ->
            array.getString(R.styleable.McbMenuButton_title)?.let { setTitle(it) }
            array.getResourceId(R.styleable.McbMenuButton_icon, -1)
                .takeIf { it != -1 }?.let { setIcon(it) }
        }
    }

    private fun setTitle(title: String) {
        mcb_button_title.text = title
    }

    private fun setIcon(@DrawableRes iconResId: Int) {
        mcb_button_icon.setIcon(iconResId)
    }
}