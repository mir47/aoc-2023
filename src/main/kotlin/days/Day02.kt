package days

import util.println
import util.readInput

fun main() {
    val day = "02"

    fun parse(input: List<String>): List<Game> {
        val games = mutableListOf<Game>()

        input.forEach { line ->
            val info = line.split(':')
            val id = info.first().filter { it.isDigit() }
            val g = Game(id = id.toInt())
            val sets = info.last().split(';')
            sets.forEach { set ->
                val cubes = Cubes()
                set.split(',').forEach { c ->
                    val number = c.filter { it.isDigit() }.toInt()
                    val colour = c.filter { !it.isDigit() }.trim()
                    when (colour) {
                        "red" -> cubes.r += number
                        "green" -> cubes.g += number
                        "blue" -> cubes.b += number
                    }
                }
                g.cubes.add(cubes)
            }
            games.add(g)
        }

        return games
    }

    fun part1(input: List<String>) = parse(input)
        .filter { it.cubes.all { c -> c.r <= 12 && c.g <= 13 && c.b <= 14 } }
        .sumOf { it.id }

    fun part2(input: List<String>) = parse(input)
        .map { it.findMin() }
        .sumOf { it.power() }

    val testInput1 = readInput("test_input_day_${day}")
    check(part1(testInput1) == 8)

    val input = readInput("input_day_${day}")
    part1(input).println()

    val testInput2 = readInput("test_input_day_${day}")
    check(part2(testInput2) == 2286)
    part2(input).println()
}

data class Cubes(var r: Int = 0, var g: Int = 0, var b: Int = 0) {
    fun power() = r * g * b
}

data class Game(val id: Int, val cubes: MutableList<Cubes> = mutableListOf()) {
    fun findMin() = Cubes(cubes.maxOf { it.r }, cubes.maxOf { it.g }, cubes.maxOf { it.b })
}
