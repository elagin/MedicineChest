package ru.crew4dev.medicinechest.presentation.widgets.form.asset

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_asset.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.domain.model.mcb.credit.McbCreditData
import ru.crew4dev.medicinechest.domain.model.mcb.credit.secondary.money.Asset

class AssetListAdapter(
    var assetList: MutableList<Asset> = mutableListOf()
) : RecyclerView.Adapter<AssetListAdapter.VH>() {

    var onAssetListChanged: ((List<Asset>) -> Unit)? = null

    override fun getItemCount() = assetList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_asset, parent, false)
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(assetList[position], ::delete)
    }

    fun insert(asset: Asset) {
        assetList.add(asset)
        onAssetListChanged?.invoke(assetList)
        notifyItemInserted(assetList.size - 1)
    }

    private fun delete(asset: Asset) {
        val position = assetList.indexOf(asset)
        if (position == -1) return

        assetList.remove(asset)
        onAssetListChanged?.invoke(assetList)
        notifyItemRemoved(position)
    }

    fun updateList(assetList: List<Asset>) {
        this.assetList = assetList.toMutableList()
        onAssetListChanged?.invoke(assetList)
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context = itemView.context
        private val type = itemView.item_asset_type
        private val purchaseMethod = itemView.item_asset_purchase_method
        private val menuButton = itemView.item_asset_menu_button

        fun bind(asset: Asset, onDelete: (Asset) -> Unit) {
            type.text = if (asset.isRealty) {
                context.getString(R.string.property_realty)
            } else {
                context.getString(R.string.property_cars)
            }
            purchaseMethod.text = McbCreditData.purchaseType
                .firstOrNull { it.second == asset.purchaseType }
                ?.first
                ?: ""

            menuButton.setOnClickListener { showMenu { onDelete.invoke(asset) } }
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