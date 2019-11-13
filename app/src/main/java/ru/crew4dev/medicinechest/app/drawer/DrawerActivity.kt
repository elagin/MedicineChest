package ru.crew4dev.medicinechest.app.drawer

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.navigation.NavActivity

abstract class DrawerActivity<VM : DrawerViewModel> : NavActivity<VM>() {

    private lateinit var toggle: ActionBarDrawerToggle
    private var currentDrawerItemLayout: DrawerItemLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDrawer()
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.managerName.subscribe { getManagerNameView().text = it }
        viewModel.managerDsa.subscribe { getManagerDsaView().text = getString(R.string.drawer_manager_dsa) + it }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    abstract fun getDrawerLayout(): DrawerLayout

    abstract fun mapDrawerItemLayout(drawerItem: DrawerItem): DrawerItemLayout

    abstract fun getManagerNameView(): TextView

    abstract fun getManagerDsaView(): TextView

    fun isDrawerLocked(isLocked: Boolean) = getDrawerLayout().setDrawerLockMode(
        if (isLocked) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED
    )

    fun isDrawerOpened(isOpened: Boolean) {
        getDrawerLayout().run { if (isOpened) openDrawer(GravityCompat.START) else closeDrawer(GravityCompat.START) }
    }

    fun updateSelectedDrawerItem(drawerItem: DrawerItem) {
        if (drawerItem == DrawerItem.LOGOUT) return

        val drawerItemLayout = mapDrawerItemLayout(drawerItem)

        currentDrawerItemLayout?.isCurrent = false
        drawerItemLayout.isCurrent = true
        currentDrawerItemLayout = drawerItemLayout

        isDrawerOpened(false)
    }

    private fun initDrawer() {
        toggle = ActionBarDrawerToggle(this, getDrawerLayout(), R.string.drawer_open, R.string.drawer_close)
        getDrawerLayout().addDrawerListener(toggle)
        initDrawerItems()
        updateSelectedDrawerItem(DrawerItem.values().first())
    }

    private fun initDrawerItems() {
        DrawerItem.values().forEach { drawerItem ->
            mapDrawerItemLayout(drawerItem).setOnClickListener { onDrawerItemPressed(drawerItem) }
        }
    }

    private fun onDrawerItemPressed(drawerItem: DrawerItem) {
        when (drawerItem) {
            DrawerItem.LOGOUT -> isDrawerOpened(false)
            else -> updateSelectedDrawerItem(drawerItem)
        }
        viewModel.onDrawerItemPressed(drawerItem)
    }
}