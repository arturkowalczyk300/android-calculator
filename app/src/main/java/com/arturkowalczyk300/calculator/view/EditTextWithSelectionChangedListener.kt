package com.arturkowalczyk300.calculator.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

@SuppressLint("AppCompatCustomView")
class EditTextWithSelectionChangedListener : EditText {
    interface OnSelectionChangedListener{
        fun onSelectionChanged(selStart:Int, selEnd: Int)
    }

    var onSelectionChangedListener: OnSelectionChangedListener? = null
    
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        onSelectionChangedListener?.onSelectionChanged(selStart, selEnd)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}