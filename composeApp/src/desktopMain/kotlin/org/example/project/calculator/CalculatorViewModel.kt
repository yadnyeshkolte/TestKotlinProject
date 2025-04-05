package org.example.project.calculator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.pow
import kotlin.math.sqrt

enum class Operation {
    NONE, ADD, SUBTRACT, MULTIPLY, DIVIDE
}

data class CalculatorState(
    val currentInput: String = "0",
    val expression: String = "",
    val operation: Operation = Operation.NONE,
    val firstOperand: Double = 0.0,
    val isNewInput: Boolean = true,
    val memoryValue: Double = 0.0
)

class CalculatorViewModel {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun appendDigit(digit: Int) {
        _state.update { currentState ->
            if (currentState.isNewInput) {
                currentState.copy(
                    currentInput = digit.toString(),
                    isNewInput = false
                )
            } else {
                // Prevent leading zeros and limit input length
                if (currentState.currentInput == "0" && digit == 0) {
                    currentState
                } else if (currentState.currentInput == "0") {
                    currentState.copy(currentInput = digit.toString())
                } else if (currentState.currentInput.length < 12) {
                    currentState.copy(currentInput = currentState.currentInput + digit)
                } else {
                    currentState
                }
            }
        }
    }

    fun appendDecimal() {
        _state.update { currentState ->
            if (currentState.isNewInput) {
                currentState.copy(
                    currentInput = "0.",
                    isNewInput = false
                )
            } else if (!currentState.currentInput.contains(".")) {
                currentState.copy(currentInput = currentState.currentInput + ".")
            } else {
                currentState
            }
        }
    }

    fun toggleSign() {
        _state.update { currentState ->
            val current = currentState.currentInput
            if (current == "0") {
                currentState
            } else if (current.startsWith("-")) {
                currentState.copy(currentInput = current.substring(1))
            } else {
                currentState.copy(currentInput = "-$current")
            }
        }
    }

    fun setOperation(op: Operation) {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0

        if (currentState.operation != Operation.NONE && !currentState.isNewInput) {
            // Chain operations
            val result = performOperation(
                currentState.firstOperand,
                currentValue,
                currentState.operation
            )
            _state.update {
                it.copy(
                    firstOperand = result,
                    operation = op,
                    currentInput = formatResult(result),
                    expression = formatResult(result) + " " + getOperationSymbol(op) + " ",
                    isNewInput = true
                )
            }
        } else {
            _state.update {
                it.copy(
                    firstOperand = currentValue,
                    operation = op,
                    expression = currentState.currentInput + " " + getOperationSymbol(op) + " ",
                    isNewInput = true
                )
            }
        }
    }

    fun calculate() {
        val currentState = _state.value
        if (currentState.operation == Operation.NONE) return

        val secondOperand = currentState.currentInput.toDoubleOrNull() ?: 0.0
        val result = performOperation(
            currentState.firstOperand,
            secondOperand,
            currentState.operation
        )

        _state.update {
            it.copy(
                currentInput = formatResult(result),
                expression = "",
                operation = Operation.NONE,
                firstOperand = 0.0,
                isNewInput = true
            )
        }
    }

    fun clear() {
        _state.update {
            CalculatorState()
        }
    }

    fun clearEntry() {
        _state.update {
            it.copy(currentInput = "0", isNewInput = true)
        }
    }

    fun backspace() {
        _state.update { currentState ->
            if (currentState.isNewInput || currentState.currentInput == "0" || currentState.currentInput.length == 1) {
                currentState.copy(currentInput = "0", isNewInput = true)
            } else {
                currentState.copy(
                    currentInput = currentState.currentInput.dropLast(1)
                )
            }
        }
    }

    fun calculateSquareRoot() {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0

        if (currentValue < 0) {
            // Handle invalid input for square root
            _state.update {
                it.copy(
                    currentInput = "Error",
                    isNewInput = true
                )
            }
            return
        }

        val result = sqrt(currentValue)

        _state.update {
            it.copy(
                currentInput = formatResult(result),
                isNewInput = true
            )
        }
    }

    fun calculateSquare() {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0
        val result = currentValue.pow(2)

        _state.update {
            it.copy(
                currentInput = formatResult(result),
                isNewInput = true
            )
        }
    }

    fun calculateReciprocal() {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0

        if (currentValue == 0.0) {
            // Handle division by zero
            _state.update {
                it.copy(
                    currentInput = "Error",
                    isNewInput = true
                )
            }
            return
        }

        val result = 1.0 / currentValue

        _state.update {
            it.copy(
                currentInput = formatResult(result),
                isNewInput = true
            )
        }
    }

    fun calculatePercent() {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0

        // If in the middle of an operation, calculate percentage of first operand
        val result = if (currentState.operation != Operation.NONE) {
            currentState.firstOperand * currentValue / 100.0
        } else {
            currentValue / 100.0
        }

        _state.update {
            it.copy(
                currentInput = formatResult(result),
                isNewInput = true
            )
        }
    }

    fun memoryClear() {
        _state.update {
            it.copy(memoryValue = 0.0)
        }
    }

    fun memoryRecall() {
        _state.update {
            it.copy(
                currentInput = formatResult(it.memoryValue),
                isNewInput = true
            )
        }
    }

    fun memoryAdd() {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0

        _state.update {
            it.copy(
                memoryValue = it.memoryValue + currentValue,
                isNewInput = true
            )
        }
    }

    fun memorySubtract() {
        val currentState = _state.value
        val currentValue = currentState.currentInput.toDoubleOrNull() ?: 0.0

        _state.update {
            it.copy(
                memoryValue = it.memoryValue - currentValue,
                isNewInput = true
            )
        }
    }

    private fun performOperation(a: Double, b: Double, operation: Operation): Double {
        return when (operation) {
            Operation.ADD -> a + b
            Operation.SUBTRACT -> a - b
            Operation.MULTIPLY -> a * b
            Operation.DIVIDE -> if (b != 0.0) a / b else Double.NaN
            Operation.NONE -> b
        }
    }

    private fun getOperationSymbol(operation: Operation): String {
        return when (operation) {
            Operation.ADD -> "+"
            Operation.SUBTRACT -> "-"
            Operation.MULTIPLY -> "×"
            Operation.DIVIDE -> "÷"
            Operation.NONE -> ""
        }
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "Error"

        // Format the number to avoid unnecessary decimals
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            // Limit decimal places for readability
            "%.10f".format(value).trimEnd('0').trimEnd('.')
        }
    }
}