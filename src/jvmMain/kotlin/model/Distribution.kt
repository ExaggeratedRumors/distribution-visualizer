package model

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

interface Distribution {
    fun probabilityDensityFunction(x: Int): Float
    fun mean(): Float
    fun standardDeviation(): Float
}

class Linear(private val a: Float, private val b: Float): Distribution {
    override fun probabilityDensityFunction(x: Int) = a * x + b
    override fun mean() = 0f
    override fun standardDeviation() = 0f
}

class Binomial(private val p: Float, private val n: Int): Distribution {
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
    override fun probabilityDensityFunction(x: Int) =
        p * (1 - p).pow(x - 1)
    override fun mean() = 1 / p
    override fun standardDeviation() = sqrt(1 - p) / p
}

class Exponential(): Distribution {
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

class Poisson(private val lambda: Float): Distribution {
    override fun probabilityDensityFunction(x: Int): Float {
        val xf = (1..x).reduce { v, w -> v * w }
        return lambda.pow(x) * exp(-lambda) / xf
    }
    override fun mean() = lambda
    override fun standardDeviation() = sqrt(lambda)
}

class Pareto() : Distribution {
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


class Gauss(private val u: Float, private val s: Float): Distribution {
    override fun probabilityDensityFunction(x: Int) =
        (1 / s * sqrt(2 * PI) * exp( -0.5 * ((x - u) / s).pow(2)))
            .toFloat()
    override fun mean() = u
    override fun standardDeviation() = s
}


class LogNormal(): Distribution {
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

class Gumbel(): Distribution {
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

class MaxwellBoltzmann(): Distribution {
    var E = 0
    var g = 0
    var n = 0
    var Z = 1
    var T = 300
    var u = 1

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


