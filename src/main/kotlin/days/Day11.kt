package days

import kotlin.math.abs

class Day11 : Day(11) {

    override fun partOne() = solve(2)

    override fun partTwo() = solve(1_000_000)

    private fun solve(times: Int): Long {
        val galaxies = parse()

        val emptyX = mutableSetOf<Int>()
        val emptyY = mutableSetOf<Int>()

        (0..inputList[0].lastIndex).forEach { i ->
            if (galaxies.none { i == it.x }) emptyX.add(i)
            if (galaxies.none { i == it.y }) emptyY.add(i)
        }

        var total = 0L
        galaxies.forEach { g1 ->
            galaxies.forEach { g2 ->
                if (g1.id < g2.id) {
                    total += g1.distance(g2, times, emptyX, emptyY)
                }
            }
        }
        return total
    }

    private fun parse(): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        inputList.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '#') {
                    galaxies.add(Galaxy(galaxies.size + 1, x, y))
                }
            }
        }
        return galaxies
    }

    data class Galaxy(val id: Int, val x: Int, val y: Int) {
        fun distance(other: Galaxy, times: Int, emptyX: Set<Int>, emptyY: Set<Int>): Int {
            val xRange = minOf(x, other.x)..maxOf(x, other.x)
            val yRange = minOf(y, other.y)..maxOf(y, other.y)

            val xCount = emptyX.count { it in xRange }
            val yCount = emptyY.count { it in yRange }

            val xDist1 = abs(x - other.x) - xCount
            val yDist1 = abs(y - other.y) - yCount

            val xDist2 = times * xCount
            val yDist2 = times * yCount

            return xDist1 + xDist2 + yDist1 + yDist2
        }
    }
}
