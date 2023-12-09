package days

import util.lcm

class Day08 : Day(8) {

    override fun partOne(): Long {
        val (instructions, network) = inputList[0] to parse(inputList)
        return "AAA".solveForNode(instructions, network)
    }

    override fun partTwo(): Long {
        val (instructions, network) = inputList[0] to parse(inputList)
        return network.filter { it.key.endsWith('A') }
            .map { it.key.solveForNode(instructions, network) }
            .reduce(::lcm)
    }

    private fun parse(input: List<String>) = input.drop(2).associate {
        val line = it.split(" = ")
        val targets = line.last().drop(1).dropLast(1).split(", ")
        line.first() to mapOf('L' to targets.first(), 'R' to targets.last())
    }

    private fun String.solveForNode(
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
}
