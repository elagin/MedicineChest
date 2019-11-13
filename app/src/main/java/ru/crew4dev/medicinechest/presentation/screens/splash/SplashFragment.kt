package ru.crew4dev.medicinechest.presentation.screens.splash

import android.os.Bundle
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.utils.hideKeyboard
import ru.crew4dev.medicinechest.presentation.base.BaseFragment

class SplashFragment : BaseFragment() {
    override val layoutResource = R.layout.fragment_splash

    override fun initUi(savedInstanceState: Bundle?) {
        hideKeyboard(baseActivity)
    }
}