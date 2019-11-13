package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.view_search_card.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.utils.showKeyboard
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs
import ru.crew4dev.medicinechest.presentation.base.onTextChanged

class SearchCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onLeftIconClick: (() -> Unit)? = null
    var onInputChanged: ((String) -> Unit)? = null
    var onInputCleared: (() -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_search_card)
        obtainAttrs(attrs, R.styleable.SearchCard) {
            setLeftIcon(it.getResourceId(R.styleable.SearchCard_leftIcon, -1))
        }
        isClickable = true

        search_card_input.onTextChanged { input ->
            search_card_clear_button.isVisible = input.isNotEmpty()
            onInputChanged?.invoke(input)
        }
        search_card_clear_button.setOnClickListener {
            search_card_input.text?.clear()
            onInputCleared?.invoke()
        }
    }

    fun requestKeyboard() {
        showKeyboard(search_card_input)
    }

    private fun setLeftIcon(@DrawableRes resId: Int) {
        if (resId == -1) return

        search_card_icon.setImageResource(resId)
        search_card_icon.setOnClickListener { onLeftIconClick?.invoke() }
    }
}