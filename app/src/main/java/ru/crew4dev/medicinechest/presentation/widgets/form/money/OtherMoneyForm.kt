package ru.crew4dev.medicinechest.presentation.widgets.form.money

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_other_money_form.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.navigation.NavActivity
import ru.crew4dev.medicinechest.domain.model.mcb.credit.secondary.money.OtherMoney
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.initList
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.dialogs.McbCreditDialogFactory

class OtherMoneyForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val otherMoneyListAdapter = OtherMoneyListAdapter()

    init {
        inflateViewAsRoot(R.layout.view_other_money_form)
        otherMoneyListAdapter.onOtherMoneyListChanged = { other_money_form_list.isVisible = it.isNotEmpty() }
        other_money_form_list.initList(otherMoneyListAdapter)
        other_money_form_add_button.setOnClickListener { showAddOtherMoneyDialog() }
    }

    fun getOtherMoneyList(): List<OtherMoney> = otherMoneyListAdapter.otherMoneyList.toList()

    fun updateOtherMoneyList(otherMoneyList: List<OtherMoney>) = otherMoneyListAdapter.updateList(otherMoneyList)

    private fun showAddOtherMoneyDialog() {
        McbCreditDialogFactory.createOtherMoneyDialog().run {
            onOtherMoneySaved = { otherMoneyListAdapter.insert(it) }
            (this@OtherMoneyForm.context as NavActivity<*>).currentFragment()?.let { show(it) }
        }
    }
}