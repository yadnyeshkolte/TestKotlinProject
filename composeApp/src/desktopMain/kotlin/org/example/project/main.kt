package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Desktop Calculator",
        state = rememberWindowState(),
    ) {
        // Set fixed window size
        window.size = Dimension(400, 600)
        window.minimumSize = Dimension(400, 600)

        DesktopCalculator()
    }
}

@Composable
fun DesktopCalculator() {
    // App state
    var displayValue by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf("") }
    var isNewOperation by remember { mutableStateOf(true) }

    CalculatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Display area
                CalculatorDisplay(displayValue)

                // Buttons area with equal spacing
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // First row
                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton(
                            text = "C",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ) {
                            displayValue = "0"
                            firstNumber = "0"
                            operation = ""
                            isNewOperation = true
                        }
                        CalculatorButton(
                            text = "±",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ) {
                            displayValue = if (displayValue.startsWith("-")) {
                                displayValue.substring(1)
                            } else {
                                "-$displayValue"
                            }
                        }
                        CalculatorButton(
                            text = "%",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ) {
                            val number = displayValue.toDoubleOrNull() ?: 0.0
                            displayValue = formatResult(number / 100)
                        }
                        CalculatorButton(
                            text = "÷",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
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
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton(
                            text = "7",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("7", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "8",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("8", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "9",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("9", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "×",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
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
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton(
                            text = "4",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("4", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "5",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("5", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "6",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("6", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "-",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
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
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton(
                            text = "1",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("1", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "2",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("2", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "3",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("3", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = "+",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
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
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton(
                            text = "0",
                            modifier = Modifier.weight(2f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            updateDisplay("0", displayValue, isNewOperation) { newValue, newIsNew ->
                                displayValue = newValue
                                isNewOperation = newIsNew
                            }
                        }
                        CalculatorButton(
                            text = ".",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.surface
                        ) {
                            if (!displayValue.contains(".")) {
                                displayValue = if (isNewOperation) {
                                    isNewOperation = false
                                    "0."
                                } else {
                                    "$displayValue."
                                }
                            }
                        }
                        CalculatorButton(
                            text = "=",
                            modifier = Modifier.weight(1f),
                            backgroundColor = MaterialTheme.colors.secondary
                        ) {
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
}

@Composable
fun CalculatorDisplay(value: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = value,
                fontSize = 40.sp,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        elevation = ButtonDefaults.elevation(defaultElevation = 6.dp, pressedElevation = 10.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = if (backgroundColor == MaterialTheme.colors.surface)
                MaterialTheme.colors.onSurface
            else
                MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun CalculatorTheme(content: @Composable () -> Unit) {
    val colors = lightColors(
        primary = Color(0xFF6200EE),         // Purple for operation buttons
        primaryVariant = Color(0xFF3700B3),  // Dark purple for special functions
        secondary = Color(0xFF03DAC5),       // Teal for equals button
        background = Color(0xFFF5F5F5),      // Light gray background
        surface = Color(0xFFFFFFFF),         // White for number buttons and display
        onPrimary = Color.White,             // White text on primary buttons
        onSecondary = Color.Black,           // Black text on secondary buttons
        onBackground = Color.Black,          // Black text on background
        onSurface = Color.Black              // Black text on surface
    )

    MaterialTheme(
        colors = colors,
        content = content
    )
}

// Helper functions
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
    return if (value.isInfinite() || value.isNaN()) {
        "Error"
    } else if (value == value.toLong().toDouble()) {
        value.toLong().toString()
    } else {
        // Limit decimal places for readability
        val resultStr = value.toString()
        if (resultStr.length > 12) {
            "%.8f".format(value).trimEnd('0').trimEnd('.')
        } else {
            resultStr
        }
    }
}