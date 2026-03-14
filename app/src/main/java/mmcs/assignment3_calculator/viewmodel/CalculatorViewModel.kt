package mmcs.assignment3_calculator.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel : BaseObservable(), Calculator {

    override var display = ObservableField("0")

    private var firstNumber: BigDecimal = BigDecimal.ZERO
    private var previousOperation: Operation? = null

    private fun currentValue(): BigDecimal {
        return display.get()?.toBigDecimalOrNull() ?: BigDecimal.ZERO
    }

    override fun addDigit(dig: Int) {
        val current = display.get() ?: "0"
        display.set(if (current == "0") "$dig" else "$current$dig")
    }

    override fun addPoint() {
        val current = display.get() ?: "0"
        if (!current.contains(".")) {
            display.set("$current.")
        }
    }

    override fun addOperation(op: Operation) {

        if (op == Operation.NEG) {
            val value = currentValue().negate()
            display.set(formatResult(value))
            return
        }

        if (previousOperation != null) {
            compute()
        }

        firstNumber = currentValue()
        previousOperation = op
        display.set("0")
    }

    override fun compute() {

        val secondNumber = currentValue()

        if (previousOperation == Operation.DIV && secondNumber == BigDecimal.ZERO) {
            display.set("Не определен")
            previousOperation = null
            return
        }

        val result = when (previousOperation) {
            Operation.ADD -> firstNumber.add(secondNumber)
            Operation.SUB -> firstNumber.subtract(secondNumber)
            Operation.MUL -> firstNumber.multiply(secondNumber)

            Operation.DIV ->
                firstNumber.divide(secondNumber, 10, RoundingMode.HALF_UP)

            Operation.PERC ->
                firstNumber.multiply(secondNumber)
                    .divide(BigDecimal(100))

            null -> secondNumber
            Operation.NEG -> secondNumber
        }

        display.set(formatResult(result))

        firstNumber = result
        previousOperation = null
    }

    override fun clear() {
        display.set("0")
    }

    override fun reset() {
        clear()
        firstNumber = BigDecimal.ZERO
        previousOperation = null
    }

    private fun formatResult(value: BigDecimal): String {
        return value.stripTrailingZeros().toPlainString()
    }
}