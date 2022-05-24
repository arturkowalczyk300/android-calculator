package com.arturkowalczyk300.calculator

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.Toast

@SuppressLint("AppCompatCustomView")
class EditTextWithSelectionChangedListener : EditText {
    public interface OnSelectionChangedListener{
        fun onSelectionChanged(selStart:Int, selEnd: Int);
    }

    var onSelectionChangedListener: OnSelectionChangedListener? = null
    
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        onSelectionChangedListener?.onSelectionChanged(selStart, selEnd)
    }

}