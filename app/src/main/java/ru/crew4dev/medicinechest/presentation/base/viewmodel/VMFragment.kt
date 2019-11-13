package ru.crew4dev.medicinechest.presentation.base.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import ru.crew4dev.medicinechest.presentation.base.BaseFragment
import ru.crew4dev.medicinechest.presentation.base.error.ErrorMapper
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.CommandsLiveData
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.VMCommand
import java.util.concurrent.ConcurrentLinkedQueue

abstract class VMFragment<VM : BaseViewModel> : BaseFragment() {

    abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.commands.subscribeCommand {
            onCommandReceived(it)
        }
        viewModel.showError.subscribeCommand {
            baseActivity?.showError(ErrorMapper.map(it))
            Log.e("MRM error", it.message, it)
        }
        initViewModel()
    }

    abstract fun initViewModel()

    @CallSuper
    open fun onBackPressed() {
        viewModel.onBackPressed()
    }

    @CallSuper
    open fun onCommandReceived(command: VMCommand) {
        (baseActivity as? VMActivity<*>)?.onCommandReceived(command)
    }

    protected inline fun <reified T : ViewModel> Fragment.injectViewModel(crossinline inject: () -> T): T {
        return ViewModelProviders.of(this, viewModelFactory(inject))[T::class.java]
    }

    protected fun <T> LiveData<T?>.subscribe(action: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { data -> data?.let { action.invoke(it) } })
    }

    protected fun <T> CommandsLiveData<T>.subscribeCommand(action: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer<ConcurrentLinkedQueue<T>> { commands ->
            if (commands == null) {
                return@Observer
            }
            var command: T?
            do {
                command = commands.poll()
                if (command != null) {
                    action.invoke(command)
                }
            } while (command != null)
        })
    }

}