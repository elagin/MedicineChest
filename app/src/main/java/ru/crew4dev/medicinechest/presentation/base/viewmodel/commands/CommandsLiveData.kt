package ru.crew4dev.medicinechest.presentation.base.viewmodel.commands

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ConcurrentLinkedQueue

class CommandsLiveData<T> : MutableLiveData<ConcurrentLinkedQueue<T>>() {

    fun onNext(value: T) {
        var commands = getValue()
        if (commands == null) {
            commands = ConcurrentLinkedQueue()
        }
        commands.add(value)
        setValue(commands)
    }
}