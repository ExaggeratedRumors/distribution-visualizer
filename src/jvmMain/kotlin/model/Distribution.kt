package model

interface Distribution {
    fun probabilityDensityFunction(x: Double): Double
    fun mean(x: Double): Double
    fun standardDeviation(x: Double): Double
}