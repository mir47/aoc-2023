package days

import util.println
import util.readInput

fun main() {
    val day = "03"

    fun parse(input: List<String>): Schematic {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()

        input.forEachIndexed { i, line ->
            var num = ""
            var xStart = -1
            line.forEachIndexed { j, c ->
                if (c.isDigit()) {
                    num += c
                    if (xStart == -1) xStart = j
                } else {
                    if (num.isNotBlank()) {
                        numbers.add(Number(value = num, xStart = xStart, xEnd = j-1, y = i))
                        num = ""
                        xStart = -1
                    }
                    if (c != '.') {
                        symbols.add(Symbol(value = c, x = j, y = i))
                    }
                }

                if (j == line.lastIndex && num.isNotBlank()) {
                    numbers.add(Number(value = num, xStart = xStart, xEnd = j, y = i))
                    num = ""
                    xStart = -1
                }
            }
        }

        return Schematic(numbers, symbols)
    }

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = parse(input)
        return numbers
            .filter { n -> symbols.any { s -> s.isAdjacent(n) } }
            .sumOf { it.value.toInt() }
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = parse(input)
        return symbols.filter { it.value == '*' }
            .map { numbers.filter { n -> it.isAdjacent(n) } }
            .filter { it.size == 2 }
            .sumOf { it[0].value.toInt() * it[1].value.toInt() }
    }

    val testInput1 = readInput("test_input_day_${day}")
    check(part1(testInput1) == 4361)

    val input = readInput("input_day_${day}")
    part1(input).println()

    val testInput2 = readInput("test_input_day_${day}")
    check(part2(testInput2) == 467835)
    part2(input).println()
}

data class Number(var value: String, var xStart: Int = -1, var xEnd: Int = -1, var y: Int) {
    fun xRange() = xStart-1..xEnd+1
    fun yRange() = y-1..y+1
}

data class Symbol(val value: Char, val x: Int, val y: Int) {
    fun isAdjacent(n: Number) = x in n.xRange() && y in n.yRange()
}

data class Schematic(val numbers: List<Number>, val symbols: List<Symbol>)
