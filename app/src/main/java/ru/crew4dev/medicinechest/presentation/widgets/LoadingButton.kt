package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_loading_button.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var title: String = ""
    private var onClickListener: OnClickListener? = null

    var isLoading: Boolean = false
        set(value) {
            loading_progress.isVisible = value
            loading_button.text = if (value) "" else title
            loading_button.setOnClickListener(if (value) null else onClickListener)
            field = value
        }

    init {
        inflateViewAsRoot(R.layout.view_loading_button)
        obtainAttrs(attrs, R.styleable.LoadingButton) {
            setTitle(it.getString(R.styleable.LoadingButton_title))
        }

        loading_progress.isVisible = false
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
        loading_button.setOnClickListener(onClickListener)
    }

    override fun isEnabled(): Boolean {
        return loading_button.isEnabled
    }

    override fun setEnabled(enabled: Boolean) {
        loading_button.isEnabled = enabled
    }

    fun setTitle(title: String?) {
        this.title = title ?: ""
        loading_button.text = this.title
    }

}