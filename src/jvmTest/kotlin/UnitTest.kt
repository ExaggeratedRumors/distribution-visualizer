import org.junit.Test
import kotlin.test.*
import gui.*

class UnitTest {
    @Test
    fun `grid data create`() {
        val pp = PlotPane()
        val o1 = pp.createYGrid(0.5f,0.75f)
        val e1 = listOf(0.55f, 0.6f, 0.65f, 0.7f, 0.75f)
        assertEquals(e1, o1)
        val o2 = pp.createYGrid(1.5f,3.5f)
        val e2 = listOf(1.5f, 2.0f, 2.5f, 3.0f, 3.5f)
        assertEquals(e2, o2)
        val o3 = pp.createYGrid(1f,5f)
        val e3 = listOf(15f, 2f, 3f, 4f, 5f)
        assertEquals(e3, o3)
    }
}
