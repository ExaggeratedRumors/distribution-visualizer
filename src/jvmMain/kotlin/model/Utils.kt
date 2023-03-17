package model

fun factorial(x: Int) : Int {
    return when(x) {
        0, 1 -> 1
        else -> (1..x).reduce { v, w -> v * w }
    }
}