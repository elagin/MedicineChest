package ru.crew4dev.medicinechest.presentation.widgets.form

import android.content.Context
import android.graphics.Rect
import android.text.*
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.view_form_input.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.domain.validation.Validator
import ru.crew4dev.medicinechest.presentation.base.*


class FormInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), FormField {

    private var value: String? = null
    private var errorText: String = ""
    @DrawableRes
    private var rightIcon: Int? = null

    private var isFilled = false
    private var isClearAvailable = true
    private var isCapitalize = false

    private var textChangeListener: TextWatcher? = null
    private var validator: Validator? = null

    override var isFilledCallback: ((Boolean) -> Unit)? = null

    var onTextChanged: ((String) -> Unit)? = null
    var onClearClicked: (() -> Unit)? = null
    var onDoneClicked: (() -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_form_input)
        obtainAttrs(attrs, R.styleable.FormInput) {
            setHint(it.getString(R.styleable.FormInput_hint))
            setAdditionalHint(it.getString(R.styleable.FormInput_additionalHint))
            setMaxLength(it.getInteger(R.styleable.FormInput_maxLength, -1))
            setInputType(it.getInteger(R.styleable.FormInput_inputType, -1))
            setAvailableDigits(it.getString(R.styleable.FormInput_digits))
            setIsCapitalize(it.getBoolean(R.styleable.FormInput_capitalize, isCapitalize))
            setErrorText(it.getString(R.styleable.FormInput_error))
            it.getString(R.styleable.FormInput_helperText)?.let { helperText -> setHelperText(helperText) }
            setRightIcon(it.getResourceId(R.styleable.FormInput_rightIcon, -1))
            setLeadingText(it.getString(R.styleable.FormInput_leadingText))
            setAllCaps(it.getBoolean(R.styleable.FormInput_allCaps, false))
            setClearAvailable(it.getBoolean(R.styleable.FormInput_clearAvailable, true))
            setLimit(it.getInteger(R.styleable.FormInput_limit, -1))
        }
        form_input.onDoneClicked { onDoneClicked?.invoke() }
        textChangeListener = form_input.onTextChanged { handleInputChanges(it, it.isNotEmpty()) }
    }

    ///

    override fun setEnabled(isEnabled: Boolean) {
        form_input_layout.isEnabled = isEnabled
    }

    override fun setOnClickListener(l: OnClickListener?) {
        form_input.setOnClickListener(l)
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        form_input.onFocusChangeListener = l
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return form_input.requestFocus(direction, previouslyFocusedRect)
    }

    override fun clearFocus() {
        form_input.clearFocus()
        form_input_layout.clearFocus()
    }

    override fun isFilled() = isFilled

    override fun getValue(): String? {
        return if (isFilled) {
            clearError()
            value
        } else {
            showError()
            null
        }
    }

    override fun setErrorText(errorText: String?) {
        this.errorText = errorText ?: this.errorText
    }

    override fun clearError() {
        form_input_layout.error = ""
    }

    fun getTextSize(): Int {
        return if (value == null) 0
        else value?.length ?: return 0
    }

    fun isEmpty() = form_input.text?.isEmpty() ?: true

    fun isFocusable(isFocusable: Boolean) {
        form_input.run {
            this.isFocusable = isFocusable
            this.isFocusableInTouchMode = isFocusable
        }
    }

    ///

    fun getInputView(): TextInputEditText = form_input

    fun setValue(value: String?) {
        this.value = value ?: ""
        form_input.setText(this.value)
        form_input.setSelection(form_input.text?.length ?: 0)
    }

    fun setValueSilent(value: String?) {
        val listener = onTextChanged
        onTextChanged = null
        setValue(value)
        onTextChanged = listener
    }

    fun clearInput() {
        form_input.text?.clear()
        onClearClicked?.invoke()
    }

    fun setHelperText(text: String) {
        form_input_helper.text = text
    }

    fun setInputMask(mask: String) {
        form_input.removeTextChangedListener(textChangeListener)
        textChangeListener = null
        form_input.addMask(
            mask = mask,
            maskFilledAction = { isFilled, value -> handleInputChanges(value, isFilled) }
        )
    }

    fun setValidator(validator: Validator) {
        this.validator = validator
    }

    private fun setLimit(limit: Int) {
        if (limit == -1) return

        val currentFilters = form_input.filters.toMutableList()
        currentFilters.add(InputFilter.LengthFilter(limit))
        form_input.filters = currentFilters.toTypedArray()
    }

    ///

    private fun handleInputChanges(value: String, isFilled: Boolean) {
        clearError()

        this.value = value
        onTextChanged?.invoke(value)

        this.isFilled = isFilled
        isFilledCallback?.invoke(this.isFilled)

        if (isFilled) validate(value)

        if (isClearAvailable) {
            val rightIcon = rightIcon

            when {
                value.isNotEmpty() -> form_input_right_icon.apply {
                    isVisible = true
                    setImageResource(R.drawable.ic_cancel)
                    setOnClickListener { clearInput() }
                }
                rightIcon != null -> form_input_right_icon.apply {
                    isVisible = true
                    setImageResource(rightIcon)
                    setOnClickListener(null)
                }
                else -> form_input_right_icon.apply {
                    isInvisible = true
                    setOnClickListener(null)
                }
            }
        } else {
            form_input_right_icon.apply {
                isInvisible = true
                setOnClickListener(null)
            }
        }

        form_input.setTextColor(
            ContextCompat.getColor(
                context,
                if (value.isNotEmpty()) R.color.charcoal else R.color.manatee
            )
        )
    }

    private fun validate(value: String) {
        val validator = validator ?: return

        val isValid = validator.validate(value)
        this.isFilled = isValid
        isFilledCallback?.invoke(isValid)
        form_input_layout.error = if (isValid) "" else validator.errorText
    }

    private fun showError() {
        form_input_layout.error = errorText
    }

    ///setters

    private fun setHint(hint: String?) {
        hint ?: return

        form_input_layout.hint = hint
    }

    private fun setAdditionalHint(hint: String?) {
        hint ?: return

        form_input.hint = hint
    }

    private fun setMaxLength(length: Int) {
        if (length == -1) {
            form_input_layout.isCounterEnabled = false
            return
        }

        form_input.filters = arrayOf(InputFilter.LengthFilter(length))
        form_input_layout.isCounterEnabled = true
        form_input_layout.counterMaxLength = length
    }

    private fun setAvailableDigits(digits: String?) {
        digits ?: return

        form_input.keyListener = DigitsKeyListener.getInstance(digits)
    }

    private fun setInputType(type: Int) {
        form_input.inputType = when (type) {
            0 -> InputType.TYPE_CLASS_PHONE
            1 -> InputType.TYPE_CLASS_NUMBER
            2 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        }
    }

    private fun setIsCapitalize(isCapitalize: Boolean) {
        this.isCapitalize = isCapitalize
        if (isCapitalize) {
            form_input.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS or InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        }
    }

    private fun setRightIcon(@DrawableRes resId: Int) {
        if (resId == -1) return

        rightIcon = resId
        form_input_right_icon.run {
            isVisible = true
            setImageResource(resId)
        }
    }

    private fun setLeadingText(leadingText: String?) {
        leadingText ?: return

        form_input.run {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (!s.toString().startsWith(leadingText)) {
                        setText(leadingText)
                        val text = text ?: return
                        Selection.setSelection(text, text.length)
                    }
                }
            })
        }
    }

    private fun setAllCaps(isCaps: Boolean) {
        if (isCaps)
            form_input.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
    }

    private fun setClearAvailable(isAvailable: Boolean) {
        isClearAvailable = isAvailable
    }
}