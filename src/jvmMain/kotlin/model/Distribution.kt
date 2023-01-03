package model

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

interface Distribution {
    fun probabilityDensityFunction(x: Float): Float
    fun mean(): Float
    fun standardDeviation(): Float
}

class Gauss(private val u: Float, private val s: Float): Distribution {
    override fun probabilityDensityFunction(x: Float) =
        (1 / s * sqrt(2 * PI) * exp( -0.5 * ((x - u) / s).pow(2)))
            .toFloat()
    override fun mean() = u
    override fun standardDeviation() = s
}

class Linear(private val a: Float, private val b: Float): Distribution {
    override fun probabilityDensityFunction(x: Float) = a * x + b
    override fun mean() = 0f
    override fun standardDeviation() = 0f
}