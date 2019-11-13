package ru.crew4dev.medicinechest.app.drawer

import androidx.lifecycle.MutableLiveData
import ru.crew4dev.medicinechest.presentation.base.viewmodel.BaseViewModel

abstract class DrawerViewModel : BaseViewModel() {
    val managerName = MutableLiveData<String>()
    val managerDsa = MutableLiveData<String>()
    abstract fun onDrawerItemPressed(drawerItem: DrawerItem)
}