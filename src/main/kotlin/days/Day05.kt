package days

import util.println
import util.readInput

fun main() {
    val day = "05"

    fun parse(input: List<String>): MutableMap<String, MutableList<Mapping>> {
        val map = mutableMapOf<String, MutableList<Mapping>>()
        var mapName = ""
        input.forEachIndexed { i, line ->
            if (i != 0 && line.isNotEmpty()) {
                if (!line[0].isDigit()) {
                    mapName = line
                    map[mapName] = mutableListOf()
                } else {
                    val ls = line.split(' ')
                    val destination = ls[0].toLong()
                    val source = ls[1].toLong()
                    val length = ls[2].toLong()
                    val m = Mapping(
                        first = source,
                        last = source + length - 1,
                        offset = destination - source
                    )
                    map[mapName]?.add(m)
                }
            }
        }
        return map
    }

    fun part1(input: List<String>): Long {
        val map4 = parse(input)
        val seeds: MutableList<MutableList<Long>> = mutableListOf()
        val l = input[0].split(':')[1].split(' ')
            .filter { it.isNotEmpty() }.map { it.toLong() }
        l.forEach { seeds.add(mutableListOf(it)) }

        seeds.forEach { seed ->
            map4.forEach { maps ->
                maps.value
                    .find { seed.last() in it.range() }
                    ?.let { seed.add(seed.last() + it.offset) }
                    ?: run { seed.add(seed.last()) }
            }
        }

        return seeds.minOf { it.last() }
    }

    fun part2(input: List<String>): Long {
        val map4 = parse(input)
        val l = input[0].split(':')[1].split(' ')
            .filter { it.isNotEmpty() }.map { it.toLong() }
        var min = Long.MAX_VALUE
        l.windowed(2, 2).forEach {
            (it[0]..<it[0] + it[1]).forEach { seed ->
                var track = seed
                map4.forEach { maps ->
                    maps.value
                        .find { ms -> track in ms.range() }
                        ?.let { f -> track += f.offset }
                }
                min = minOf(min, track)
            }
        }

        return min
    }

    val testInput1 = readInput("test_input_day_${day}")
    check(part1(testInput1) == 35L)

    val input = readInput("input_day_${day}")
    part1(input).println()

    val testInput2 = readInput("test_input_day_${day}")
    check(part2(testInput2) == 46L)
    part2(input).println()
}

data class Mapping(val first: Long, val last: Long, val offset: Long) {
    fun range() = first..last
}
