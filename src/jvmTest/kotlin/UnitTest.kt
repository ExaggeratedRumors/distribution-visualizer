import org.junit.Test
import kotlin.test.*
import gui.*

class UnitTest {
    @Test
    fun `grid data create`() {
        val pp = PlotPane()
        val o1 = pp.discretization(0.5f,0.75f)
        val e1 = listOf(0.5f, 0.6f, 0.7f)
        assertEquals(e1, o1)
        val o2 = pp.discretization(10.5f,35.5f)
        val e2 = listOf(20f, 30f)
        assertEquals(e2, o2)
        val o3 = pp.discretization(0.004f,1f)
        val e3 = listOf(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f)
        assertEquals(e3, o3)
    }
}
