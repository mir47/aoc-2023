package days

import util.println
import util.readInput
import java.io.File

fun main_mine() {
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

    // TODO: this works but takes waaay too long...
    //  should solve for ranges and not for single paths
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

    val testInput = readInput("test_input_day_${day}")
    val input = readInput("input_day_${day}")

    check(part1(testInput) == 35L)
    part1(input).println()

    check(part2(testInput) == 46L)
    part2(input).println()
}

data class Mapping(val first: Long, val last: Long, val offset: Long) {
    fun range() = first..last
}

/**
 * solution from: https://github.com/ondrsh/AdventOfCode2023/blob/master/src/main/kotlin/Day5.kt
 */
fun main() {
    val blocks = File("src/main/kotlin/resources/input_day_05.txt").readText().split("\n\n")
    val seeds = blocks.first().substringAfter(": ").split(" ").map { it.toLong() }
    val seedRanges = seeds.chunked(2).map { (start, length) -> start..start + length }
    val rangeMaps = blocks.filter { it.contains("map") }.map { mapBlock ->
        mapBlock.split("\n").drop(1).map { line ->
            val (dst, src, length) = line.split(" ").map { it.toLong() }
            (src until src + length) to (dst until dst + length)
        }.toMap()
    }

    fun mapRange(
        input: LongRange,
        rangeMap: Map<LongRange, LongRange>
    ): List<LongRange> = buildList {
        val outputs = rangeMap.mapNotNull { (src, dst) ->
            val start = maxOf(src.first, input.first)
            val end = minOf(src.last, input.last)
            val shift = dst.first - src.first
            if (end < start) null else (start + shift)..(end + shift)
        }.ifEmpty { listOf(input.first..input.last) }
        addAll(outputs)
        if (size > 1) {
            val theirMin = rangeMap.minOf { it.key.first }
            val theirMax = rangeMap.maxOf { it.key.last }
            if (input.first < theirMin) add(input.first until theirMin)
            if (input.last > theirMax) add(theirMax + 1..input.last)
        }
    }

    fun List<LongRange>.mapRangesIteratively(): Long = flatMap { startingRange ->
        rangeMaps.fold(listOf(startingRange)) { ranges, rangeMap ->
            ranges.flatMap { range -> mapRange(range, rangeMap) }
        }
    }.minOf { it.first }

    val ans1 = seeds.map { it..it }.mapRangesIteratively()
    val ans2 = seedRanges.mapRangesIteratively()
    println(ans1)
    println(ans2)
}
