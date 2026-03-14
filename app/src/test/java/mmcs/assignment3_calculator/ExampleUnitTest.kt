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
        setDisplay("0")
    }
    fun getDisplay(): String{
        return viewModel.display.get() ?: ""
    }
    fun setDisplay(value: String){
        viewModel.display.set(value)
    }
    @Test
    fun testAddDigit1(){
        viewModel.addDigit(5)
        assertEquals("5", getDisplay())
    }
    @Test
    fun testAddDigit2(){
        setDisplay("12")
        viewModel.addDigit(3)
        assertEquals("123", getDisplay())
    }
    @Test
    fun testAddDigit3(){
        viewModel.addDigit(4)
        viewModel.addDigit(5)
        viewModel.addDigit(6)
        assertEquals("456", getDisplay())
    }
    @Test
    fun testAddPoint1(){
        setDisplay("12")
        viewModel.addPoint()
        assertEquals("12.", getDisplay())
    }
    @Test
    fun testAddPoint2(){
        setDisplay("2.7")
        viewModel.addPoint()
        assertEquals("2.7", getDisplay())
    }
    @Test
    fun testAddPoint3(){
        setDisplay("3")
        viewModel.addPoint()
        viewModel.addDigit(5)
        assertEquals("3.5", getDisplay())
    }
    @Test
    fun testAddOperationPlus(){
        setDisplay("0.1")
        viewModel.addOperation(Operation.ADD)
        setDisplay("0.2")
        viewModel.compute()
        assertEquals("0.3", getDisplay())
    }
    @Test
    fun testAddOperationSub(){
        setDisplay("8")
        viewModel.addOperation(Operation.SUB)
        setDisplay("3")
        viewModel.compute()
        assertEquals("5", getDisplay())
    }
    @Test
    fun testAddOperationMul(){
        setDisplay("12")
        viewModel.addOperation(Operation.MUL)
        setDisplay("5")
        viewModel.compute()
        assertEquals("60", getDisplay())
    }
    @Test
    fun testAddOperationDiv(){
        setDisplay("12")
        viewModel.addOperation(Operation.DIV)
        setDisplay("4")
        viewModel.compute()
        assertEquals("3", getDisplay())
    }
    @Test
    fun testAddOperationDiv0(){
        setDisplay("4")
        viewModel.addOperation(Operation.DIV)
        setDisplay("0")
        viewModel.compute()
        assertEquals("Не определен", getDisplay())
    }
    @Test
    fun testAddOperationPerc() {
        viewModel.addDigit(1)
        viewModel.addDigit(2)
        viewModel.addDigit(0)        // display = "120"
        viewModel.addOperation(Operation.PERC)
        viewModel.addDigit(5)        // display = "5"
        viewModel.compute()
        assertEquals("6", getDisplay())
    }
    @Test
    fun testAddOperationNeg(){
        setDisplay("12")
        viewModel.addOperation(Operation.NEG)
        viewModel.compute()
        assertEquals("-12", getDisplay())
    }
    @Test
    fun testClear1(){
        setDisplay("15")
        viewModel.clear()
        assertEquals("0", getDisplay())
    }
    @Test
    fun testClear2(){
        viewModel.addDigit(3)
        viewModel.addOperation(Operation.MUL)
        viewModel.clear()
        viewModel.addDigit(5)
        viewModel.compute()
        assertEquals("15", getDisplay())
    }
    @Test
    fun testReset1(){
        viewModel.addDigit(6)
        viewModel.reset()
        assertEquals("0", getDisplay())
    }
    @Test
    fun testReset2(){
        viewModel.addDigit(6)
        viewModel.addOperation(Operation.DIV)
        viewModel.addDigit(3)
        viewModel.reset()
        viewModel.compute()
        assertEquals("0", getDisplay())
    }
    @Test
    fun testReset3() {
        viewModel.addDigit(6)
        viewModel.addOperation(Operation.DIV)
        viewModel.addDigit(3)
        viewModel.compute()
        viewModel.reset()
        assertEquals("0", getDisplay())
    }
    @Test
    fun testResultFormatted1(){
        viewModel.addDigit(10)
        viewModel.addOperation(Operation.DIV)
        viewModel.addDigit(4)
        viewModel.compute()
        assertEquals("2.5", getDisplay())

    }
    @Test
    fun testResultFormatted2(){
        setDisplay("2.5")
        viewModel.addOperation(Operation.SUB)
        setDisplay("1.5")
        viewModel.compute()
        assertEquals("1", getDisplay())
    }
}