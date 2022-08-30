package com.arturkowalczyk300.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arturkowalczyk300.calculator.viewmodel.MainViewModel
import com.arturkowalczyk300.calculator.R
import com.arturkowalczyk300.calculator.view.EditTextWithSelectionChangedListener.OnSelectionChangedListener
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var editTextExpression: EditTextWithSelectionChangedListener
    private var editTextExpressionCursorCurrentIndex = 0
    private var editTextExpressionCursorLastNonZeroIndex = 0
    private lateinit var textViewResult: TextView
    private lateinit var textViewLabelEqual: TextView


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
            }
        }

        viewModel = ViewModelProvider(this)[MainViewModel::
        class.java]

        //init database
        viewModel.initDatabase(applicationContext)
    }

    fun buttonOnClickListener(view: View) {
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
                textViewResult.text =
                    viewModel.calculateResult(viewModel.currentExpression.toString())
                        .toString()
            } catch (exc: Exception) {
                Toast.makeText(applicationContext, exc.toString(), Toast.LENGTH_LONG).show()
            }
            switchResultVisibility(true) //result ready
        }

        val cursorPosition = editTextExpression.selectionEnd
        editTextExpression.setText(viewModel.currentExpression)

        if (!expressionClearedFlag) {
            if (!characterNotAddedFlag) {
                if (moveCursorBackwardFlag && cursorPosition > 0)
                    editTextExpression.setSelection(cursorPosition - 1) //move cursor to left
                else editTextExpression.setSelection(cursorPosition + 1) //move cursor to right
            } else
                editTextExpression.setSelection(cursorPosition) //restore previous cursor position
        }
    }


    private fun switchResultVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        textViewResult.visibility = visibility
        textViewLabelEqual.visibility = visibility
    }
}