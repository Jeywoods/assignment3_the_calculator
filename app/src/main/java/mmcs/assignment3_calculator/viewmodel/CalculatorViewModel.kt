package mmcs.assignment3_calculator.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel : BaseObservable(), Calculator {

    override var display = ObservableField("0")

    private var firstNumber: BigDecimal = BigDecimal.ZERO
    private var previousOperation: Operation? = null
    private var isNewNumber = true

    companion object {
        private const val ERROR = "Не определен"
    }

    private fun currentValue(): BigDecimal {
        return display.get()?.toBigDecimalOrNull() ?: BigDecimal.ZERO
    }

    override fun addDigit(dig: Int) {

        if (display.get() == ERROR) {
            reset()
        }

        val current = display.get() ?: "0"

        if (isNewNumber) {
            display.set(dig.toString())
            isNewNumber = false
        } else {
            display.set(if (current == "0") "$dig" else "$current$dig")
        }
    }

    override fun addPoint() {

        if (display.get() == ERROR) {
            reset()
        }

        val current = display.get() ?: "0"

        if (isNewNumber) {
            display.set("0.")
            isNewNumber = false
        } else if (!current.contains(".")) {
            display.set("$current.")
        }
    }

    override fun addOperation(op: Operation) {

        if (display.get() == ERROR) return

        if (op == Operation.NEG) {
            val value = currentValue().negate()
            display.set(formatResult(value))
            return
        }

        if (previousOperation != null && !isNewNumber) {
            compute()
        }

        firstNumber = currentValue()
        previousOperation = op
        isNewNumber = true
    }

    override fun compute() {

        if (display.get() == ERROR) return

        val secondNumber = currentValue()

        if (previousOperation == Operation.DIV && secondNumber == BigDecimal.ZERO) {
            display.set(ERROR)
            previousOperation = null
            isNewNumber = true
            return
        }

        val result = when (previousOperation) {

            Operation.ADD ->
                firstNumber.add(secondNumber)

            Operation.SUB ->
                firstNumber.subtract(secondNumber)

            Operation.MUL ->
                firstNumber.multiply(secondNumber)

            Operation.DIV ->
                firstNumber.divide(secondNumber, 10, RoundingMode.HALF_UP)

            Operation.PERC ->
                firstNumber.multiply(secondNumber).divide(BigDecimal(100))

            null ->
                secondNumber

            Operation.NEG ->
                secondNumber
        }

        display.set(formatResult(result))

        firstNumber = result
        previousOperation = null
        isNewNumber = true
    }

    override fun clear() {
        display.set("0")
        isNewNumber = true
    }

    override fun reset() {
        display.set("0")
        firstNumber = BigDecimal.ZERO
        previousOperation = null
        isNewNumber = true
    }

    private fun formatResult(value: BigDecimal): String {
        return value.stripTrailingZeros().toPlainString()
    }
}