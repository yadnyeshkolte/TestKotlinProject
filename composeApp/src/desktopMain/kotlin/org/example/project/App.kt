package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    MaterialTheme {
        var displayValue by remember { mutableStateOf("0") }
        var firstNumber by remember { mutableStateOf("0") }
        var operation by remember { mutableStateOf("") }
        var isNewOperation by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display
            Surface(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = displayValue,
                        fontSize = 36.sp,
                        textAlign = TextAlign.End
                    )
                }
            }

            // Calculator buttons
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // First row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalcButton("C", Modifier.weight(1f)) {
                        displayValue = "0"
                        firstNumber = "0"
                        operation = ""
                        isNewOperation = true
                    }
                    CalcButton("±", Modifier.weight(1f)) {
                        displayValue = if (displayValue.startsWith("-")) {
                            displayValue.substring(1)
                        } else {
                            "-$displayValue"
                        }
                    }
                    CalcButton("%", Modifier.weight(1f)) {
                        val number = displayValue.toDoubleOrNull() ?: 0.0
                        displayValue = (number / 100).toString()
                    }
                    OperatorButton("÷", Modifier.weight(1f)) {
                        handleOperation("/", displayValue, firstNumber, operation, isNewOperation) { newDisplay, newFirst, newOp, newIsNew ->
                            displayValue = newDisplay
                            firstNumber = newFirst
                            operation = newOp
                            isNewOperation = newIsNew
                        }
                    }
                }

                // Second row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NumberButton("7", Modifier.weight(1f)) {
                        updateDisplay("7", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton("8", Modifier.weight(1f)) {
                        updateDisplay("8", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton("9", Modifier.weight(1f)) {
                        updateDisplay("9", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    OperatorButton("×", Modifier.weight(1f)) {
                        handleOperation("*", displayValue, firstNumber, operation, isNewOperation) { newDisplay, newFirst, newOp, newIsNew ->
                            displayValue = newDisplay
                            firstNumber = newFirst
                            operation = newOp
                            isNewOperation = newIsNew
                        }
                    }
                }

                // Third row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NumberButton("4", Modifier.weight(1f)) {
                        updateDisplay("4", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton("5", Modifier.weight(1f)) {
                        updateDisplay("5", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton("6", Modifier.weight(1f)) {
                        updateDisplay("6", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    OperatorButton("-", Modifier.weight(1f)) {
                        handleOperation("-", displayValue, firstNumber, operation, isNewOperation) { newDisplay, newFirst, newOp, newIsNew ->
                            displayValue = newDisplay
                            firstNumber = newFirst
                            operation = newOp
                            isNewOperation = newIsNew
                        }
                    }
                }

                // Fourth row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NumberButton("1", Modifier.weight(1f)) {
                        updateDisplay("1", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton("2", Modifier.weight(1f)) {
                        updateDisplay("2", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton("3", Modifier.weight(1f)) {
                        updateDisplay("3", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    OperatorButton("+", Modifier.weight(1f)) {
                        handleOperation("+", displayValue, firstNumber, operation, isNewOperation) { newDisplay, newFirst, newOp, newIsNew ->
                            displayValue = newDisplay
                            firstNumber = newFirst
                            operation = newOp
                            isNewOperation = newIsNew
                        }
                    }
                }

                // Fifth row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NumberButton("0", Modifier.weight(2f)) {
                        updateDisplay("0", displayValue, isNewOperation) { newValue, newIsNew ->
                            displayValue = newValue
                            isNewOperation = newIsNew
                        }
                    }
                    NumberButton(".", Modifier.weight(1f)) {
                        if (!displayValue.contains(".")) {
                            displayValue = if (isNewOperation) {
                                isNewOperation = false
                                "0."
                            } else {
                                "$displayValue."
                            }
                        }
                    }
                    EqualsButton("=", Modifier.weight(1f)) {
                        if (operation.isNotEmpty()) {
                            val first = firstNumber.toDoubleOrNull() ?: 0.0
                            val second = displayValue.toDoubleOrNull() ?: 0.0
                            val result = when (operation) {
                                "+" -> first + second
                                "-" -> first - second
                                "*" -> first * second
                                "/" -> if (second != 0.0) first / second else Double.POSITIVE_INFINITY
                                else -> second
                            }
                            displayValue = formatResult(result)
                            firstNumber = "0"
                            operation = ""
                            isNewOperation = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumberButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface
        )
    ) {
        Text(text, fontSize = 20.sp)
    }
}

@Composable
fun OperatorButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        Text(text, fontSize = 20.sp)
    }
}

@Composable
fun CalcButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.7f)
        )
    ) {
        Text(text, fontSize = 20.sp)
    }
}

@Composable
fun EqualsButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        Text(text, fontSize = 20.sp)
    }
}

private fun updateDisplay(
    digit: String,
    currentDisplay: String,
    isNewOp: Boolean,
    updateState: (String, Boolean) -> Unit
) {
    if (isNewOp) {
        updateState(digit, false)
    } else if (currentDisplay == "0") {
        updateState(digit, false)
    } else {
        updateState(currentDisplay + digit, false)
    }
}

private fun handleOperation(
    op: String,
    currentDisplay: String,
    firstNum: String,
    currentOp: String,
    isNewOp: Boolean,
    updateState: (String, String, String, Boolean) -> Unit
) {
    if (currentOp.isEmpty()) {
        // Just store the first number and operation
        updateState(currentDisplay, currentDisplay, op, true)
    } else if (!isNewOp) {
        // Calculate the result of the previous operation
        val first = firstNum.toDoubleOrNull() ?: 0.0
        val second = currentDisplay.toDoubleOrNull() ?: 0.0
        val result = when (currentOp) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> if (second != 0.0) first / second else Double.POSITIVE_INFINITY
            else -> second
        }
        val formattedResult = formatResult(result)
        updateState(formattedResult, formattedResult, op, true)
    } else {
        // Just update the operation
        updateState(currentDisplay, firstNum, op, true)
    }
}

private fun formatResult(value: Double): String {
    return if (value == value.toLong().toDouble()) {
        value.toLong().toString()
    } else {
        value.toString()
    }
}