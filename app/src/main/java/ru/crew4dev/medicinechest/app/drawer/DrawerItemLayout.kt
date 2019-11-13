package ru.crew4dev.medicinechest.app.drawer

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.view_drawer_item.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs
import ru.crew4dev.medicinechest.presentation.base.setColor

class DrawerItemLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var isCurrent: Boolean = false
        set(value) {
            val color = if (value) R.color.crimson_glory else R.color.gray
            drawer_item_icon.setColor(color)
            field = value
        }

    init {
        inflateViewAsRoot(R.layout.view_drawer_item)
        obtainAttrs(attrs, R.styleable.DrawerItemLayout) {
            setIcon(it.getResourceId(R.styleable.DrawerItemLayout_icon, Int.MIN_VALUE))
            setTitle(it.getString(R.styleable.DrawerItemLayout_title))
        }
        drawer_item_icon.setColor(R.color.gray)
    }

    private fun setIcon(@DrawableRes resId: Int) {
        if (resId == Int.MIN_VALUE) return
        drawer_item_icon.setImageResource(resId)
    }

    private fun setTitle(title: String?) {
        title ?: return
        drawer_item_title.text = title
    }

}
