package model

import kotlin.math.*

interface Distribution {
    fun range(): List<Float>
    fun probabilityDensityFunction(x: Float): Float
    fun mean(): Float
    fun standardDeviation(): Float
}

class Continuous(private val a: Float, private val b: Float): Distribution {
    init {
        require(a < b) {
            "First node must be lower than second"
        }
    }
    override fun range() = (0..100).map {
        (a - 10) + it / ((b + 10) - (a - 10))
    }
    override fun probabilityDensityFunction(x: Float) =
        if(x < a || x > b) 0f else 1 / (b - a)
    override fun mean() = (a + b) / 2
    override fun standardDeviation() = (b - a) / sqrt(12f)
}

class Binomial(private val p: Float, private val n: Int): Distribution {
    init {
        require(p in 0.0..1.0 && n >= 0) {
            "Probability must be in range [0,1] and amount must be positive"
        }
    }
    override fun range() = (0..100).map {
        1f * it / n
    }
    override fun probabilityDensityFunction(x: Float): Float {
        val nf = (1..n).reduce { v, w -> v * w }
        val xf = (1..x.toInt()).reduce { v, w -> v * w }
        val nkf = (1 .. (n - x.toInt())).reduce { v, w -> v * w }
        return nf / (xf * nkf) * p.pow(x) * (1 - p).pow(n - x)
    }
    override fun mean() = n * p
    override fun standardDeviation() = sqrt(n * p * (1 - p))
}

class Geometric(private val p: Float): Distribution {
    init {
        require(p in 0.0..1.0) {
            "Probability must be in range [0,1]"
        }
    }
    override fun range() = (1..100).map {
        it * p
    }
    override fun probabilityDensityFunction(x: Float) =
        p * (1 - p).pow(x - 1)
    override fun mean() = 1 / p
    override fun standardDeviation() = sqrt(1 - p) / p
}

class Exponential(private val lambda: Float): Distribution {
    init {
        require(lambda > 0) {
            "Lambda parameter must be greater than 0"
        }
    }
    override fun range() = (1..100).map {
        it / (lambda * 5)
    }
    override fun probabilityDensityFunction(x: Float) = lambda * exp(-lambda * x)
    override fun mean() = 1 / lambda
    override fun standardDeviation() = 1 / lambda
}

class Poisson(private val lambda: Float): Distribution {
    init {
        require(lambda > 0) {
            "Lambda parameter must be greater than 0"
        }
    }
    override fun range() = (1..100).map {
        it / (2 * lambda)
    }
    override fun probabilityDensityFunction(x: Float): Float {
        val xf = (1..x.toInt()).reduce { v, w -> v * w }
        return lambda.pow(x) * exp(-lambda) / xf
    }
    override fun mean() = lambda
    override fun standardDeviation() = sqrt(lambda)
}

class Pareto(private val xm: Float, private val alpha: Float) : Distribution {
    init {
        require(xm > 0 && alpha > 0) {
            "Mode and alpha parameter must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
       1 + it * alpha / 10
    }
    override fun probabilityDensityFunction(x: Float) =
        alpha * xm.pow(alpha) / x.toFloat().pow(alpha + 1)
    override fun mean() =
        if (alpha <= 1) Float.POSITIVE_INFINITY else alpha * xm / (alpha - 1)
    override fun standardDeviation() =
        if (alpha <= 2) Float.POSITIVE_INFINITY else xm * sqrt(alpha) / ((alpha - 1) * sqrt(alpha - 2))
}

class Gauss(private val mean: Float, private val sigma: Float): Distribution {
    init {
        require(sigma > 0) {
            "Sigma must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
        (mean - 4 * sigma) + it / (8 * sigma)
    }
    override fun probabilityDensityFunction(x: Float) =
        (1 / sigma * sqrt(2 * PI) * exp( -0.5 * ((x - mean) / sigma).pow(2)))
            .toFloat()
    override fun mean() = mean
    override fun standardDeviation() = sigma
}

class LogNormal(private val mean: Float, private val sigma: Float): Distribution {
    init {
        require(sigma > 0) {
            "Sigma must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
        it / (50 * sigma)
    }
    override fun probabilityDensityFunction(x: Float) =
        (1 / (abs(x) * sigma * sqrt(2 * PI.toFloat()))) *
                exp(-1 * (ln(abs(x)) - mean).pow(2) / (2 * sigma.pow(2)))
    override fun mean() = exp(mean + sigma.pow(2) / 2)
    override fun standardDeviation() =
        sqrt(exp(sigma.pow(2) - 1) * exp(2 * mean + sigma.pow(2)))
}

class Gumbel(private val mean: Float, private val beta: Float): Distribution {
    init {
        require(beta > 0) {
            "Beta must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
        it / (beta * 10)
    }
    override fun probabilityDensityFunction(x: Float): Float {
        val z = (x - mean) / beta
        return (1 / beta) * exp ( -1 * (z + exp(-z)))
    }
    override fun mean(): Float {
        val eulerMascheroni = 0.57721f
        return mean + beta * eulerMascheroni
    }
    override fun standardDeviation() = PI.toFloat() * beta / 6
}

