package ru.crew4dev.medicinechest.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_drug.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.data.Drug

class DrugListAdapter (

    private var clientList: List<Drug> = emptyList(),
    private val onItemClick: (Drug) -> Unit
) : RecyclerView.Adapter<DrugListAdapter.VH>() {

    override fun getItemCount() = clientList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_drug, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(clientList[position], onItemClick)

    fun updateList(clientList: List<Drug>) {
        this.clientList = clientList
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val surname = itemView.item_client_surname
        private val name = itemView.item_client_name
        private val phone = itemView.item_client_phone

        @SuppressLint("SetTextI18n")
        fun bind(drug: Drug, onItemClick: (Drug) -> Unit) {
            //surname.text = drug.surname
            name.text = (drug.name ?: "")
            //phone.text = PhoneConverter.hideNumber(client.phoneNumber ?: "")
            itemView.setOnClickListener { onItemClick.invoke(drug) }
        }
    }
}