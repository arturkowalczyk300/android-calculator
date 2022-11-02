package com.arturkowalczyk300.calculator.view

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arturkowalczyk300.calculator.viewmodel.MainViewModel
import com.arturkowalczyk300.calculator.R
import com.arturkowalczyk300.calculator.model.CalculationEntity
import com.arturkowalczyk300.calculator.view.EditTextWithSelectionChangedListener.OnSelectionChangedListener
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    companion object {
        val DIALOG_CALCULATIONS_HISTORY_TAG = "CUSTOM_DIALOG_CALCULATIONS_HISTORY"
    }

    private var currentlySelectedNumberIndexRange = IntRange(-1, -1)
    private var span: BackgroundColorSpan? = null
    private var cursorPositionChangePending: Boolean = false
    private lateinit var viewModel: MainViewModel
    private lateinit var editTextExpression: EditTextWithSelectionChangedListener
    private var editTextExpressionCursorCurrentIndex by Delegates.observable(0) { property, oldValue, newValue ->

        //callbackCursorPositionChanged(oldValue, newValue)
    }
    private var editTextExpressionCursorLastNonZeroIndex = 0
    private lateinit var textViewResult: TextView
    private lateinit var textViewLabelEqual: TextView

    private var listOfHistoricalCalculations: List<CalculationEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextExpression = findViewById(R.id.editTextExpression)
        textViewResult = findViewById(R.id.tvResult)
        textViewLabelEqual = findViewById(R.id.tvLabelEqual)

        editTextExpression.showSoftInputOnFocus = false //disable popup keyboard on view select

        editTextExpression.onSelectionChangedListener = object : OnSelectionChangedListener {
            override fun onSelectionChanged(selStart: Int, selEnd: Int) {
                editTextExpressionCursorCurrentIndex = selEnd
                if (editTextExpressionCursorCurrentIndex > 0)
                    editTextExpressionCursorLastNonZeroIndex = editTextExpressionCursorCurrentIndex

                if (cursorPositionChangePending) {
                    cursorPositionChangePending = false
                    if (editTextExpressionCursorCurrentIndex <= editTextExpression.text.lastIndex)
                        highlightNumberAtSpecifiedCursorPosition(
                            editTextExpressionCursorCurrentIndex
                        )
                    else
                        editTextExpressionRemoveSpan()
                }
            }
        }
        editTextExpression.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            cursorPositionChangePending = true
            false //otherwise it is impossible to move cursor
        }

        viewModel = ViewModelProvider(this)[MainViewModel::
        class.java]

        //init database
        viewModel.initDatabase(applicationContext)

        //observe livedata
        viewModel.getAllCalculationHistoryEntities().observe(this, Observer {
            listOfHistoricalCalculations = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_options_menu_history -> {
                displayCalculationsHistoryDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun buttonOnClickListener(view: View) {
        view.startAnimation(clickAnimation())

        val lastElementIndex =
            if (editTextExpression.length() > 0) editTextExpression.length() else 0

        val currentIndex: Int =
            if (!editTextExpression.isCursorVisible) {
                lastElementIndex
            } else {
                if (editTextExpressionCursorCurrentIndex > 0)
                    editTextExpressionCursorCurrentIndex
                else
                    0
            }

        var moveCursorBackwardFlag = false
        var characterNotAddedFlag = false
        var expressionClearedFlag = false

        switchResultVisibility(false) //expression changed

        val tag: String = view.tag.toString()

        if (viewModel.isStringNumber(tag)) {
            viewModel.currentExpression.insert(currentIndex, tag)
        } else if (viewModel.isStringOperator(tag)) {
            val isPreviousCharacterOperator =
                if (viewModel.currentExpression.isNotEmpty() && currentIndex > 0)
                    (viewModel.isStringOperator(
                        viewModel.currentExpression[currentIndex - 1] //prevent entering two operators next to each other
                            .toString()
                    ))
                else {
                    false
                }

            val isNextCharacterOperator =
                if (viewModel.currentExpression.isNotEmpty() && ((currentIndex + 1) <= lastElementIndex))
                    (viewModel.isStringOperator(
                        viewModel.currentExpression[currentIndex] //prevent entering two operators next to each other
                            .toString()
                    )
                            )
                else {
                    false
                }

            if (tag == "-" //exception condition to make typing negative numbers possible
                || (!isPreviousCharacterOperator && !isNextCharacterOperator)
            ) {
                viewModel.currentExpression.insert(currentIndex, tag)
            } else
                characterNotAddedFlag = true
        } else if (tag == "DEL") {
            if (viewModel.currentExpression.isNotEmpty() && currentIndex > 0) {
                viewModel.currentExpression.deleteCharAt(currentIndex - 1)
                moveCursorBackwardFlag = true
            } else
                characterNotAddedFlag = true
        } else if (tag == "AC") {
            viewModel.currentExpression.clear()
            expressionClearedFlag = true
        } else if (tag == "=") {
            characterNotAddedFlag = true
            try {
                val exp = viewModel.currentExpression.toString()
                val date = Date()
                val result = viewModel.calculateResult(
                    exp,
                    date //current date
                )
                textViewResult.text = result.toString()

                viewModel.insertCalculationToHistory(exp, date, result)


            } catch (exc: Exception) {
                Toast.makeText(applicationContext, exc.toString(), Toast.LENGTH_LONG).show()
            }
            switchResultVisibility(true) //result ready
        } else if (tag == "+/-")
            invertSignOfCurrentlySelectedNumber(
                currentlySelectedNumberIndexRange.first,
                currentlySelectedNumberIndexRange.last
            )

        var cursorPosition = editTextExpression.selectionEnd
        editTextExpressionUpdate()
        if (editTextExpressionCursorCurrentIndex < editTextExpression.text.length - 2) {
            cursorPositionChangePending = true
        }

        if (!expressionClearedFlag) {
            if (!characterNotAddedFlag) {
                if (moveCursorBackwardFlag && cursorPosition > 0)
                    editTextExpression.setSelection(cursorPosition - 1) //move cursor to left
                else editTextExpression.setSelection(cursorPosition + 1) //move cursor to right
            } else
                editTextExpression.setSelection(cursorPosition) //restore previous cursor position
        }
    }

    private fun displayCalculationsHistoryDialog() {

        val builder: AlertDialog.Builder? =
            AlertDialog.Builder(this, R.style.custom_calculations_history_dialog_style)
        val view = layoutInflater.inflate(R.layout.custom_calculations_history_dialog, null)
            .apply {
                tag = DIALOG_CALCULATIONS_HISTORY_TAG
            }

        builder?.setView(view)

        val lv = view.findViewById<ListView>(R.id.dialog_lvCalculationsHistory)
        lv.adapter = CalculationsHistoryArrayAdapter(this, listOfHistoricalCalculations?.map {
            it.equation
        } as ArrayList<String>)

        val btnDeleteAll = view.findViewById<Button>(R.id.dialog_btnDeleteAll)
        btnDeleteAll.setOnClickListener {
            viewModel.deleteAllCalculationHistoryEntities()
            (lv.adapter as CalculationsHistoryArrayAdapter).deleteAll()
        }

        val dialog: AlertDialog? = builder?.create()

        (lv.adapter as CalculationsHistoryArrayAdapter).setItemOnClickListener { equation ->
            viewModel.currentExpression.clear()
            viewModel.currentExpression.append(equation)
            editTextExpressionUpdate()
            dialog?.dismiss()
        }
        (lv.adapter as CalculationsHistoryArrayAdapter).setDeleteButtonOnClickListener {
            viewModel.deleteCalculationHistoryEntity(it!!)
        }

        dialog?.show()
    }

    private fun editTextExpressionUpdate() {
        editTextExpression.setText(viewModel.currentExpression)
    }

    private fun switchResultVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        textViewResult.visibility = visibility
        textViewLabelEqual.visibility = visibility
    }

    private fun clickAnimation(): AlphaAnimation {
        val anim = AlphaAnimation(1.0f, 0.7f)
        anim.duration = 100
        return anim
    }

    private fun getAllNumbers(equation: String): List<Pair<String, IntRange>> {
        val listOfPairs = mutableListOf<Pair<String, IntRange>>()

        val regex = Regex("""(-?[\d.]*)[+-/*]?""")
        val match = regex.findAll(equation)

        match.forEach {
            listOfPairs.add(
                Pair(
                    it.groups[1]!!.value,
                    it.groups[1]!!.range
                )
            )
        }

        return listOfPairs
    }

    private fun highlightNumberAtSpecifiedCursorPosition(cursorPosition: Int) {
        val numbers = getAllNumbers(viewModel.currentExpression.toString())

        val found = numbers.find {
            currentlySelectedNumberIndexRange = it.second
            cursorPosition >= it.second.first && cursorPosition <= it.second.last
        }

        if (found != null)
            highlightSubstring(
                currentlySelectedNumberIndexRange.first,
                currentlySelectedNumberIndexRange.last + 1
            )
        else
            editTextExpressionRemoveSpan()
    }

    private fun highlightSubstring(startIndex: Int, endIndex: Int) {
        if (startIndex == -1 || endIndex == -1)
            return

        editTextExpressionSetSpan(startIndex, endIndex)
    }

    private fun editTextExpressionSetSpan(startIndex: Int, endIndex: Int) {
        if (span == null)
            span = BackgroundColorSpan(Color.argb(30, 0, 0, 255))

        editTextExpressionRemoveSpan()

        editTextExpression.text.setSpan(
            span!!,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }

    private fun editTextExpressionRemoveSpan() {
        if (span != null)
            editTextExpression.text.removeSpan(span) //remove previously applied span
    }

    private fun invertSignOfCurrentlySelectedNumber(startIndex: Int, endIndex: Int) {
        if (viewModel.currentExpression[startIndex] == '-') //negative number
        {
            viewModel.currentExpression =
                StringBuilder(viewModel.currentExpression.removeRange(startIndex, startIndex + 1))
        } else {
            viewModel.currentExpression.insert(startIndex, "-")
        }
        editTextExpressionUpdate()
    }
}