package ru.crew4dev.medicinechest.presentation.widgets

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import kotlinx.android.synthetic.main.view_expand_card_layout.view.*
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot
import ru.crew4dev.medicinechest.presentation.base.isVisible
import ru.crew4dev.medicinechest.presentation.base.obtainAttrs

class ExpandCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val EMPTY_RES_VALUE = -1
        const val ANIMATE_DURATION_MS = 250L
        const val START_ANGLE = 90f
        const val FINISH_ANGLE = -90f
    }

    private enum class State { CLOSED, EXPANDED }

    private var state: State = State.CLOSED

    private var expandAnimator: ValueAnimator? = null
    private var rotateAnimator: ValueAnimator? = null

    private var expandedViewHeight = 0
    private var measured = false

    var onClick: (() -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.view_expand_card_layout)

        isClickable = true
        clipChildren = false
        clipToPadding = false

        obtainAttrs(attrs, R.styleable.ExpandCard) {
            setIcon(it.getResourceId(R.styleable.ExpandCard_image, EMPTY_RES_VALUE))
            setTitle(it.getString(R.styleable.ExpandCard_title))
            setInnerLayout(it.getResourceId(R.styleable.ExpandCard_innerLayoutId, EMPTY_RES_VALUE))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measured) return

        (expand_card_frame.layoutParams as MarginLayoutParams).height = 0
        expandedViewHeight = expand_card_frame.measuredHeight

        measured = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        listOfNotNull(expandAnimator, rotateAnimator).forEach {
            if (it.isRunning) it.cancel()
            it.removeAllUpdateListeners()
        }
    }

    private fun setIcon(@DrawableRes resId: Int) {
        if (resId == EMPTY_RES_VALUE) return
        expand_card_image.setImageResource(resId)
    }

    fun setTitle(title: String?) {
        title ?: return
        expand_card_title.text = title
    }

    private fun setInnerLayout(@LayoutRes layout: Int) {
        if (layout == EMPTY_RES_VALUE) {
            expand_card_layout.setOnClickListener { onClick?.invoke() }
            expand_card_arrow.isVisible = false
            return
        }

        LayoutInflater.from(context).inflate(layout, expand_card_frame)
        expand_card_layout.setOnClickListener { toggle(); onClick?.invoke() }
        expand_card_arrow.isVisible = true
    }

    private fun toggle() {
        when (state) {
            State.EXPANDED -> move(expandedViewHeight, 0)
            State.CLOSED -> move(0, expandedViewHeight)
        }
    }

    private fun move(start: Int, end: Int) {
        expandAnimator = ValueAnimator.ofInt(start, end).apply {
            addUpdateListener { animation ->
                expand_card_frame.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    state = if (end - start < 0) State.CLOSED else State.EXPANDED
                }
            })
            duration = ANIMATE_DURATION_MS
            start()
        }

        when (end - start < 0) {
            true -> reverseArrow(FINISH_ANGLE, START_ANGLE)
            false -> reverseArrow(START_ANGLE, FINISH_ANGLE)
        }
    }

    private fun reverseArrow(start: Float, end: Float) {
        rotateAnimator = ValueAnimator.ofFloat(start, end).apply {
            addUpdateListener { animation ->
                expand_card_arrow.rotation = animation.animatedValue as Float
            }
            duration = ANIMATE_DURATION_MS
            start()
        }
    }

}