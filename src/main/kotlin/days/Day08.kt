package days

import util.lcm
import util.println
import util.readInput

fun main() {
    val day = "08"

    fun parse(input: List<String>) = input.drop(2).associate {
        val line = it.split(" = ")
        val targets = line.last().drop(1).dropLast(1).split(", ")
        line.first() to mapOf('L' to targets.first(), 'R' to targets.last())
    }

    fun String.solveForNode(
        instructions: String,
        nodes: Map<String, Map<Char, String>>
    ): Long {
        var cur = this
        var steps = 0
        while (!cur.endsWith('Z')) {
            val ins = instructions[steps++ % instructions.length]
            cur = nodes[cur]!![ins]!!
        }
        return steps.toLong()
    }

    fun part1(input: List<String>): Long {
        val (instructions, network) = input[0] to parse(input)
        return "AAA".solveForNode(instructions, network)
    }

    fun part2(input: List<String>): Long {
        val (instructions, network) = input[0] to parse(input)
        return network.filter { it.key.endsWith('A') }
            .map { it.key.solveForNode(instructions, network) }
            .reduce(::lcm)
    }

    val testInput1 = readInput("test_input_day_${day}_part_1")
    val testInput2 = readInput("test_input_day_${day}_part_2")
    val input = readInput("input_day_${day}")

    check(part1(testInput1) == 6L)
    part1(input).println()

    check(part2(testInput2) == 6L)
    part2(input).println()
}
