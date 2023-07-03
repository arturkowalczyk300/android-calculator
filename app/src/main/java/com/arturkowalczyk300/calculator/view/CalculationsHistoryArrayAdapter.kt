package com.arturkowalczyk300.calculator.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arturkowalczyk300.calculator.R

class CalculationsHistoryArrayAdapter(context: Context, data: ArrayList<String>) :
    ArrayAdapter<String>(context, R.layout.item_calculations_history, data), OnClickListener {
    private var data: ArrayList<String>
    private var ctx: Context
    private lateinit var buttonDeleteAll: Button
    private lateinit var callbackItemOnClick: (equation: String) -> Unit
    private lateinit var callbackDeleteButtonOnClick: (equation: String) -> Unit

    class CustomViewHolder(view: View) : ViewHolder(view) {
        lateinit var textView: TextView
        lateinit var button: Button

        fun bind(text: String, listener: OnClickListener) {
            textView.text = text
            button.setOnClickListener(listener)
        }
    }

    init {
        this.data = data
        this.ctx = context
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        @Suppress("CanBeVal")
        var viewHolder: CustomViewHolder?

        if (!(::buttonDeleteAll.isInitialized)) {
            val btn =
                (parent.parent as LinearLayout).findViewById<LinearLayout>(R.id.dialog_ll_buttons)
                    .findViewById<Button>(R.id.dialog_btnDeleteAll)
            buttonDeleteAll = btn
            changeButtonDeleteAllVisibility()
        }

        if (view == null) {
            val inflater = LayoutInflater.from(ctx)
            view = inflater.inflate(R.layout.item_calculations_history, parent, false)

            viewHolder = CustomViewHolder(view)
            view.tag = viewHolder

            view?.setOnClickListener(this)
        } else {
            viewHolder = view.tag as CustomViewHolder
        }

        view = view ?: throw IllegalStateException("Illegal state!")

        viewHolder.textView = view.findViewById(R.id.item_text)
        viewHolder.button = view.findViewById(R.id.item_button)

        viewHolder.bind(data[position]) {
            val equation = (it.parent as RelativeLayout).findViewById<TextView>(
                R.id.item_text
            ).text.toString()
            callbackDeleteButtonOnClick.invoke(equation)
            data.remove(equation)
            notifyDataSetChanged()
        }

        return view
    }

    private fun changeButtonDeleteAllVisibility() {
        buttonDeleteAll.visibility = when (data.size) {
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