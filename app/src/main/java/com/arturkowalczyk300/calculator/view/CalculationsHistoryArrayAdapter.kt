package com.arturkowalczyk300.calculator.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.arturkowalczyk300.calculator.R

class CalculationsHistoryArrayAdapter(context: Context, data: ArrayList<String>) :
    ArrayAdapter<String>(context, R.layout.item_calculations_history, data), View.OnClickListener {
    //TODO(add viewholder)

    private var data: ArrayList<String>
    private var ctxt: Context
    private var buttonDeleteAll: Button? = null
    private lateinit var callbackItemOnClick: (equation: String) -> Unit
    private lateinit var callbackDeleteButtonOnClick: (equation: String) -> Unit

    init {
        this.data = data
        this.ctxt = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (buttonDeleteAll == null) {
            val btn =
                (parent.parent as LinearLayout).findViewById<LinearLayout>(R.id.dialog_ll_buttons)
                    .findViewById<Button>(R.id.dialog_btnDeleteAll)
            buttonDeleteAll = btn
            changeButtonDeleteAllVisibility()
        }

        val equation: String? = getItem(position)

        val inflater = LayoutInflater.from(ctxt)
        val view = inflater.inflate(R.layout.item_calculations_history, parent, false)

        val textView = view.findViewById<TextView>(R.id.item_text)
        textView.text = data[position]

        val button = view.findViewById<Button>(R.id.item_button)
        button.setOnClickListener {
            val str = (it.parent as RelativeLayout).findViewById<TextView>(
                R.id.item_text
            ).text.toString()
            callbackDeleteButtonOnClick.invoke(str)
            data.remove(str)
            notifyDataSetChanged()
        }

        view.setOnClickListener(this)

        return view
    }

    private fun changeButtonDeleteAllVisibility() {
        buttonDeleteAll!!.visibility = when (data.size) {
            0 -> View.GONE
            else -> View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        if (callbackItemOnClick != null)
            callbackItemOnClick.invoke(v?.findViewById<TextView>(R.id.item_text)?.text.toString())
    }

    fun setItemOnClickListener(callback: (equation: String) -> Unit) {
        this.callbackItemOnClick = callback
    }

    fun setDeleteButtonOnClickListener(callback: (equation: String) -> Unit) {
        this.callbackDeleteButtonOnClick = callback
    }

    fun deleteAll() {
        data.removeAll { true }
        notifyDataSetChanged()
        changeButtonDeleteAllVisibility()
    }
}