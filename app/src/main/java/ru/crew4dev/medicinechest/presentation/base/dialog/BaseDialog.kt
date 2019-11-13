package ru.crew4dev.medicinechest.presentation.base.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.dialog_base.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.isInvisible
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.setIcon

open class BaseDialog : DialogFragment() {
    companion object {
        private const val NAME = "NAME"
        private const val TITLE = "TITLE"
        private const val ICON = "ICON"
        private const val TEXT = "TEXT"
        private const val POSITIVE = "POSITIVE"
        private const val NEGATIVE = "NEGATIVE"

        fun newInstance(
            name: String = "321",
            @StringRes title: Int? = null,
            @DrawableRes icon: Int? = null,
            @StringRes text: Int? = null,
            @StringRes positive: Int? = null,
            @StringRes negative: Int? = null
        ): BaseDialog {
            return BaseDialog().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                    title?.let { putInt(TITLE, it) }
                    icon?.let { putInt(ICON, it) }
                    text?.let { putInt(TEXT, it) }
                    positive?.let { putInt(POSITIVE, it) }
                    negative?.let { putInt(NEGATIVE, it) }
                }
            }
        }
    }

    private val name by lazy { arguments?.getString(NAME) }
    private val title by lazy { arguments?.getInt(TITLE) }
    private val icon by lazy { arguments?.getInt(ICON) }
    private val text by lazy { arguments?.getInt(TEXT) }
    private val positive by lazy { arguments?.getInt(POSITIVE) }
    private val negative by lazy { arguments?.getInt(NEGATIVE) }

    var onButtonClicked: ((DialogButton) -> Unit)? = null
    var onDialogClosed: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(title)
        setIcon(icon)
        setText(text)
        setNegative(negative)
        setPositive(positive)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onDialogClosed?.invoke()
    }

    fun show(hostFragment: Fragment) {
        with(hostFragment) {
            val oldDialog = childFragmentManager.findFragmentByTag(name)
            if (oldDialog != null) return

            show(childFragmentManager, name)
        }
    }

    fun show(hostActivity: AppCompatActivity) {
        with(hostActivity) {
            val oldDialog = supportFragmentManager.findFragmentByTag(name)
            if (oldDialog != null) return

            show(supportFragmentManager, name)
        }
    }

    private fun setTitle(textResId: Int?) {
        dialog_title.isVisible = !resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_title.text = getString(textResId!!)
    }

    private fun setIcon(iconResId: Int?) {
        dialog_icon.isVisible = !resIsNull(iconResId)
        if (resIsNull(iconResId)) return

        dialog_icon.setIcon(iconResId!!)
    }

    private fun setText(textResId: Int?) {
        dialog_text.isVisible = !resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_text.text = getString(textResId!!)
    }

    private fun setNegative(textResId: Int?) {
        dialog_negative.isInvisible = resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_negative.apply {
            text = getString(textResId!!)
            setOnClickListener {
                close()
                onButtonClicked?.invoke(DialogButton.Negative)
            }
        }
    }

    private fun setPositive(textResId: Int?) {
        dialog_positive.isVisible = !resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_positive.apply {
            text = getString(textResId!!)
            setOnClickListener {
                close()
                onButtonClicked?.invoke(DialogButton.Positive)
            }
        }

    }

    private fun close() {
        fragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitAllowingStateLoss()
    }

    private fun resIsNull(resId: Int?) = resId == null || resId == 0

}