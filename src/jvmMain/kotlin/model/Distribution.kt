package model

import kotlin.math.*

sealed interface Distribution {
    fun range(): List<Float>
    fun probabilityDensityFunction(x: Float): Float
    fun mean(): Float
    fun standardDeviation(): Float
}

class Continuous(private val a: Float = 1f, private val b: Float = 2f): Distribution {
    init {
        require(a < b) {
            "First node must be lower than second"
        }
    }
    override fun range() = (0..100).map {
        - 1 + a + (b - a + 2) * it / 100
    }
    override fun probabilityDensityFunction(x: Float) =
        if(x < a || x > b) 0f else 1 / (b - a)
    override fun mean() = (a + b) / 2
    override fun standardDeviation() = (b - a) / sqrt(12f)
}

class Binomial(private val p: Float = 0.5f, private val n: Int = 10): Distribution {
    init {
        require(p in 0.0..1.0 && n >= 2) {
            "Probability must be in range [0,1] and amount must be positive"
        }
    }
    override fun range() = (0..100).map {
        -1 + 1f * (n + 2) * it / 100
    }
    override fun probabilityDensityFunction(x: Float): Float {
        if(x < 0 || x > n) return 0f
        val nf = factorial(n)
        val xf = factorial(x.toInt())
        val nkf = factorial((n - x.toInt()))
        return nf / (xf * nkf) * p.pow(x) * (1 - p).pow(n - x)
    }
    override fun mean() = n * p
    override fun standardDeviation() = sqrt(n * p * (1 - p))
}

class Geometric(private val p: Float = 0.5f): Distribution {
    init {
        require(p in 0.0..1.0) {
            "Probability must be in range [0,1]"
        }
    }
    override fun range() = (0..100).map {
        it / exp(p + 1)
    }
    override fun probabilityDensityFunction(x: Float) =
        p * (1 - p).pow(x - 1)
    override fun mean() = 1 / p
    override fun standardDeviation() = sqrt(1 - p) / p
}

class Exponential(private val lambda: Float = 1f): Distribution {
    init {
        require(lambda > 0) {
            "Lambda parameter must be greater than 0"
        }
    }
    override fun range() = (0..100).map {
        (it + 1) / (lambda * 5)
    }
    override fun probabilityDensityFunction(x: Float) = lambda * exp(-lambda * x)
    override fun mean() = 1 / lambda
    override fun standardDeviation() = 1 / lambda
}

class Poisson(private val lambda: Float = 1f): Distribution {
    init {
        require(lambda > 0) {
            "Lambda parameter must be greater than 0"
        }
    }
    override fun range() = (0..100)
        .map { it.toFloat() }
        .filter { if(lambda < 2) it <= 10 else it <= 20}

    override fun probabilityDensityFunction(x: Float): Float {
        val xf = factorial(x.toInt())
        return lambda.pow(x) * exp(-lambda) / xf
    }
    override fun mean() = lambda
    override fun standardDeviation() = sqrt(lambda)
}

class Pareto(private val xm: Float = 1f, private val alpha: Float = 10f) : Distribution {
    init {
        require(xm > 0 && alpha > 0) {
            "Mode and alpha parameter must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
        1f + it / (10 * alpha)
    }
    override fun probabilityDensityFunction(x: Float) =
        alpha * xm.pow(alpha) / x.pow(alpha + 1)
    override fun mean() =
        if (alpha <= 1) Float.POSITIVE_INFINITY else alpha * xm / (alpha - 1)
    override fun standardDeviation() =
        if (alpha <= 2) Float.POSITIVE_INFINITY else xm * sqrt(alpha) / ((alpha - 1) * sqrt(alpha - 2))
}

class Gauss(private val mean: Float = 0f, private val sigma: Float = 1f): Distribution {
    init {
        require(sigma > 0) {
            "Sigma must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
        mean + sigma * (50 - it) / 10
    }
    override fun probabilityDensityFunction(x: Float) =
        (1 / sigma * sqrt(2 * PI) * exp( -0.5 * ((x - mean) / sigma).pow(2)))
            .toFloat()
    override fun mean() = mean
    override fun standardDeviation() = sigma
}

class LogNormal(private val mean: Float = 0f, private val sigma: Float = 0.25f): Distribution {
    init {
        require(sigma > 0) {
            "Sigma must be higher than 0"
        }
    }
    override fun range() = (0..100).map {
        mean + sigma * (it + 1) / 10
    }
    override fun probabilityDensityFunction(x: Float) =
        (1 / (x * sigma * sqrt(2 * PI.toFloat()))) *
                exp(-1 * (ln(x) - mean).pow(2) / (2 * sigma.pow(2)))
    override fun mean() = exp(mean + sigma.pow(2) / 2)
    override fun standardDeviation() =
        sqrt(exp(sigma.pow(2) - 1) * exp(2 * mean + sigma.pow(2)))
}

class Gumbel(private val mean: Float = 0f, private val beta: Float = 1f): Distribution {
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

