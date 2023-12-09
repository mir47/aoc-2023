package days

import util.println
import util.readInput

fun main() {
    val day = "09"

    fun parse(input: List<String>) = input.map { it.split(' ').map { it.toInt() } }

    fun List<Int>.deltas() = windowed(2) { it.last() - it.first() }

    fun List<Int>.extrapolate(): Int = if (all { it == 0 }) 0 else {
        deltas().extrapolate() + last()
    }

    fun part1(input: List<String>) = parse(input).sumOf { it.extrapolate() }

    fun part2(input: List<String>) = parse(input).sumOf { it.reversed().extrapolate() }

    val testInput = readInput("test_input_day_${day}")
    val input = readInput("input_day_${day}")

    check(part1(testInput) == 114)
    part1(input).println()

    check(part2(testInput) == 2)
    part2(input).println()
}
