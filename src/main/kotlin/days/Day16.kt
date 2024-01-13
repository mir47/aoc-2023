package days

class Day16 : Day(16) {

    override fun partOne(): Int {
        return solve(Coord(0, 0, Move.E))
    }

    override fun partTwo(): Int {
        val l = inputList.indices.maxOf { solve(Coord(0, it, Move.E)) }
        val r = inputList.indices.maxOf { solve(Coord(inputList.first().lastIndex, it, Move.W)) }
        val u = inputList.first().indices.maxOf { solve(Coord(it, 0, Move.S)) }
        val d = inputList.first().indices.maxOf { solve(Coord(it, inputList.lastIndex, Move.N)) }
        return maxOf(l, r, u, d)
    }

    private fun solve(start: Coord): Int {
        val energized = Array(inputCharArray.size) { Array(inputCharArray.first().size) { '.' } }
        val visited = mutableListOf<Coord>()
        val beams = mutableListOf(start)
        while (beams.isNotEmpty()) {
            val split = mutableListOf<Coord>()
            beams.forEach { cur ->
                energized[cur.y][cur.x] = '#'
                visited.add(Coord(cur.x, cur.y, cur.move))
                val test = inputCharArray[cur.y][cur.x]
                when (test) {
                    '.' -> {
                        when (cur.move) {
                            Move.S -> cur.y++
                            Move.N -> cur.y--
                            Move.E -> cur.x++
                            Move.W -> cur.x--
                        }
                    }
                    '|' -> {
                        when (cur.move) {
                            Move.S -> cur.y++
                            Move.N -> cur.y--
                            else -> {
                                split.add(Coord(cur.x, cur.y+1, Move.S))
                                cur.y--
                                cur.move = Move.N
                            }
                        }
                    }
                    '-' -> {
                        when (cur.move) {
                            Move.E -> cur.x++
                            Move.W -> cur.x--
                            else -> {
                                split.add(Coord(cur.x+1, cur.y, Move.E))
                                cur.x--
                                cur.move = Move.W
                            }
                        }
                    }
                    '/' -> {
                        when (cur.move) {
                            Move.E -> {
                                cur.y--
                                cur.move = Move.N
                            }
                            Move.W -> {
                                cur.y++
                                cur.move = Move.S
                            }
                            Move.S -> {
                                cur.x--
                                cur.move = Move.W
                            }
                            Move.N -> {
                                cur.x++
                                cur.move = Move.E
                            }
                        }
                    }
                    '\\' -> {
                        when (cur.move) {
                            Move.E -> {
                                cur.y++
                                cur.move = Move.S
                            }
                            Move.W -> {
                                cur.y--
                                cur.move = Move.N
                            }
                            Move.S -> {
                                cur.x++
                                cur.move = Move.E
                            }
                            Move.N -> {
                                cur.x--
                                cur.move = Move.W
                            }
                        }
                    }
                }
            }

            beams.addAll(split)
            beams.removeAll {
                it in visited ||
                        (it.x < 0 || it.x > inputCharArray.first().lastIndex ||
                                it.y < 0 || it.y > inputCharArray.lastIndex)
            }
        }

        var count = 0
        energized.forEach { row ->
            row.forEach {
                if (it == '#') count++
            }
        }
        return count
    }

    enum class Move { N, S, E, W }

    data class Coord(var x: Int, var y: Int, var move: Move)

}
