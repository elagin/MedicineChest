package ru.crew4dev.medicinechest.presentation.widgets.form

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_birth_place.view.*
import kotlinx.android.synthetic.main.view_form_birth_place.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.domain.model.client.Address
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot

class BirthPlaceForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)  {

    private val birthPlaceListAdapter: BirthPlaceListAdapter

    var onInputChanged: ((input: String) -> Unit)? = null
    var onBirthPlaceClick: ((Address) -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_form_birth_place)
        birth_place_input.onTextChanged = { input ->
            if (input.length > 2) onInputChanged?.invoke(input)
        }
        birthPlaceListAdapter = BirthPlaceListAdapter(emptyList()) { onBirthPlaceClick?.invoke(it) }
        birth_place_list.run {
            isNestedScrollingEnabled = true
            layoutManager = LinearLayoutManager(context)
            adapter = birthPlaceListAdapter
        }
    }

    fun updateSuggestList(suggestList: List<Address>) {
        birthPlaceListAdapter.run {
            addressList = suggestList
            notifyDataSetChanged()
        }
    }

    private class BirthPlaceListAdapter(
        var addressList: List<Address>,
        var onClick: (Address) -> Unit
    ) : RecyclerView.Adapter<BirthPlaceListAdapter.VH>() {

        override fun getItemCount() = addressList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_birth_place, parent, false)
        )

        override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(addressList[position], onClick)

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val region = itemView.item_birth_place_region
            private val city = itemView.item_birth_place_city

            fun bind(address: Address, onClick: (Address) -> Unit) {
                region.text = address.region?.trim()
                city.text = address.city?.trim()
                itemView.setOnClickListener { onClick.invoke(address) }
            }
        }
    }
}