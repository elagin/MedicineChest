package ru.crew4dev.medicinechest.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.presentation.base.inflateViewAsRoot

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr)  {

    init {
        inflateViewAsRoot(R.layout.view_loading)
    }

}