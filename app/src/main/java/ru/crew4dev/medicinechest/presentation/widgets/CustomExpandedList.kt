package ru.crew4dev.medicinechest.presentation.widgets

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import kotlinx.android.synthetic.main.view_header_expanded_list.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs

class CustomExpandedList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val EMPTY_RES_VALUE = -1
        const val ANIMATE_DURATION_MS = 250L
        const val DOWN_ANGLE = 90f
        const val UP_ANGLE = -90f
    }

    private enum class State { COLLAPSED, EXPANDED }

    private var state: State = State.COLLAPSED

    private var expandAnimator: ValueAnimator? = null
    private var rotateAnimator: ValueAnimator? = null

    private var expandedViewHeight = 0
    private var measured = false

    init {
        inflateViewAsRoot(R.layout.view_header_expanded_list)

        isClickable = true
        clipChildren = false
        clipToPadding = false

        // Назначение параметров из layout
        obtainAttrs(attrs, R.styleable.CustomExpandedList) {
            setIcon(it.getResourceId(R.styleable.CustomExpandedList_img, EMPTY_RES_VALUE))
            setTitle(it.getString(R.styleable.CustomExpandedList_title))
            setInnerLayout(it.getResourceId(R.styleable.CustomExpandedList_listLayoutId, EMPTY_RES_VALUE))
            if (it.getBoolean(R.styleable.CustomExpandedList_isExpanded, false)) {
                state = State.EXPANDED; iv_expanded_list_arrow.rotation = UP_ANGLE; }
            else {
                state = State.COLLAPSED; iv_expanded_list_arrow.rotation = DOWN_ANGLE }
        }

        header_expanded_list_layout.setOnClickListener { trigger() }
    }

    /**
     * Определить высоту списка
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measured) return

        (expanded_list_frame.layoutParams as MarginLayoutParams).height = 0
        expandedViewHeight = expanded_list_frame.measuredHeight

        measured = true
        if (state == State.EXPANDED)
            expanded_list_frame.layoutParams.height = expandedViewHeight
    }

    /**
     * Освобождение ресурсов аниматоров при закрытии окна
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        listOfNotNull(expandAnimator, rotateAnimator).forEach {
            if (it.isRunning) it.cancel()
            it.removeAllUpdateListeners()
        }
    }

    private fun setIcon(@DrawableRes resId: Int) {
        if (resId == EMPTY_RES_VALUE) return
        iv_expanded_list_arrow.setImageResource(resId)
    }

    private fun setTitle(title: String?) {
        title ?: return
        tv_header_list_title.text = title
    }

    /**
     * Размещение списка
     */
    private fun setInnerLayout(@LayoutRes layout: Int) {
        if (layout != EMPTY_RES_VALUE) {
            LayoutInflater.from(context).inflate(layout, expanded_list_frame)
        }
    }

    /**
     * Переключатель состояния
     */
    private fun trigger() {
        state = when (state) {
            State.EXPANDED -> { repaintList(expandedViewHeight, 0); reverseArrow(UP_ANGLE, DOWN_ANGLE); State.COLLAPSED
            }
            State.COLLAPSED -> { repaintList(0, expandedViewHeight); reverseArrow(DOWN_ANGLE, UP_ANGLE); State.EXPANDED
            }
        }
    }

    /**
     * Перерисовка списка
     */
    private fun repaintList(start: Int, finish: Int) {

        expandAnimator = ValueAnimator.ofInt(start, finish).apply {
            addUpdateListener { animation ->
                expanded_list_frame.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            addListener(object : AnimatorListenerAdapter() {
            })
            duration = ANIMATE_DURATION_MS
            start()
        }
    }

    /**
     * Перерисовка стрелки
     */
    private fun reverseArrow(start: Float, end: Float) {
        rotateAnimator = ValueAnimator.ofFloat(start, end).apply {
            addUpdateListener { animation ->
                iv_expanded_list_arrow.rotation = animation.animatedValue as Float
            }
            duration = ANIMATE_DURATION_MS
            start()
        }
    }
}