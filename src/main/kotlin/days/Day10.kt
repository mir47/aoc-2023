package days

class Day10 : Day(10) {

    private val possibleMoves = mapOf(
        Direction.LEFT to listOf('-', 'L', 'F'),
        Direction.RIGHT to listOf('-', 'J', '7'),
        Direction.UP to listOf('|', '7', 'F'),
        Direction.DOWN to listOf('|', 'L', 'J'),
    )

    init {
//        check(partOne() == 6886)
//        check(partTwo() == 2)

//        File(".gitignore").readLines().forEach { it.println() }
//        println()
//        File("src/main/kotlin/days/Day01.kt").readLines().forEach { it.println() }
//        println()

    }

    override fun partOne() = solve().size/2

    override fun partTwo(): Int {
        var count = 0

        clean().forEach { line ->
            var inLoop = false
            var corner1 = ' '
            line.forEach { tile ->
                when (tile) {
                    '|' -> inLoop = !inLoop
                    'L', 'F' -> {
                        corner1 = tile
                    }
                    '7' -> {
                        if (corner1 == 'L') inLoop = !inLoop
                        corner1 = ' '
                    }
                    'J' -> {
                        if (corner1 == 'F') inLoop = !inLoop
                        corner1 = ' '
                    }
                    '.' -> {
                        if (inLoop) count++
                    }
                    else -> {}
                }
            }
        }

        return count
    }

    private fun clean(): List<String> {
        val loop = solve()
        val cleaned = mutableListOf<String>()

        inputList.forEachIndexed { y, line ->
            var clean = ""
            line.forEachIndexed { x, tile ->
                val t = if (tile == 'S') 'L' else tile // L
                clean += if (Tile(x, y) in loop) t else "."
            }
            cleaned.add(clean)
            println(clean)
        }
        println()

        return cleaned
    }

    private fun solve(): List<Tile> {
        val start = findStart()
        var cur = start
        val loop = mutableListOf(cur)
        var prevDir = Direction.NONE

        if (cur.x > 0 && check(Direction.LEFT, Tile(cur.x - 1, cur.y))) {
            cur = Tile(cur.x - 1, cur.y)
            loop.add(cur)
            prevDir = Direction.RIGHT
        } else if (check(Direction.RIGHT, Tile(cur.x + 1, cur.y))) {
            cur = Tile(cur.x + 1, cur.y)
            loop.add(cur)
            prevDir = Direction.LEFT
        } else if (check(Direction.UP, Tile(cur.x, cur.y - 1))) {
            cur = Tile(cur.x, cur.y - 1)
            loop.add(cur)
            prevDir = Direction.DOWN
        } else if (check(Direction.DOWN, Tile(cur.x, cur.y + 1))) {
            cur = Tile(cur.x, cur.y + 1)
            loop.add(cur)
            prevDir = Direction.UP
        }

        var searching = true
        while (searching) {

            when (inputList[cur.y][cur.x]) {
                '|' -> {
                    if (prevDir == Direction.UP) {
                        val next = Tile(cur.x, cur.y + 1)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.UP
                        }
                    } else if (prevDir == Direction.DOWN) {
                        val next = Tile(cur.x, cur.y - 1)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.DOWN
                        }
                    }
                }
                '-' -> {
                    if (prevDir == Direction.LEFT) {
                        val next = Tile(cur.x + 1, cur.y)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.LEFT
                        }
                    } else if (prevDir == Direction.RIGHT) {
                        val next = Tile(cur.x - 1, cur.y)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.RIGHT
                        }
                    }
                }
                'L' -> {
                    if (prevDir == Direction.UP) {
                        val next = Tile(cur.x + 1, cur.y)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.LEFT
                        }
                    } else if (prevDir == Direction.RIGHT) {
                        val next = Tile(cur.x, cur.y - 1)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.DOWN
                        }
                    }
                }
                'J' -> {
                    if (prevDir == Direction.UP) {
                        val next = Tile(cur.x - 1, cur.y)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.RIGHT
                        }
                    } else if (prevDir == Direction.LEFT) {
                        val next = Tile(cur.x, cur.y - 1)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.DOWN
                        }
                    }
                }
                '7' -> {
                    if (prevDir == Direction.LEFT) {
                        val next = Tile(cur.x, cur.y + 1)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.UP
                        }
                    } else if (prevDir == Direction.DOWN) {
                        val next = Tile(cur.x - 1, cur.y)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.RIGHT
                        }
                    }
                }
                'F' -> {
                    if (prevDir == Direction.RIGHT) {
                        val next = Tile(cur.x, cur.y + 1)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.UP
                        }
                    } else if (prevDir == Direction.DOWN) {
                        val next = Tile(cur.x + 1, cur.y)
                        if (isEnd(next)) {
                            searching = false
                        } else {
                            loop.add(next)
                            cur = next
                            prevDir = Direction.LEFT
                        }
                    }
                }
            }
        }
        return loop
    }

    enum class Direction { NONE, UP, DOWN, LEFT, RIGHT }

    data class Tile(val x: Int, val y: Int)

    private fun check(d: Direction, next: Tile): Boolean {
        val nextTile = inputList[next.y][next.x]
        return (nextTile in (possibleMoves[d]!!))
    }

    private fun isEnd(next: Tile) = inputList[next.y][next.x] == 'S'

    private fun findStart(): Tile {
        inputList.forEachIndexed { row, line ->
            line.forEachIndexed { col, tile ->
                if (tile == 'S') return Tile(col, row)
            }
        }
        return Tile(0, 0)
    }
}
