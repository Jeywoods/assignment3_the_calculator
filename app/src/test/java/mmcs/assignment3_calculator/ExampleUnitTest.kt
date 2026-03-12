package mmcs.assignment3_calculator

import mmcs.assignment3_calculator.viewmodel.CalculatorViewModel
import mmcs.assignment3_calculator.viewmodel.Operation
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorViewModelTest {
    private lateinit var viewModel: CalculatorViewModel
    @Before
    fun setInitValue(){
        viewModel = CalculatorViewModel()
        viewModel.display.set("0")
    }
    @Test
    fun testAddDigit1(){
        viewModel.addDigit(5)
        assertEquals("5", viewModel.display.get())
    }
    @Test
    fun testAddDigit2(){
        viewModel.display.set("12")
        viewModel.addDigit(3)
        assertEquals("123", viewModel.display.get())
    }
    @Test
    fun testAddDigit3(){
        viewModel.addDigit(4)
        viewModel.addDigit(5)
        viewModel.addDigit(6)
        assertEquals("456", viewModel.display.get())
    }
    @Test
    fun testAddPoint1(){
        viewModel.display.set("12")
        viewModel.addPoint()
        assertEquals("12.", viewModel.display.get())
    }
    @Test
    fun testAddPoint2(){
        viewModel.display.set("2.7")
        viewModel.addPoint()
        assertEquals("2.7", viewModel.display.get())
    }
    @Test
    fun testAddPoint3(){
        viewModel.display.set("3")
        viewModel.addPoint()
        viewModel.addDigit(5)
        assertEquals("3.5", viewModel.display.get())
    }
    @Test
    fun testAddOperationPlus(){
        viewModel.display.set("4")
        viewModel.addOperation(Operation.ADD)
        viewModel.display.set("5")
        viewModel.compute()
        assertEquals("9", viewModel.display.get())
    }
    @Test
    fun testAddOperationSub(){
        viewModel.display.set("8")
        viewModel.addOperation(Operation.SUB)
        viewModel.display.set("3")
        viewModel.compute()
        assertEquals("5", viewModel.display.get())
    }
    @Test
    fun testAddOperationMul(){
        viewModel.display.set("12")
        viewModel.addOperation(Operation.MUL)
        viewModel.display.set("5")
        viewModel.compute()
        assertEquals("60", viewModel.display.get())
    }
    @Test
    fun testAddOperationDiv(){
        viewModel.display.set("12")
        viewModel.addOperation(Operation.DIV)
        viewModel.display.set("4")
        viewModel.compute()
        assertEquals("3", viewModel.display.get())
    }
    @Test
    fun testAddOperationDiv0(){
        viewModel.display.set("4")
        viewModel.addOperation(Operation.DIV)
        viewModel.display.set("0")
        viewModel.compute()
        assertEquals("Не определен", viewModel.display.get())
    }
    @Test
    fun testAddOperationPerc(){
        viewModel.display.set("120")
        viewModel.addOperation(Operation.PERC)
        viewModel.display.set("5")
        viewModel.compute()
        assertEquals("6", viewModel.display.get())
    }
    @Test
    fun testAddOperationNegPostfix(){
        viewModel.display.set("12")
        viewModel.addOperation(Operation.NEG)
        viewModel.compute()
        assertEquals("-12", viewModel.display.get())
    }
    @Test
    fun testAddOperationNegPrefix(){
        viewModel.addOperation(Operation.NEG)
        viewModel.display.set("12")
        viewModel.compute()
        assertEquals("-12", viewModel.display.get())
    }
    @Test
    fun testClear1(){
        viewModel.display.set("15")
        viewModel.clear()
        assertEquals("0", viewModel.display.get())
    }
    @Test
    fun testClear2(){
        viewModel.addDigit(3)
        viewModel.addOperation(Operation.MUL)
        viewModel.clear()
        viewModel.addDigit(5)
        viewModel.compute()
        assertEquals("15", viewModel.display.get())
    }
    @Test
    fun testReset1(){
        viewModel.addDigit(6)
        viewModel.reset()
        assertEquals("0", viewModel.display.get())
    }
    @Test
    fun testReset2(){
        viewModel.addDigit(6)
        viewModel.addOperation(Operation.DIV)
        viewModel.addDigit(3)
        viewModel.reset()
        viewModel.compute()
        assertEquals("0", viewModel.display.get())
    }
    @Test
    fun testReset3() {
        viewModel.addDigit(6)
        viewModel.addOperation(Operation.DIV)
        viewModel.addDigit(3)
        viewModel.compute()
        viewModel.reset()
        assertEquals("0", viewModel.display.get())
    }
    @Test
    fun testResultFormatted1(){
        viewModel.addDigit(10)
        viewModel.addOperation(Operation.DIV)
        viewModel.addDigit(4)
        viewModel.compute()
        assertEquals("2.5", viewModel.display.get())

    }
    @Test
    fun testResultFormatted2(){
        viewModel.display.set("2.5")
        viewModel.addOperation(Operation.SUB)
        viewModel.display.set("1.5")
        viewModel.compute()
        assertEquals("1", viewModel.display.get())
    }
}