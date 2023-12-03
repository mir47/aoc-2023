package days

import util.println
import util.readInput

fun main() {
    val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        .withIndex().associateBy ({ it.value }, { it.index+1 })

    fun solve(input: List<String>): Int = input.sumOf { line ->
        val digits = line.filter { it.isDigit() }
        (digits.first().digitToInt() * 10) + digits.last().digitToInt()
    }

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

    fun part1(input: List<String>) = solve(input)

    fun part2(input: List<String>) = solve(parse(input))

    val testInput1 = readInput("test_input_day_01_part_1")
    check(part1(testInput1) == 142)

    val input = readInput("input_day_01")
    part1(input).println()

    val testInput2 = readInput("test_input_day_01_part_2")
    check(part2(testInput2) == 281)
    part2(input).println()
}
