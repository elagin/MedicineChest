package ru.crew4dev.medicinechest.presentation.base.viewmodel

import androidx.lifecycle.*

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }
}

fun <T> MutableLiveData<T>.onNext(data: T) {
    this.value = data
}

fun <T> MutableLiveData<T>.get(): T? {
    return this.value
}
