package ru.crew4dev.medicinechest.presentation.widgets.form.money

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_other_money.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.data.converters.MoneyConverter
import ru.crew4dev.medicinechest.domain.model.mcb.credit.McbCreditData
import ru.crew4dev.medicinechest.domain.model.mcb.credit.secondary.money.OtherMoney

class OtherMoneyListAdapter(
    var otherMoneyList: MutableList<OtherMoney> = mutableListOf()
) : RecyclerView.Adapter<OtherMoneyListAdapter.VH>() {

    var onOtherMoneyListChanged: ((List<OtherMoney>) -> Unit)? = null

    override fun getItemCount() = otherMoneyList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_other_money, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(otherMoneyList[position], ::delete)
    }

    fun insert(otherMoney: OtherMoney) {
        otherMoneyList.add(otherMoney)
        onOtherMoneyListChanged?.invoke(otherMoneyList)
        notifyItemInserted(otherMoneyList.size - 1)
    }

    private fun delete(otherMoney: OtherMoney) {
        val position = otherMoneyList.indexOf(otherMoney)
        if (position == -1) return

        otherMoneyList.remove(otherMoney)
        onOtherMoneyListChanged?.invoke(otherMoneyList)
        notifyItemRemoved(position)
    }

    fun updateList(otherMoneyList: List<OtherMoney>) {
        this.otherMoneyList = otherMoneyList.toMutableList()
        onOtherMoneyListChanged?.invoke(otherMoneyList)
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context = itemView.context
        private val category = itemView.item_other_money_category
        private val type = itemView.item_other_money_type
        private val sum = itemView.item_other_money_sum
        private val menuButton = itemView.item_other_money_menu_button

        fun bind(otherMoney: OtherMoney, onDelete: (OtherMoney) -> Unit) {
            category.text = McbCreditData.otherMoneyCategory
                .firstOrNull { it.second == otherMoney.category }
                ?.first
                ?: ""
            type.text = McbCreditData.otherMoneyTypeIncome
                .firstOrNull { it.second == otherMoney.type }
                ?.first
                ?: McbCreditData.otherMoneyTypeOutcome
                    .firstOrNull { it.second == otherMoney.type }
                    ?.first
                        ?: ""
            sum.text = MoneyConverter.sumToString(otherMoney.sum)

            menuButton.setOnClickListener { showMenu { onDelete.invoke(otherMoney) } }
        }

        private fun showMenu(onDelete: () -> Unit) {
            PopupMenu(context, menuButton).run {
                menu.add(Menu.NONE, 0, Menu.NONE, context.getString(R.string.delete))
                setOnMenuItemClickListener {
                    if (it.itemId == 0) onDelete.invoke()
                    true
                }
                show()
            }
        }
    }

}