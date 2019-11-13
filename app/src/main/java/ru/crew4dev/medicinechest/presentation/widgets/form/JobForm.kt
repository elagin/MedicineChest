package ru.crew4dev.medicinechest.presentation.widgets.form

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.widget.FrameLayout
import androidx.appcompat.widget.PopupMenu
import kotlinx.android.synthetic.main.view_job_form.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.data.converters.DateConverter
import ru.crew4dev.medicinechest.domain.model.mcb.credit.McbCreditData
import ru.crew4dev.medicinechest.domain.model.mcb.credit.search.Organization
import ru.crew4dev.medicinechest.domain.model.mcb.credit.secondary.money.Job
import ru.crew4dev.medicinechest.domain.usecases.AddressUseCase
import ru.crew4dev.medicinechest.domain.validation.Masks
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isGone
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.widgets.CustomSnackBar
import java.util.*

class JobForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val requiredFields = ArrayList<FormField>()
    private val optionalFields = ArrayList<FormField>()
    private var lastFormFilled = false

    private var organizationPopup: PopupMenu? = null
    private var organizationPopupIsShowing = false

    private var addressPopup: PopupMenu? = null
    private var addressPopupIsShowing = false
    private var suggestAddressList: MutableList<String> = mutableListOf()

    var onNameChanged: ((String) -> Unit)? = null
    var onAddressChanged: ((String) -> Unit)? = null
    var isFormFilled: ((Boolean) -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_job_form)

        job_mcb_credit_inn.setInputMask(Masks.INN_EMPLOYER)
        job_mcb_credit_phone.setInputMask(Masks.PHONE)
        job_mcb_credit_started_at.setInputMask(Masks.MONTH_AND_YEAR)

        job_mcb_credit_opf_picker.setItemList(McbCreditData.opfType)
        job_mcb_credit_activity_picker.setItemList(McbCreditData.activityType)
        job_mcb_credit_employer_picker.setItemList(McbCreditData.employerType)
        job_mcb_credit_position_picker.setItemList(McbCreditData.positionType)

        job_mcb_credit_started_at.onTextChanged = { started_at ->
            //Проверить дату (не больше текущей)
            if (started_at.isNotEmpty() && started_at.length == 7) {
                if (DateConverter.shortDateMoreThanCurrent(started_at)) {
                    job_mcb_credit_started_at.clearInput()
                    CustomSnackBar(job_mcb_credit_started_at, R.string.sb_job_created_at_more_than, 3_000).show()
                }
            }
        }

        job_mcb_credit_name.onTextChanged = { onNameChanged?.invoke(it) }
        job_mcb_credit_address.onTextChanged = { onAddressChanged?.invoke(it) }
        job_mcb_credit_percentage.onTextChanged = { percentString ->
            val percent = percentString.toIntOrNull()
            if (percent != null && percent > 100) job_mcb_credit_percentage.setValue("100")
        }

        job_mcb_credit_activity_picker.onItemChanged = {
            when (job_mcb_credit_activity_picker.getSelectedItemId()) {
                //Собственный бизнес/доля
                1 -> {
                    job_mcb_credit_percentage.isVisible = true
                    requiredFields.add(job_mcb_credit_percentage)
                    job_mcb_credit_position_picker.isGone = true
                    requiredFields.remove(job_mcb_credit_position_picker)
                }
                //Индивидуальный предприниматель
                2 -> {
                    job_mcb_credit_percentage.isGone = true
                    requiredFields.remove(job_mcb_credit_percentage)
                    job_mcb_credit_position_picker.isGone = true
                    requiredFields.remove(job_mcb_credit_position_picker)
                }
                //Наемный сотрудник
                3 -> {
                    job_mcb_credit_percentage.isGone = true
                    requiredFields.remove(job_mcb_credit_percentage)
                    job_mcb_credit_position_picker.isVisible = true
                    requiredFields.add(job_mcb_credit_position_picker)
                }
            }
        }

        requiredFields.addAll(
            listOf(
                job_mcb_credit_name,
                job_mcb_credit_position,
                job_mcb_credit_salary,
                job_mcb_credit_started_at,
                job_mcb_credit_phone,
                job_mcb_credit_address,
                job_mcb_credit_employer_picker,
                job_mcb_credit_activity_picker
            )
        )
        requiredFields.forEach { it.isFilledCallback = { onFormUpdated() } }

        optionalFields.addAll(
            listOf(
                job_mcb_credit_opf_picker,
                job_mcb_credit_inn
            )
        )
        optionalFields.forEach { it.isFilledCallback = { onFormUpdated() } }
    }

    fun isFilled() = lastFormFilled

    fun suggestOrganizationList(organizationList: List<Organization>) {
        if (organizationList.isEmpty()) return

        if (organizationPopupIsShowing) {
            organizationPopup?.run {
                menu.clear()
                organizationList.forEach { menu.add(Menu.NONE, it.id, Menu.NONE, it.name) }
            }
        }

        organizationPopup = PopupMenu(context, job_mcb_credit_name).apply {
            organizationList.forEach { menu.add(Menu.NONE, it.id, Menu.NONE, it.name) }
            setOnMenuItemClickListener onClick@{ menuItem ->
                val organization = organizationList.firstOrNull { it.id == menuItem.itemId }
                    ?: return@onClick true

                job_mcb_credit_name.run {
                    onTextChanged = null
                    setValue(organization.name)
                    onTextChanged = { onNameChanged?.invoke(it) }
                }
                organization.inn?.let { job_mcb_credit_inn.setValue(it) }

                return@onClick true
            }
            setOnDismissListener {
                organizationPopup = null
                organizationPopupIsShowing = false
            }
            show()
            organizationPopupIsShowing = true
        }
    }

    fun suggestAddressList(addressList: List<String>) {
        if (addressList.isEmpty()) return

        suggestAddressList.clear()
        suggestAddressList.addAll(addressList)

        val addressToShow =
            AddressUseCase.prepareAddressesToShow(job_mcb_credit_address.getValue() ?: "", suggestAddressList)

        if (addressToShow.isEmpty()) return

        if (addressPopupIsShowing) {
            addressPopup?.run {
                menu.clear()
                addressToShow.forEach {
                    val (index, showText) = it
                    menu.add(Menu.NONE, index, Menu.NONE, showText)
                }
            }
            return
        }

        addressPopup = PopupMenu(context, job_mcb_credit_address).apply {
            setOnMenuItemClickListener onClick@{ menuItem ->
                val value = suggestAddressList[menuItem.itemId]
                job_mcb_credit_address.run {
                    onTextChanged = null
                    setValue(value)
                    onTextChanged = { onAddressChanged?.invoke(it) }
                }

                return@onClick true
            }
            setOnDismissListener {
                addressPopup = null
                addressPopupIsShowing = false
            }

            addressToShow.forEach {
                val (index, showText) = it
                menu.add(Menu.NONE, index, Menu.NONE, showText)
            }

            addressPopupIsShowing = true
            show()
        }
    }

    fun getFormData(showErrorOnNull: Boolean = false): Job? {
        val errorText = context.getString(R.string.error_required)
        if (showErrorOnNull) {
            requiredFields.forEach { it.setErrorText(errorText); it.getValue() }
            if (optionalFields.all { it.getValue() == null }) {
                optionalFields.forEach { it.setErrorText(errorText) }
            } else {
                optionalFields.forEach { it.setErrorText("") }
            }
            optionalFields.forEach { it.getValue() }
        }
        if (!lastFormFilled) return null

        return Job(
            name = job_mcb_credit_name.getValue()!!,
            opf = job_mcb_credit_opf_picker.getSelectedItemId(),
            inn = job_mcb_credit_inn.getValue(),
            activityType = job_mcb_credit_activity_picker.getSelectedItemId()!!,
            employerType = job_mcb_credit_employer_picker.getSelectedItemId()!!,
            positionType = job_mcb_credit_position_picker.getSelectedItemId(),
            percentage = job_mcb_credit_percentage.getValue()?.toInt(),
            position = job_mcb_credit_position.getValue()!!,
            salary = job_mcb_credit_salary.getValue()!!.toInt(),
            startedAt = job_mcb_credit_started_at.getValue()!!,
            phone = job_mcb_credit_phone.getValue()!!,
            additionalPhone = job_mcb_credit_phone_additional.getValue(),
            address = job_mcb_credit_address.getValue()!!
        )
    }

    private fun onFormUpdated() {
        if (job_mcb_credit_inn.isFilled()) job_mcb_credit_opf_picker.clearError()
        if (job_mcb_credit_opf_picker.isFilled()) job_mcb_credit_inn.clearError()

        val formFilled = requiredFields.all { it.isFilled() } && optionalFields.any { it.isFilled() }

        lastFormFilled = formFilled
        isFormFilled?.invoke(formFilled)
    }
}