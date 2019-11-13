package ru.crew4dev.medicinechest.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.drawer.DrawerActivity
import ru.crew4dev.medicinechest.app.drawer.DrawerItem
import ru.crew4dev.medicinechest.app.drawer.DrawerItemLayout
import ru.crew4dev.medicinechest.app.push.FirebasePushPayload
import ru.crew4dev.medicinechest.di.DI
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.VMCommand
import ru.crew4dev.medicinechest.presentation.screens.login.Login
import ru.crew4dev.medicinechest.presentation.screens.login.Logout

class MainActivity : DrawerActivity<MainViewModel>() {
    companion object {
        fun getOpenAppIntent(appContext: Context, payload: FirebasePushPayload): Intent {
            return Intent(appContext, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtras(Bundle().apply {
                    payload.entries.forEach {
                        putString(it.key, it.value)
                    }
                })
            }
        }
    }

    override val layoutResource: Int = R.layout.activity_main

    override val viewModel: MainViewModel by lazy { injectViewModel { DI.app.mainViewModel() } }

    override fun getFragmentsContainerId(): Int = R.id.main_container

    override fun getDrawerLayout(): DrawerLayout = main_drawer

    override fun getManagerNameView(): TextView = drawer_manager_name

    override fun getManagerDsaView(): TextView = drawer_manager_dsa

    override fun mapDrawerItemLayout(drawerItem: DrawerItem): DrawerItemLayout {
        return when (drawerItem) {
            DrawerItem.MENU -> drawer_item_sections
            DrawerItem.HISTORY -> drawer_item_history
            DrawerItem.STATISTICS -> drawer_item_statistics
            DrawerItem.SIGNER_OPS -> drawer_item_signer_ops
            DrawerItem.LOGOUT -> drawer_item_logout
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //при открытии пуша в убитом приложении
        viewModel.onNewIntent(intent?.extras)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //при открытии пуша в запущенном приложении
        viewModel.onNewIntent(intent?.extras)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.onAfterInit()
    }

    override fun onCommandReceived(command: VMCommand) {
        super.onCommandReceived(command)
        when (command) {
            is Login -> viewModel.onLogin()
            is Logout -> viewModel.onLogout()
        }
    }
}
