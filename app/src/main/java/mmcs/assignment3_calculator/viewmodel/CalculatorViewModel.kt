package mmcs.assignment3_calculator.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class CalculatorViewModel: BaseObservable(), Calculator {
    override var display = ObservableField<String>()

    private var firstNumber: Double = 0.0
    private var secondNumber: Double = 0.0
    private var previousOperation: Operation? = null


    override fun addDigit(dig: Int) {
        val currentNumber = display.get() ?: "0"
        display.set(if(currentNumber == "0") "$dig" else currentNumber + dig)
    }

    override fun addPoint() {
        val currentNumber = display.get() ?: "0"
        if(!currentNumber.contains("."))
            display.set("$currentNumber.")
    }

    override fun addOperation(op: Operation) {
        if(previousOperation != null)
            compute()
        firstNumber = display.get()?.toDoubleOrNull() ?: 0.0
        previousOperation = op
        display.set("0")
    }

    override fun compute() {
        secondNumber = display.get()?.toDoubleOrNull() ?: 0.0
        if (previousOperation == Operation.DIV && secondNumber == 0.0) {
            display.set("Не определен")
            previousOperation = null
            return
        }
        val result = when(previousOperation){
            Operation.ADD -> firstNumber + secondNumber
            Operation.SUB -> firstNumber - secondNumber
            Operation.MUL -> firstNumber * secondNumber
            Operation.DIV -> firstNumber / secondNumber
            Operation.PERC -> firstNumber * secondNumber / 100
            Operation.NEG -> -secondNumber
            null -> secondNumber
        }
        display.set(result.toString())
        firstNumber = result
        previousOperation = null
    }

    override fun clear() {
        display.set("0")
    }

    override fun reset() {
        clear()
        firstNumber = 0.0
        secondNumber = 0.0
        previousOperation = null
    }
}