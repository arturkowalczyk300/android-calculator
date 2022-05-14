package com.arturkowalczyk300.calculator

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.Integer.parseInt
import java.lang.NumberFormatException
import java.lang.StringBuilder
import kotlin.math.exp


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var textViewExpression: TextView
    private lateinit var textViewResult: TextView
    private lateinit var textViewLabelEqual: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewExpression = findViewById(R.id.tvExpression)
        textViewResult = findViewById(R.id.tvResult)
        textViewLabelEqual = findViewById(R.id.tvLabelEqual)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    fun buttonOnClickListener(view: View) {
        switchResultVisibility(false) //expression changed

        val tag: String = view.tag.toString()

        if (viewModel.isStringNumber(tag)) {
            viewModel.currentExpression.append(tag)
        } else if (viewModel.isStringOperator(tag)) {
            val isPreviousCharacterOperator =
                if (viewModel.currentExpression.isNotEmpty())
                    viewModel.isStringOperator(
                        viewModel.currentExpression[viewModel.currentExpression.length - 1]
                            .toString()
                    )
                else
                    false
            if (tag == "-" //exception condition to make typing negative numbers possible
                || !isPreviousCharacterOperator
            ) {
                viewModel.currentExpression.append(tag)
            }
        } else if (tag == "DEL") {
            if (viewModel.currentExpression.isNotEmpty())
                viewModel.currentExpression
                    .setLength((viewModel.currentExpression.length - 1)) //delete last character
        } else if (tag == "AC") {
            viewModel.currentExpression.clear()
        } else if (tag == "=") {
            try {
                textViewResult.text =
                    viewModel.calculateResult(viewModel.currentExpression.toString())
                        .toString()
            } catch (exc: Exception) {
                Toast.makeText(applicationContext, exc.toString(), Toast.LENGTH_LONG).show()
            }
            switchResultVisibility(true) //result ready
        }


        textViewExpression.text = viewModel.currentExpression
    }


    private fun switchResultVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        textViewResult.visibility = visibility
        textViewLabelEqual.visibility = visibility
    }
}