package days

import util.println
import util.readInput

fun main() {
    val day = "07"

    fun parse(input: List<String>) = input
        .map { line -> line.split(' ').let { it.first() to it.last() } }
        .map { (cards, bid) -> Hand(cards, bid.toLong()) }

    fun List<Hand>.solve() = sorted().map { it.bid }
        .reduceIndexed { index, acc, bid -> acc + ((index + 1) * bid) }

    fun part1(input: List<String>) = parse(input)
        .solve()

    fun part2(input: List<String>) = parse(input)
        .map { it.apply { withJoker = true } }
        .solve()

    val testInput = readInput("test_input_day_${day}")
    val input = readInput("input_day_${day}")

    check(part1(testInput) == 6440L)
    part1(input).println()

    check(part2(testInput) == 5905L)
    part2(input).println()
}

data class Hand(val cards: String, val bid: Long = 0): Comparable<Hand> {

    var withJoker = false

    override fun compareTo(other: Hand): Int {
        val t1 = if (withJoker) cards.improve().type() else cards.type()
        val t2 = if (withJoker) other.cards.improve().type() else other.cards.type()
        if (t1 > t2) return 1
        if (t1 < t2) return -1
        else {
            cards.indices.forEach {
                val s1 = cards[it].strength(withJoker)
                val s2 = other.cards[it].strength(withJoker)
                if (s1 > s2) return 1
                if (s1 < s2) return -1
            }
        }
        return 0
    }
}

fun String.improve() = toCharArray().distinct()
    .map { this.replace('J', it) }
    .maxOf { Hand(it) }
    .cards

fun String.type() = groupingBy { it }.eachCount().let { counts ->
    val pairs = counts.values.filter { it == 2 }
    when {
        5 in counts.values -> 7
        4 in counts.values -> 6
        3 in counts.values -> if (2 in counts.values) 5 else 4
        pairs.size == 2 -> 3
        pairs.size == 1 -> 2
        else -> 1
    }
}

fun Char.strength(withJoker: Boolean): Int = when (this) {
    'A' -> 14
    'K' -> 13
    'Q' -> 12
    'J' -> if (withJoker) 1 else 11
    'T' -> 10
    else -> this.digitToInt()
}
