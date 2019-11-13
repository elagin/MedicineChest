package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.view_app_picker.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isGone
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs
import ru.crew4dev.medicinechest.presentation.widgets.form.FormField

class AppPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), FormField {

    private var title: String? = null
    private var hint: String? = null
    private var itemList: List<Pair<String, Int>> = listOf()
    private var errorText: String = ""

    override var isFilledCallback: ((Boolean) -> Unit)? = null

    var item: String? = null
        set(value) {
            field = value
            app_picker_hint.text = value ?: hint ?: ""
            app_picker_error.isGone = true
            onItemChanged?.invoke(value)
            isFilledCallback?.invoke(value != null)
        }

    var onItemChanged: ((String?) -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_app_picker)
        obtainAttrs(attrs, R.styleable.AppPicker) {
            title = it.getString(R.styleable.AppPicker_title)
            hint = it.getString(R.styleable.AppPicker_hint)
            app_picker_hint.text = hint
        }
        app_picker_title.text = title
        setOnClickListener { openPickerDialog() }
    }

    override fun getValue(): String? {
        return if (isFilled()) {
            clearError()
            item
        } else {
            showError()
            null
        }
    }

    override fun isFilled() = item != null

    override fun setErrorText(errorText: String?) {
        this.errorText = errorText ?: this.errorText
    }

    override fun clearError() {
        app_picker_error.run {
            isVisible = false
            text = errorText
        }
    }

    fun setItemList(itemList: List<Pair<String, Int>>) {
        this.itemList = itemList
        item = null
    }

    fun getSelectedItemId(): Int? {
        val currentItem = item ?: return null
        return itemList.firstOrNull { it.first == currentItem }?.second
    }

    private fun showError() {
        app_picker_error.run {
            isVisible = true
            text = errorText
        }
    }

    private fun openPickerDialog() {
        val itemArray = itemList.map { it.first }.toTypedArray()

        AlertDialog.Builder(context)
            .setTitle(title ?: "")
            .setSingleChoiceItems(itemArray, itemArray.indexOf(item)) { dialog, which ->
                item = itemArray[which]
                dialog.dismiss()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}