package ru.crew4dev.medicinechest.presentation.screens.main

import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.crew4dev.medicinechest.app.drawer.DrawerItem
import ru.crew4dev.medicinechest.app.drawer.DrawerViewModel
import ru.crew4dev.medicinechest.app.navigation.Screens
//import ru.crew4dev.medicinechest.domain.model.client.photo.McbPassportPackage
//import ru.crew4dev.medicinechest.domain.model.login.UserData
import ru.crew4dev.medicinechest.domain.usecases.AppUseCase
import ru.crew4dev.medicinechest.domain.usecases.PushUseCase
import ru.crew4dev.medicinechest.presentation.base.viewmodel.onNext
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModelMainViewModel @Inject constructor(
    private val router: Router//,
    //private val appUseCase: AppUseCase,
    //private val pushUseCase: PushUseCase
) : DrawerViewModel() {
    override fun onBackPressed() = router.finishChain()

    override fun onDrawerItemPressed(drawerItem: DrawerItem) {
        when (drawerItem) {
            DrawerItem.MENU -> openMenuScreen()
            //DrawerItem.HISTORY -> openHistoryScreen()
            //DrawerItem.STATISTICS -> openStatisticsScreen()
            //DrawerItem.SIGNER_OPS -> openSignerOpsScreen()
            //DrawerItem.LOGOUT -> onLogout()
        }
    }

    fun onAfterInit() {
        //openSplashScreen()
        startApp()
    }
/*
    fun onLogin() {
        safeSubscribe {
            appUseCase.signIn().subscribe(
                {
                    handleAuthData(it)
                    openMenuScreen()
                },
                ::handleError
            )
        }
    }

    fun onLogout() {
        safeSubscribe {
            appUseCase.signOut().subscribe(
                { openLoginScreen(true) },
                ::handleError
            )
        }
    }
*/
    fun onNewIntent(bundle: Bundle?) {
        bundle ?: return

        val bundleMap = HashMap<String, String>()
        bundle.keySet().forEach { key ->
            val value = bundle.get(key)?.toString()
            bundleMap[key] = value ?: ""
        }
/*
        val afterPushScreen = pushUseCase.handlePushPayload(bundleMap) ?: return
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            router.navigateTo(afterPushScreen)
        }
 */
    }

    private fun startApp() {
        safeSubscribe {
            appUseCase.startApp()
                .subscribe(
                    {
                        openMenuScreen()
//                        when (it) {
//                            is UserData.AuthData -> {
//                                handleAuthData(it)
//                                openMenuScreen()
//                            }
//                            UserData.WithoutAuth -> openLoginScreen(false)
//                        }
                    },
                    ::handleError
                )
        }
    }
/*
    private fun handleAuthData(authData: UserData.AuthData) {
        managerName.onNext("${authData.surname} ${authData.name}")
        val s = if (authData.siebelId == 0L) "" else authData.siebelId.toString()
        managerDsa.onNext(s)
    }
*/
    private fun openSplashScreen() = router.newRootScreen(Screens.SplashScreen)

    //todo Вернуть обратно после окончания отладки
    //private fun openLoginScreen(afterLogout: Boolean) = router.newRootScreen(Screens.LoginPhoneScreen(afterLogout))
    //private fun openLoginScreen(afterLogout: Boolean) = router.newRootScreen(Screens.CameraScreen(McbPassportPackage(3)))
    //private fun openLoginScreen(afterLogout: Boolean) = router.newRootScreen(Screens.MenuScreen())

    private fun openMenuScreen() = router.newRootScreen(Screens.MenuScreen())
/*
    private fun openHistoryScreen() = router.newRootScreen(Screens.HistoryScreen)

    private fun openStatisticsScreen() = router.newRootScreen(Screens.StatisticsScreen)

    private fun openSignerOpsScreen() = router.newRootScreen(Screens.SignerOpsDrawerScreen)
*/
}