package days

class Day01 : Day(1) {

    override fun partOne() = solve(inputList)

    override fun partTwo() = solve(parse(inputList))

    private val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        .withIndex().associate { it.value to it.index + 1 }

    private fun solve(input: List<String>): Int = input.sumOf { line ->
        val digits = line.filter { it.isDigit() }
        (digits.first().digitToInt() * 10) + digits.last().digitToInt()
    }

    private fun parse(input: List<String>): List<String> = input.map { line ->
        var parsed = ""
        line.forEachIndexed { idx, letter ->
            if (letter.isDigit()) {
                parsed += letter
            } else {
                numbers.forEach { number ->
                    if (line.substring(idx).startsWith(number.key)) {
                        parsed += number.value.toString()
                    }
                }
            }
        }
        parsed
    }
}
