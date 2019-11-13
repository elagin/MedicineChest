package ru.crew4dev.medicinechest.presentation.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
//import ru.crew4dev.medicinechest.domain.exceptions.AuthException
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.CommandsLiveData
import ru.crew4dev.medicinechest.presentation.base.viewmodel.commands.VMCommand
//import ru.crew4dev.medicinechest.presentation.screens.login.Logout

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val commands = CommandsLiveData<VMCommand>()
    val showProgress = MutableLiveData<Boolean>()
    val showError = CommandsLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        clearAllObservables()
    }

    abstract fun onBackPressed()

    protected fun safeSubscribe(block: () -> Disposable) {
        compositeDisposable.add(block.invoke())
    }

    protected fun showProgress(show: Boolean) {
        showProgress.onNext(show)
    }

    protected fun handleError(th: Throwable) = handleError(th) { showError.onNext(th) }

    protected fun handleError(th: Throwable, handler: ((Throwable) -> (Unit))) {
        when (th) {
//            is AuthException -> {
//                commands.onNext(Logout)
//                handler.invoke(th)
//            }
            else -> handler.invoke(th)
        }
    }

    private fun clearAllObservables() {
        compositeDisposable.clear()
    }

}