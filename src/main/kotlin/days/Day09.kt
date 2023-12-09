package days

import util.readInts

class Day09 : Day(9) {

    override fun partOne() = inputList.readInts().sumOf { it.extrapolate() }

    override fun partTwo() = inputList.readInts().sumOf { it.reversed().extrapolate() }

    private fun List<Int>.deltas() = windowed(2) { it.last() - it.first() }

    private fun List<Int>.extrapolate(): Int = if (all { it == 0 }) 0 else {
        deltas().extrapolate() + last()
    }
}
