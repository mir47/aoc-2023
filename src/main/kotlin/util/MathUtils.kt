package util

/**
 * Calculate the least common multiple of two numbers.
 */
fun lcm(a: Long, b: Long) = a * ( b/gcd(a, b) )

/**
 * Calculate the greatest common divisor of two numbers.
 */
fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    if (a < 0 || b < 0 || a + b <= 0) {
        throw IllegalArgumentException("GCD Error: a=$a, b=$b")
    }

    while (a > 0 && b > 0) {
        if (a >= b) {
            a %= b
        } else {
            b %= a
        }
    }

    return maxOf(a, b)
}
