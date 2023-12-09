package days

import util.println
import util.readInput
import kotlin.math.pow

fun main() {
    val day = "04"

    fun String.parseNumbers() = this
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    fun parse(input: List<String>): List<Pair<Int, Int>> = input
        .mapIndexed { i, line ->
            val n = line.split(':')[1].split('|')
            val win = n[0].parseNumbers().toSet()
            val pick = n[1].parseNumbers().toSet()
            i to (win intersect pick).size
        }

    fun part1(input: List<String>) = parse(input)
        .filter { it.second > 0 }
        .sumOf { 2.0.pow(it.second - 1) }
        .toInt()

    fun part2(input: List<String>): Long {
        val cards = parse(input)
        val result = MutableList<Long>(cards.size) { 1 }
        cards.forEach {
            (1..it.second).forEach { i ->
                result[it.first + i] += result[it.first]
            }
        }
        return result.sum()
    }

    val testInput = readInput("test_input_day_${day}")
    val input = readInput("input_day_${day}")

    check(part1(testInput) == 13)
    part1(input).println()

    check(part2(testInput) == 30L)
    part2(input).println()
}
