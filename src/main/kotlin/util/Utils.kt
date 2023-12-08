package util

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
// TODO: refactor to use resources - check InputReader.kt
fun readInput(name: String) = Path("src/main/kotlin/resources/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

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
