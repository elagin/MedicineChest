package ru.crew4dev.medicinechest.presentation.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.crew4dev.medicinechest.presentation.base.BaseActivity
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.CommandsLiveData
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.VMCommand
import java.util.concurrent.ConcurrentLinkedQueue

abstract class VMActivity<VM : BaseViewModel> : BaseActivity() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    @CallSuper
    open fun onCommandReceived(command: VMCommand) {}

    @CallSuper
    protected open fun initViewModel() {
        viewModel.commands.subscribeCommand {
            onCommandReceived(it)
        }
    }

    protected inline fun <reified T : BaseViewModel> injectViewModel(crossinline inject: () -> T): T {
        return ViewModelProviders.of(this, viewModelFactory(inject))[T::class.java]
    }

    protected fun <T> LiveData<T?>.subscribe(action: (T) -> Unit) {
        this.observe(this@VMActivity, Observer { data -> data?.let { action.invoke(it) } })
    }

    private fun <T> CommandsLiveData<T>.subscribeCommand(action: (T) -> Unit) {
        this.observe(this@VMActivity, Observer<ConcurrentLinkedQueue<T>> { commands ->
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