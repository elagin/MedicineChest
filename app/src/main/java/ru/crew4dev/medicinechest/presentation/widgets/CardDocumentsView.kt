package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.view_card_document.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs
import ru.crew4dev.medicinechest.presentation.base.setColor

class CardDocumentsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0
) : LinearLayout(context, attributeSet, defAttrStyle) {

    var text: String
        get() = doc_text.text.toString()
        set(value) {
            doc_text.text = value
        }

    init {
        inflateViewAsRoot(R.layout.view_card_document)
        obtainAttrs(attributeSet, R.styleable.CardDocumentsView) { ta ->
            ta.getString(R.styleable.CardDocumentsView_text)?.let { text = it }
            setIcon(ta.getResourceId(R.styleable.CardDocumentsView_icon, -1))
        }
    }

    fun setIcon(@DrawableRes resId: Int) {
        if (resId == -1) return
        doc_icon.setImageResource(resId)
    }

    fun setOnClickListener(onClick: () -> Unit) {
        super.setOnClickListener { onClick.invoke() }
    }

    fun setImageColor(@ColorRes color: Int) {
        doc_icon.setColor(color)
    }

    fun setTextColor(@ColorRes color: Int) {
        doc_text.setColor(color)
    }
}