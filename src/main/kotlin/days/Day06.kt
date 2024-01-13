package days

import util.println
import util.readInput

// TODO: simplify code
fun main() {
    val day = "06"

    fun part1(input: List<String>): Long {
        val time = input[0].split(':')[1].split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
        val distance = input[1].split(':')[1].split(' ').filter { it.isNotEmpty() }.map { it.toInt() }

        val races = time.mapIndexed { index, t -> Pair(t, distance[index]) }
        val rr = races.mapIndexed { idx, race ->
            val r = mutableListOf<Int>()
            for (speed in 1..<race.first) {
                val d = speed * (race.first - speed)
                if (d > race.second) r.add(d)
            }
            Pair(idx, r.size.toLong())
        }
        var count = 1L
        rr.forEach { count *= it.second }
        return count
    }

    fun part2(input: List<String>): Long {
        val time = listOf(input[0].split(':')[1].filter { it != ' ' }.toLong())
        val distance = listOf(input[1].split(':')[1].filter { it != ' ' }.toLong())

        val races = time.mapIndexed { index, t -> Pair(t, distance[index]) }
        val rr = races.mapIndexed { idx, race ->
            val r = mutableListOf<Long>()
            for (speed in 1..<race.first) {
                val d = speed * (race.first - speed)
                if (d > race.second) r.add(d)
            }
            Pair(idx, r.size.toLong())
        }
        var count = 1L
        rr.forEach { count *= it.second }
        return count
    }

    val testInput = readInput("test_input_day_${day}")
    val input = readInput("input_day_${day}")

    check(part1(testInput) == 288L)
    part1(input).println()

    check(part2(testInput) == 71503L)
    part2(input).println()
}
