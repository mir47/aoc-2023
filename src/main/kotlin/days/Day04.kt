package days

import util.println
import util.readInput
import kotlin.math.pow

fun main() {
    val day = "04"

    fun parse(input: List<String>): List<Card> {
        val cards = mutableListOf<Card>()
        input.forEach { line ->
            val card = line.split(':')
            val numbers = card.last().split('|')
            val win = numbers.first().trim().split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }.toSet()
            val pick = numbers.last().trim().split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }.toSet()
            cards.add(Card(win, pick))
        }

        return cards
    }

    fun part1(input: List<String>) = parse(input)
        .map { it.winningNumbers() }
        .filter { it.isNotEmpty() }
        .sumOf { 2.0.pow(it.size - 1) }
        .toInt()

    fun part2(input: List<String>): Long {
        val cards = parse(input)
        val m = cards.withIndex()
            .associate { it.index+1 to it.value }
            .toMutableMap()

        val mm = mutableMapOf<Int, Long>()
        mm[1] = 1

        val lastCard = m.size
        m.forEach { (cardId, card) ->
            mm[cardId] = 1
        }

        m.forEach { (cardId, card) ->
            val next = cardId+1
            var winningNumbers = card.winningNumbers().size
            if (cardId + winningNumbers > lastCard) {
                winningNumbers = lastCard - cardId - 1
            }
            var final = next + winningNumbers - 1

            for (i in next..final) {
                mm[i] = (mm[i] ?: 1) + (mm[cardId] ?: 0)
            }
        }

        return mm.values.sum()
    }

    val testInput1 = readInput("test_input_day_${day}")
    check(part1(testInput1) == 13)

    val input = readInput("input_day_${day}")
    part1(input).println()

    val testInput2 = readInput("test_input_day_${day}")
    check(part2(testInput2) == 30L)
    part2(input).println()
}

data class Card(val win: Set<Int>, val pick: Set<Int>) {
    fun winningNumbers() = win intersect pick
}
