package model

import kotlin.math.*

interface Distribution {
    fun genRange(): FloatArray
    fun probabilityDensityFunction(x: Int): Float
    fun mean(): Float
    fun standardDeviation(): Float
}

class Linear(private val a: Float, private val b: Float): Distribution {
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int) = a * x + b
    override fun mean() = 0f
    override fun standardDeviation() = 0f
}

class Binomial(private val p: Float, private val n: Int): Distribution {
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int): Float {
        val nf = (1..n).reduce { v, w -> v * w }
        val xf = (1..x).reduce { v, w -> v * w }
        val nkf = (1 .. (n - x)).reduce { v, w -> v * w }
        return nf / (xf * nkf) * p.pow(x) * (1 - p).pow(n - x)
    }
    override fun mean() = n * p
    override fun standardDeviation() = sqrt(n * p * (1 - p))
}

class Geometric(private val p: Float): Distribution {
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int) =
        p * (1 - p).pow(x - 1)
    override fun mean() = 1 / p
    override fun standardDeviation() = sqrt(1 - p) / p
}

class Exponential(private val lambda: Float): Distribution {
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int) = lambda * exp(-lambda * x)
    override fun mean() = 1 / lambda
    override fun standardDeviation() = 1 / lambda
}

class Poisson(private val lambda: Float): Distribution {
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int): Float {
        val xf = (1..x).reduce { v, w -> v * w }
        return lambda.pow(x) * exp(-lambda) / xf
    }
    override fun mean() = lambda
    override fun standardDeviation() = sqrt(lambda)
}

class Pareto(xm_i: Float, alpha_i: Float) : Distribution {
    private var xm: Float = if (xm_i > 0) xm_i else 1f
    private var alpha: Float = if (alpha_i > 0) alpha_i else 1f
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int) =
        alpha * xm.pow(alpha) / x.toFloat().pow(alpha + 1)
    override fun mean() =
        if (alpha <= 1) Float.POSITIVE_INFINITY else alpha * xm / (alpha - 1)
    override fun standardDeviation() =
        if (alpha <= 2) Float.POSITIVE_INFINITY else xm * sqrt(alpha) / ((alpha - 1) * sqrt(alpha - 2))
}


class Gauss(private val u: Float, private val s: Float): Distribution {
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int) =
        (1 / s * sqrt(2 * PI) * exp( -0.5 * ((x - u) / s).pow(2)))
            .toFloat()
    override fun mean() = u
    override fun standardDeviation() = s
}


class LogNormal(private val u: Float, private val s: Float): Distribution {
    init {
        require(s > 0) {
            "Sigma must be higher than 0"
        }
    }

    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int) =
        (1 / (x * s * sqrt(2 * PI.toFloat()))) *  exp(-1 * (ln(x.toFloat()) - u).pow(2) / (2 * s.pow(2)))
    override fun mean() = exp(u + s.pow(2) / 2)
    override fun standardDeviation() =
        sqrt(exp(s.pow(2) - 1) * exp(2 * u + s.pow(2)))
}

class Gumbel(private val u: Float, private val b: Float): Distribution {
    init {
        require(b > 0) {
            "Beta must be higher than 0"
        }
    }

    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int): Float {
        val z = (x - u) / b
        return (1 / b) * exp ( -1 * (z + exp(-z)))
    }
    override fun mean(): Float {
        val eulerMascheroni = 0.57721f
        return u + b * eulerMascheroni
    }
    override fun standardDeviation() = PI.toFloat() * b / 6
}

class MaxwellBoltzmann(): Distribution {
    var E = 0
    var g = 0
    var n = 0
    var Z = 1
    var T = 300
    var u = 1
    override fun genRange(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun probabilityDensityFunction(x: Int): Float {
        TODO("Not yet implemented")
    }

    override fun mean(): Float {
        TODO("Not yet implemented")
    }

    override fun standardDeviation(): Float {
        TODO("Not yet implemented")
    }
}


class BoseEinstein()

class FermiDirac()


