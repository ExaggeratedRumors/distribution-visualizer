import org.junit.Test

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
}
