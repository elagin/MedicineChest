package ru.crew4dev.medicinechest.presentation.screens.menu

import android.os.Bundle
import android.widget.ScrollView
import androidx.core.view.ViewCompat
import androidx.transition.AutoTransition
import androidx.transition.TransitionSet
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.layout_mcb_buttons.*
import kotlinx.android.synthetic.main.layout_npf_buttons.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.drawer.DrawerHolderFragment
import ru.crew4dev.medicinechest.app.drawer.DrawerItem
import ru.crew4dev.medicinechest.app.utils.hideKeyboard
import ru.crew4dev.medicinechest.di.DI
import ru.crew4dev.medicinechest.domain.exceptions.ConvertException
import ru.crew4dev.medicinechest.domain.model.login.CardType
import ru.crew4dev.medicinechest.presentation.base.isGone
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.viewmodel.VMFragment
import ru.crew4dev.medicinechest.presentation.screens.main.MainActivity
import ru.crew4dev.medicinechest.presentation.screens.search.SearchMenuFragment
import ru.crew4dev.medicinechest.presentation.widgets.ExpandCard

class MenuFragment : VMFragment<MenuViewModel>(), DrawerHolderFragment {
    companion object {
        const val TRANSITION_DURATION = 250L
    }

    override val layoutResource: Int = R.layout.fragment_menu

    override val viewModel: MenuViewModel by lazy { injectViewModel { DI.user.get().menuViewModel() } }

    override fun initUi(savedInstanceState: Bundle?) {
        menu_search_card_icon.setOnClickListener { getBaseActivity<MainActivity>().isDrawerOpened(true) }
        menu_search_card.setOnClickListener { onSearchPressed() }

        menu_vsk_card.onClick = { viewModel.onVskPressed() }

        mcb_debit_button.setOnClickListener { viewModel.onMcbDebitPressed() }
        mcb_credit_button.setOnClickListener { viewModel.onMcbCreditPressed() }
        mcb_loan_button.setOnClickListener { viewModel.onMcbLoanPressed() }
        //todo family
        //mcb_family_team_button.setOnClickListener { viewModel.onMcbFamilyTeamPressed() }

        menu_dvb_card.onClick = { viewModel.onDvbIssuePressed() }
        mcb_hypothec_button.setOnClickListener { viewModel.onHypothecIssuePressed() }

        menu_ops_card.onClick = {
            delay(ExpandCard.ANIMATE_DURATION_MS) { menu_scroll?.fullScroll(ScrollView.FOCUS_DOWN) }
        }
        menu_ops_button.setOnClickListener { viewModel.onOpsPressed() }
        menu_ipo_button.setOnClickListener { viewModel.onIpoPressed() }
        menu_wp.onClick = { viewModel.onWpPressed() }
    }

    override fun initViewModel() {
        viewModel.cardType.subscribe { handleCardType(it) }
        viewModel.onAfterInit(this, requireContext().applicationContext)
    }

    override fun onResume() {
        super.onResume()
        getBaseActivity<MainActivity>().updateSelectedDrawerItem(DrawerItem.MENU)
        delay(50) { hideKeyboard(baseActivity) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResult(requireContext().applicationContext, requestCode, grantResults)
    }

    private fun onSearchPressed() {
        val sharedElementView = menu_search_card
        val sharedElementName = ViewCompat.getTransitionName(sharedElementView) ?: return

        getBaseActivity<MainActivity>().run {
            supportFragmentManager
                .beginTransaction()
                .addSharedElement(sharedElementView, sharedElementName)
                .replace(getFragmentsContainerId(), SearchMenuFragment().apply {
                    sharedElementEnterTransition = TransitionSet().apply {
                        addTransition(AutoTransition())
                        duration = TRANSITION_DURATION
                    }
                    sharedElementReturnTransition = TransitionSet().apply {
                        addTransition(AutoTransition())
                        duration = TRANSITION_DURATION
                    }
                })
                .addToBackStack(null)
                .commit()
        }
    }

    private fun handleCardType(cardType: CardType) {
        when (cardType) {
            CardType.MCB -> {
                menu_mkb_card.isVisible = true
                menu_dvb_card.isGone = true
            }
            CardType.DVB -> {
                menu_dvb_card.isVisible = true
                menu_mkb_card.isGone = true
            }
            CardType.MCB_DVB -> {
                menu_mkb_card.isVisible = true
                menu_dvb_card.isVisible = true
            }
            else -> throw ConvertException()
        }
    }
}