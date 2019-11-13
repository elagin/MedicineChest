package ru.crew4dev.medicinechest.presentation.base

import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.addMask(
    mask: String,
    maskFilledAction: (isFilled: Boolean, value: String) -> Unit
) {
    MaskedTextChangedListener.installOn(
        this,
        mask,
        object : MaskedTextChangedListener.ValueListener {
            override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
                maskFilledAction.invoke(maskFilled, extractedValue)
            }
        }
    )
}

fun EditText.onTextChanged(action: (String) -> Unit): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s ?: return
            action.invoke(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}

fun EditText.onDoneClicked(action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) action.invoke()
        return@setOnEditorActionListener false
    }
}

fun ViewGroup.inflateViewAsRoot(layoutResId: Int): View? = View.inflate(context, layoutResId, this)

fun ViewGroup.obtainAttrs(
    attrs: AttributeSet?, @StyleableRes attrsId: IntArray,
    callback: (array: TypedArray) -> Unit
) {
    val array = context.obtainStyledAttributes(attrs, attrsId)
    callback.invoke(array)
    array.recycle()
}

fun AppCompatImageView.setIcon(@DrawableRes iconRes: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, iconRes))
}

fun AppCompatImageView.setColor(@ColorRes colorRes: Int) {
    setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN)
}

fun AppCompatTextView.setColor(@ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
}

fun CheckBox.isChecked(callback: (Boolean) -> Unit) {
    setOnCheckedChangeListener { _, isChecked -> callback.invoke(isChecked) }
}

fun RecyclerView.initList(recyclerAdapter: RecyclerView.Adapter<*>, scrollable: Boolean = false) {
    layoutManager = LinearLayoutManager(context)
    adapter = recyclerAdapter
    isNestedScrollingEnabled = scrollable
}

fun RecyclerView.initGrid(recyclerAdapter: RecyclerView.Adapter<*>, rowCount: Int, scrollable: Boolean = false) {
    layoutManager = GridLayoutManager(context, rowCount)
    adapter = recyclerAdapter
    isNestedScrollingEnabled = scrollable
}

fun FloatingActionsMenu.isExpanded(isCollapsed: (Boolean) -> Unit) {
    setOnFloatingActionsMenuUpdateListener(object :
        FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
        override fun onMenuCollapsed() {
            isCollapsed.invoke(false)
        }

        override fun onMenuExpanded() {
            isCollapsed.invoke(true)
        }
    })
}

inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

inline var View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

inline var View.isGone: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }