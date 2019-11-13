package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.view_app_toolbar.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs

class AppToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onLeftIconClick: (() -> Unit)? = null
    var onRightButtonClick: (() -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_app_toolbar)
        obtainAttrs(attrs, R.styleable.AppToolbar) {
            setTitle(it.getString(R.styleable.AppToolbar_title))
            setProgress(it.getInteger(R.styleable.AppToolbar_progress, 0))
            setLeftIcon(it.getResourceId(R.styleable.AppToolbar_leftIcon, -1))
            setRightButtonText(it.getString(R.styleable.AppToolbar_rightButtonText))
        }
    }

    fun setTitle(title: String?) {
        title ?: return

        app_toolbar_title.text = title
    }

    fun setTitle(@StringRes resId: Int) {
        app_toolbar_title.setText(resId)
    }

    fun setProgress(progress: Int) {
        app_toolbar_progress.isVisible = progress != 0
        app_toolbar_progress.progress = progress
    }

    private fun setLeftIcon(@DrawableRes resId: Int) {
        if (resId == -1) return

        app_toolbar_icon_left.setImageResource(resId)
        app_toolbar_icon_left.setOnClickListener { onLeftIconClick?.invoke() }
    }

    private fun setRightButtonText(text: String?) {
        app_toolbar_button_right.isVisible = text != null
        text ?: return

        app_toolbar_button_right.run {
            this.text = text
            setOnClickListener { onRightButtonClick?.invoke() }
        }
    }
}