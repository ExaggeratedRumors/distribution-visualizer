import org.junit.Test
import kotlin.test.*
import gui.*

class UnitTest {
    @Test
    fun `grid data create`() {
        val pp = PlotPane()
        val output = pp.createYGrid(0.5f,0.75f)
        val expected = listOf(0.55f, 0.6f, 0.65f, 0.7f, 0.75f)
        assertEquals(expected, output)
    }
}
