package ru.crew4dev.medicinechest.app.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.viewmodel.BaseViewModel
import ru.crew4dev.medicinechest.presentation.base.viewmodel.VMActivity
import ru.crew4dev.medicinechest.presentation.base.viewmodel.VMFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

abstract class NavActivity<VM : BaseViewModel> : VMActivity<VM>() {

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(getFragmentsContainerId()) as? VMFragment<*>?

    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigator()
        lifecycle.addObserver(NavHolderLifecycle(navigator))
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed()
    }

    @IdRes
    abstract fun getFragmentsContainerId(): Int

    fun currentFragment() = currentFragment

    private fun initNavigator() {
        navigator = object : SupportAppNavigator(this, getFragmentsContainerId()) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction?
            ) {
                setFragmentAnimation(fragmentTransaction)
            }
        }
    }

    private fun setFragmentAnimation(fragmentTransaction: FragmentTransaction?) {
        fragmentTransaction ?: return

        fragmentTransaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
    }
}