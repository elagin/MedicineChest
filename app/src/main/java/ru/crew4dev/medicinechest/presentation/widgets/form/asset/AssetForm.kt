package ru.crew4dev.medicinechest.presentation.widgets.form.asset

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_asset_form.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.navigation.NavActivity
import ru.crew4dev.medicinechest.domain.model.mcb.credit.secondary.money.Asset
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.initList
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.dialogs.McbCreditDialogFactory

class AssetForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val assetListAdapter = AssetListAdapter()

    init {
        inflateViewAsRoot(R.layout.view_asset_form)
        assetListAdapter.onAssetListChanged = { asset_form_list.isVisible = it.isNotEmpty() }
        asset_form_list.initList(assetListAdapter)
        asset_form_add_button.setOnClickListener { showAddOtherMoneyDialog() }
    }

    fun getAssetList(): List<Asset> = assetListAdapter.assetList.toList()

    fun updateAssetList(assetList: List<Asset>) = assetListAdapter.updateList(assetList)

    private fun showAddOtherMoneyDialog() {
        McbCreditDialogFactory.createAssetDialog().run {
            onAssetSaved = { assetListAdapter.insert(it) }
            (this@AssetForm.context as NavActivity<*>).currentFragment()?.let { show(it) }
        }
    }
}