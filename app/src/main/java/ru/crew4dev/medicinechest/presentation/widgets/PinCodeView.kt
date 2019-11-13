package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_pin.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import java.util.*

class PinCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val maxDigit = 4

    private var code: String = ""
    private val digitViews = ArrayList<TextView>()
    private val pointViews = ArrayList<View>()

    var isFormFilled: ((Boolean) -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_pin)
        initForm()
    }

    fun getCode(): String = code

    fun clearCode() {
        code = ""
        pointViews.forEach { it.isSelected = false }
        isFormFilled?.invoke(isField())
    }

    private fun initForm() {
        digitViews.addAll(
            listOf(
                digit_0, digit_1, digit_2, digit_3, digit_4,
                digit_5, digit_6, digit_7, digit_8, digit_9
            )
        )

        pointViews.addAll(
            listOf(
                pin_1, pin_2, pin_3, pin_4
            )
        )
        for (digit in digitViews) {
            digit.setOnClickListener { clickDigit(digit.text.toString()) }
        }
        back_space.setOnClickListener { clickBackSpace() }
    }

    private fun isField(): Boolean = code.length == maxDigit

    private fun clickBackSpace() {
        if (code.isEmpty())
            return
        code = code.substring(0, code.length - 1)
        pointViews[code.length].isSelected = false
        isFormFilled?.invoke(isField())
    }

    private fun clickDigit(digit: String) {
        if (isField())
            return
        code += digit
        pointViews[code.length - 1].isSelected = true
        isFormFilled?.invoke(isField())
    }

}