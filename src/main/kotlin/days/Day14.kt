package days

import util.transposeStrings

class Day14 : Day(14) {

//    override fun partOne(): Long {
//        val l = mutableListOf<List<Pair<Int, Int>>>()
//        val t = inputList.transposeStrings()
//        t.forEach {
//            val ll = mutableListOf<Pair<Int, Int>>()
//            var cubeIdx = it.length + 1
//            var round = 0
//            it.forEachIndexed { i, c ->
//                when (c) {
//                    'O' -> round++
//                    '#' -> {
//                        ll.add(Pair(cubeIdx, round))
//                        cubeIdx = it.length - i
//                        round = 0
//                    }
//                    else -> { }
//                }
//            }
//            ll.add(Pair(cubeIdx, round))
//            l.add(ll)
//        }
//
//        var count = 0L
//        l.forEach { col ->
//            col.forEach {
//                if (it.second > 0) {
//                    val end = it.first - 1
//                    val start = end - it.second + 1
//                    count += (start..end).sum()
//                }
//            }
//        }
//
//        return count
//    }

    override fun partOne(): Long {
        val north = inputList.transposeStrings()
        return calcLoad(findPositions(tilt(north)))
    }

    /*
    .N.
    W.E
    .S.

    transpose -> N
    .W.
    N.S
    .E.

    start

    tilt !

    transpose -> W !
    .N.
    W.E
    .S.

    transpose -> N
    .W.
    N.S
    .E.

    reverse -> S !
    .W.
    S.N
    .E.

    transpose -> W
    .S.
    W.E
    .N.

    reverse -> E !
    .S.
    E.W
    .N.

    reverse -> W
    .S.
    W.E
    .N.

    transpose -> S
    .W.
    S.N
    .E.

    reverse -> N (load)
    .W.
    N.S
    .E.


    repeat

     */
    override fun partTwo(): Long {
        var control = inputList.transposeStrings()
        var dish = control // N
        // solve for N
        dish = tilt(dish)

        var load = 0L
        var found = false
        var cycle = 0
        var score = mutableListOf<Long>()
        var scoreC = mutableListOf<Long>()
        var score1 = mutableListOf<Long>()
        var ci = 0
        var foundStart = false


        while (!found) {
//        while (!found && cycle <4) {
            cycle++

            dish = dish.transposeStrings()
            // solve for W
            dish = tilt(dish)
            dish = dish.transposeStrings()
            dish = dish.map { it.reversed() }
            // solve for S
            dish = tilt(dish)
            dish = dish.transposeStrings()
            dish = dish.map { it.reversed() }
            // solve for E
            dish = tilt(dish)
            // W
            dish = dish.map { it.reversed() }
            // S
            dish = dish.transposeStrings()
            // N
            dish = dish.map { it.reversed() }

            dish = dish.transposeStrings()
            println("")
            println("cycle: $cycle")
            dish.forEach {
                println(it)
            }
            dish = dish.transposeStrings()

//            var diff = false
//            dish.forEachIndexed { index, s ->
//                val ss = control[index]
//                if (s != ss) {
//                    diff = true
//                }
//            }

//            if (diff) {
//                control = dish
//            } else {
////                val calc = calcLoad(findPositions(dish))
////                if (load == calc || cycle == 3) {
////                    found = true
////                } else {
////                    load = calc
////                }
//
//                found = true
//                return calcLoad(findPositions(dish))
//            }

            // solve for N
            dish = tilt(dish)

            val cc = calcLoad(findPositions(dish))

            if (foundStart) {
                score1.add(cc)
                if (ci + score1.lastIndex <= score.lastIndex) {
                    score1.forEachIndexed { index, l2 ->
                        val l1 = score[ci + index]
                        if (l2 != l1) {
                            foundStart = false
                        } else if (ci + index == score.lastIndex) {

//                            val head = score.size - score1.size
//                            val cycles = 1_000_000_000
//                            val rem = cycles - head
//                            val times = rem / score1.size
//                            val last = cycles - (head + (score1.size * times))
//
//
//                            println()

                            foundStart = false
                            score += score1
                            score1 = mutableListOf()
                        }
                    }
                    if (!foundStart) {
                        score += score1
                        score1 = mutableListOf()
                    }
                } else {
                    score += score1
                    foundStart = false
                    score1 = mutableListOf()
                }
            } else {
                if (cc in score) {
                    foundStart = true
                    ci = score.lastIndexOf(cc)
                    score1.add(cc)
                } else {
                    score.add(cc)
                }
            }

        }

        return load
    }

    private fun findPositions(input: List<String>): List<List<Pair<Int, Int>>> {
        val l = mutableListOf<List<Pair<Int, Int>>>()
        input.forEach {
            val ll = mutableListOf<Pair<Int, Int>>()
            var cubeIdx = it.length + 1
            var round = 0
            it.forEachIndexed { i, c ->
                when (c) {
                    'O' -> round++
                    '#' -> {
                        ll.add(Pair(cubeIdx, round))
                        cubeIdx = it.length - i
                        round = 0
                    }
                }
            }
            ll.add(Pair(cubeIdx, round))
            l.add(ll)
        }
        return l
    }

    private fun calcLoad(input: List<List<Pair<Int, Int>>>): Long {
        var count = 0L
        input.forEach { col ->
            col.forEach {
                if (it.second > 0) {
                    val end = it.first - 1
                    val start = end - it.second + 1
                    count += (start..end).sum()
                }
            }
        }
        return count
    }

    private fun tilt(input: List<String>): List<String> {
        val l = mutableListOf<String>()
        input.forEach {
            var round = 0
            var dot = 0
            var s = ""
            it.forEach { c ->
                when (c) {
                    '.' -> dot++
                    'O' -> round++
                    '#' -> {
                        repeat(round) { s += "O" }
                        repeat(dot) { s += "." }
                        s += "#"
                        round = 0
                        dot = 0
                    }
                }
            }
            repeat(round) { s += "O" }
            repeat(dot) { s += "." }
            l.add(s)
        }
        return l
    }
}
