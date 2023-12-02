package days

import util.println
import util.readInput

fun main() {
    val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun calculate(input: List<String>): Int = input
        .map { line -> line.filter { it.isDigit() } }
        .map { line -> "${line.first()}${line.last()}" }
        .sumOf { it.toInt() }

    fun parse(input: List<String>): List<String> = input.map { line ->
        var parsed = ""
        line.forEachIndexed { idx, letter ->
            if (letter.isDigit()) {
                parsed += letter
            } else {
                numbers.forEach { number ->
                    if (line.substring(idx).startsWith(number.key)) {
                        parsed += number.value.toString()
                    }
                }
            }
        }
        parsed
    }

    fun part1(input: List<String>): Int {
        return calculate(input)
    }

    fun part2(input: List<String>): Int {
        return calculate(parse(input))
    }

    val testInput1 = readInput("test_input_day_01_part_1")
    check(part1(testInput1) == 142)

    val input = readInput("input_day_01")
    part1(input).println()

    val testInput2 = readInput("test_input_day_01_part_2")
    check(part2(testInput2) == 281)
    part2(input).println()
}