package days

import kotlin.test.Test
import kotlin.test.assertEquals

abstract class DayTest {

    abstract val day: Day
    abstract val part1: Any
    abstract val part2: Any

    @Test
    fun partOne() = assertEquals(part1, day.partOne())

    @Test
    fun partTwo() = assertEquals(part2, day.partTwo())
}
