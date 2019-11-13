package ru.crew4dev.medicinechest.presentation.widgets.form

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_client_form.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.data.converters.DateConverter
import ru.crew4dev.medicinechest.domain.model.client.Client
import ru.crew4dev.medicinechest.domain.validation.EmailValidator
import ru.crew4dev.medicinechest.domain.validation.Masks
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs

class ClientForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var client: Client? = null
    private val requiredFields = mutableListOf<FormInput>()

    var isFormFilled: ((Boolean) -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_client_form)
        obtainAttrs(attrs, R.styleable.ClientForm) {
            handleEmailRequired(it.getBoolean(R.styleable.ClientForm_emailRequired, true))
        }
        initForm()
    }

    fun fillForm(client: Client) {
        this.client = client

        client_form_surname.setValue(client.surname)
        client_form_name.setValue(client.name)
        client_form_middle_name.setValue(client.middleName ?: "")
        client.birthday?.let { client_form_birthday.setValue(DateConverter.toDateString(it)) }
        client_form_phone_number.setValue(client.phoneNumber)
        client_form_email.setValue(client.email ?: "")

        checkFormIsFilled()
    }

    fun collectForm() = Client(
        id = client?.id ?: -1,
        surname = client_form_surname.getValue()!!,
        name = client_form_name.getValue()!!,
        middleName = client_form_middle_name.getValue(),
        birthday = DateConverter.fromDateString(client_form_birthday.getValue()!!),
        email = client_form_email.getValue(),
        phoneNumber = "7${client_form_phone_number.getValue()!!}"
    )

    private fun initForm() {
        client_form_birthday.setInputMask(Masks.DATE)
        client_form_phone_number.setInputMask(Masks.PHONE)

        requiredFields.addAll(
            listOf(
                client_form_surname,
                client_form_name,
                client_form_birthday,
                client_form_phone_number
            )
        )

        requiredFields.forEach {
            it.isFilledCallback = { checkFormIsFilled() }
        }
    }

    private fun handleEmailRequired(isRequiredEmail: Boolean) {
        if (!isRequiredEmail) return

        requiredFields.add(client_form_email)
        client_form_email.setValidator(EmailValidator(context.getString(R.string.invalid_email_format)))
        client_form_email.isFilledCallback = { checkFormIsFilled() }
    }

    private fun checkFormIsFilled() {
        isFormFilled?.invoke(requiredFields.all { it.isFilled() })
    }

}