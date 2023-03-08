import org.junit.Test
import kotlin.test.*
import gui.*
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow

class UnitTest {
    @Test
    fun `grid strokes`() {
        val maxVal = 10f
        val minVal = 1f
        val orderOfMagnitude = 10.0.pow(floor(ln((maxVal - minVal).toDouble()) * 0.4343))
        val p = (1..10)
            .map { (it * orderOfMagnitude).toFloat() }
            .filter { it in minVal..maxVal }
        println(p)
    }
}
