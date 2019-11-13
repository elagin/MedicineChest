package ru.crew4dev.medicinechest.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.crew4dev.medicinechest.app.App
import ru.crew4dev.medicinechest.app.drawer.DrawerHolderFragment
import ru.crew4dev.medicinechest.app.utils.hideKeyboard
import ru.crew4dev.medicinechest.domain.exceptions.ConvertException
import ru.crew4dev.medicinechest.presentation.screens.main.MainActivity
import java.util.*

abstract class BaseFragment : Fragment() {

    abstract val layoutResource: Int

    private val singleTaskTimer = Timer()
    private var singleDelayedTask: TimerTask? = null

    val baseActivity: BaseActivity?
        get() = activity as BaseActivity?

    inline fun <reified T> getBaseActivity(): T {
        return activity as? T ?: throw ConvertException()
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
        initUi(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        updateDrawerState()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        singleTaskTimer.cancel()
    }

    abstract fun initUi(savedInstanceState: Bundle?)

    protected fun delay(duration: Long = App.SEARCH_DELAY_MILLISECONDS, action: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(duration)
            action.invoke()
        }
    }

    /****
     * добавить одно действие в очередь, предыдущее действие будет отменено если еще не успело выполниться
     */
    protected fun delaySingle(duration: Long = App.SEARCH_DELAY_MILLISECONDS, action: () -> Unit) {

        singleDelayedTask?.cancel()
        singleTaskTimer.purge()

        val task = object : TimerTask() {
            override fun run() {
                baseActivity?.runOnUiThread {
                    if (!isResumed) return@runOnUiThread

                    action.invoke()
                }
            }
        }

        singleDelayedTask = task
        singleTaskTimer.schedule(task, duration)
    }

    protected fun resizeLayoutForKeyboard() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                    or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    private fun updateDrawerState() {
        getBaseActivity<MainActivity>().isDrawerLocked(this !is DrawerHolderFragment)
    }

}