package com.example.basiccalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.etInput)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { numberPressed((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { operatorPressed("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { operatorPressed("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { operatorPressed("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorPressed("/") }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDel).setOnClickListener { deleteLast() }
    }

    private fun numberPressed(value: String) {
        if (etInput.text.toString() == "0") etInput.setText("")
        currentInput += value
        etInput.setText(currentInput)
    }

    private fun operatorPressed(op: String) {
        if (currentInput.isEmpty()) return
        firstNumber = currentInput.toDouble()
        operator = op
        currentInput = ""
    }

    private fun calculateResult() {
        if (currentInput.isEmpty() || operator.isEmpty()) return

        val secondNumber = currentInput.toDouble()
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> {
                if (secondNumber == 0.0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                } else firstNumber / secondNumber
            }
            else -> 0.0
        }

        etInput.setText(result.toString())
        currentInput = result.toString()
        operator = ""
    }

    private fun clearAll() {
        currentInput = ""
        operator = ""
        firstNumber = 0.0
        etInput.setText("0")
    }

    private fun deleteLast() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            etInput.setText(if (currentInput.isEmpty()) "0" else currentInput)
        }
    }
}
