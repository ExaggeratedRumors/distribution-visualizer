import org.junit.Test
import gui.*

fun main() {
    val unitTest = UnitTest()
    unitTest.calculations()
}

class UnitTest {
    @Test
    fun calculations() {
        val amt = 10
        val c = (0f..amt.toFloat())
        println(c)
    }

    @Test
    fun `grid data create`() {
        val pp = PlotPane()
        val output = pp.createGrid(0f,1f,0f,5f)
        print(output)
    }
}
