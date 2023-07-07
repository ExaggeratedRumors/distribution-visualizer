package model

import kotlin.math.*

sealed interface Distribution {
    fun range(): List<Float>
    fun conditions(): Boolean
    fun probabilityDensityFunction(x: Float): Float
    fun mean(): Float
    fun standardDeviation(): Float
    fun getParameters(): Map<String, Float>
    fun setParameters(args: List<Float>)
}

class Continuous(private var a: Float = 1f, private var b: Float = 2f) : Distribution {
    init {
        require(conditions()) {
            "First node must be lower than second"
        }
    }

    override fun range() = (0..100).map {
        -1 + a + (b - a + 2) * it / 100
    }

    override fun conditions() = a < b

    override fun probabilityDensityFunction(x: Float) = if (x < a || x > b) 0f else 1 / (b - a)

    override fun mean() = (a + b) / 2
    override fun standardDeviation() = (b - a) / sqrt(12f)
    override fun getParameters(): Map<String, Float> = mapOf("a" to a, "b" to b)
    override fun setParameters(args: List<Float>) {
        if (!conditions()) return
        a = args[0]
        b = args[1]
    }
}

class Binomial(private var p: Float = 0.5f, private var n: Int = 10) : Distribution {
    init {
        require(conditions()) {
            "Probability must be in range [0,1] and amount must be positive"
        }
    }

    override fun range() = (0..100).map {
        -1 + 1f * (n + 2) * it / 100
    }

    override fun conditions() = p in 0.0..1.0 && n >= 2

    override fun probabilityDensityFunction(x: Float): Float {
        if (x < 0 || x > n) return 0f
        val nf = factorial(n)
        val xf = factorial(x.toInt())
        val nkf = factorial((n - x.toInt()))
        return nf / (xf * nkf) * p.pow(x) * (1 - p).pow(n - x)
    }

    override fun mean() = n * p
    override fun standardDeviation() = sqrt(n * p * (1 - p))
    override fun getParameters(): Map<String, Float> = mapOf("p" to p, "n" to n.toFloat())
    override fun setParameters(args: List<Float>) {
        if (!conditions()) return
        p = args[0]
        n = args[1].toInt()
    }
}

class Geometric(private var p: Float = 0.5f) : Distribution {
    init {
        require(conditions()) {
            "Probability must be in range [0,1]"
        }
    }

    override fun range() = (0..100).map {
        it / exp(p + 1)
    }

    override fun conditions() = p in 0.0..1.0

    override fun probabilityDensityFunction(x: Float) = p * (1 - p).pow(x - 1)

    override fun mean() = 1 / p
    override fun standardDeviation() = sqrt(1 - p) / p
    override fun getParameters(): Map<String, Float> = mapOf("p" to p)
    override fun setParameters(args: List<Float>) {
        if (!conditions()) return
        this.p = args[0]
    }
}

class Exponential(private var lambda: Float = 1f) : Distribution {
    init {
        require(conditions()) {
            "Lambda parameter must be greater than 0"
        }
    }

    override fun range() = (0..100).map {
        (it + 1) / (lambda * 5)
    }

    override fun conditions() = lambda > 0
    override fun probabilityDensityFunction(x: Float) = lambda * exp(-lambda * x)
    override fun mean() = 1 / lambda
    override fun standardDeviation() = 1 / lambda
    override fun getParameters(): Map<String, Float> = mapOf("lambda" to lambda)
    override fun setParameters(args: List<Float>) {
        if(!conditions()) return
        lambda = args[0]
    }
}

class Poisson(private var lambda: Float = 1f) : Distribution {
    init {
        require(conditions()) {
            "Lambda parameter must be greater than 0"
        }
    }

    override fun range() = (0..100).map { it.toFloat() }.filter { if (lambda < 2) it <= 10 else it <= 20 }
    override fun conditions() = lambda > 0
    override fun probabilityDensityFunction(x: Float): Float {
        val xf = factorial(x.toInt())
        return lambda.pow(x) * exp(-lambda) / xf
    }

    override fun mean() = lambda
    override fun standardDeviation() = sqrt(lambda)
    override fun getParameters(): Map<String, Float> = mapOf("lambda" to lambda)
    override fun setParameters(args: List<Float>) {
        if(!conditions()) return
        lambda = args[0]
    }
}

class Pareto(private var xm: Float = 1f, private var alpha: Float = 10f) : Distribution {
    init {
        require(conditions()) {
            "Mode and alpha parameter must be higher than 0"
        }
    }

    override fun range() = (0..100).map {
        1f + it / (10 * alpha)
    }

    override fun conditions() = xm > 0 && alpha > 0
    override fun probabilityDensityFunction(x: Float) = alpha * xm.pow(alpha) / x.pow(alpha + 1)

    override fun mean() = if (alpha <= 1) Float.POSITIVE_INFINITY else alpha * xm / (alpha - 1)

    override fun standardDeviation() =
        if (alpha <= 2) Float.POSITIVE_INFINITY else xm * sqrt(alpha) / ((alpha - 1) * sqrt(alpha - 2))

    override fun getParameters(): Map<String, Float> = mapOf("xm" to xm, "alpha" to alpha)
    override fun setParameters(args: List<Float>) {
        if(!conditions()) return
        xm = args[0]
        alpha = args[1]
    }
}

class Gauss(private var mean: Float = 0f, private var sigma: Float = 1f) : Distribution {
    init {
        require(conditions()) {
            "Sigma must be higher than 0"
        }
    }

    override fun range() = (0..100).map {
        mean + sigma * (50 - it) / 10
    }

    override fun conditions() = sigma > 0
    override fun probabilityDensityFunction(x: Float) =
        (1 / sigma * sqrt(2 * PI) * exp(-0.5 * ((x - mean) / sigma).pow(2))).toFloat()

    override fun mean() = mean
    override fun standardDeviation() = sigma
    override fun getParameters(): Map<String, Float> = mapOf("mean" to mean, "sigma" to sigma)
    override fun setParameters(args: List<Float>) {
        if(!conditions()) return
        mean = args[0]
        sigma = args[1]
    }
}

class LogNormal(private var mean: Float = 0f, private var sigma: Float = 1f) : Distribution {
    init {
        require(conditions()) {
            "Sigma must be higher than 0"
        }
    }

    override fun range() = (0..100).map {
        mean + sigma * (it + 1) / 10
    }

    override fun conditions() = sigma > 0
    override fun probabilityDensityFunction(x: Float) =
        (1 / (x * sigma * sqrt(2 * PI.toFloat()))) * exp(-1 * (ln(x) - mean).pow(2) / (2 * sigma.pow(2)))

    override fun mean() = exp(mean + sigma.pow(2) / 2)
    override fun standardDeviation() = sqrt(exp(sigma.pow(2) - 1) * exp(2 * mean + sigma.pow(2)))

    override fun getParameters(): Map<String, Float> = mapOf("mean" to mean, "sigma" to sigma)
    override fun setParameters(args: List<Float>) {
        if(!conditions()) return
        mean = args[0]
        sigma = args[1]
    }
}

class Gumbel(private var mean: Float = 0f, private var beta: Float = 1f) : Distribution {
    private val eulerMascheroni = 0.57721f
    init {
        require(conditions()) {
            "Beta must be higher than 0"
        }
    }

    override fun range() = (0..100).map {
        it / (beta * 10)
    }

    override fun conditions() = beta > 0

    override fun probabilityDensityFunction(x: Float): Float {
        val z = (x - mean) / beta
        return (1 / beta) * exp(-1 * (z + exp(-z)))
    }

    override fun mean(): Float {
        return mean + beta * eulerMascheroni
    }

    override fun standardDeviation() = PI.toFloat() * beta / 6
    override fun getParameters(): Map<String, Float> = mapOf("mean" to mean, "beta" to beta)
    override fun setParameters(args: List<Float>) {
        if(!conditions()) return
        mean = args[0]
        beta = args[1]
    }
}

