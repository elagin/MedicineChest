package ru.crew4dev.medicinechest.presentation.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.app.App
import ru.crew4dev.medicinechest.app.utils.hideKeyboard

abstract class BaseFullScreenDialog : DialogFragment() {
    companion object {
        const val NAME = "NAME"
    }

    abstract val layoutResource: Int
    abstract fun initUi(view: View, savedInstanceState: Bundle?)

    private val name by lazy { arguments?.getString(NAME) }
    var onClosePressed: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppFullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view, savedInstanceState)
    }

    fun show(hostFragment: Fragment) {
        with(hostFragment) {
            val oldDialog = childFragmentManager.findFragmentByTag(name)
            if (oldDialog != null) return

            show(childFragmentManager, name)
        }
    }

    fun isShowing(): Boolean {
        return dialog != null && dialog.isShowing && !isRemoving
    }

    protected fun delay(duration: Long = App.SEARCH_DELAY_MILLISECONDS, action: () -> Unit): Job {
        return GlobalScope.launch {
            kotlinx.coroutines.delay(duration)
            action.invoke()
        }
    }

    protected fun close() {
        onClosePressed?.invoke()
        hideKeyboard(activity)
        dismiss()
    }
}