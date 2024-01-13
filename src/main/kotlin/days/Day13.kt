package days

import util.transposeStrings

class Day13 : Day(13) {

    override fun partOne() = solve(0)

    override fun partTwo() = solve(1)

    private fun solve(smudge: Int): Long {
        var result = 0L
        inputString.split("\r\n\r\n").forEach { b ->
            val p = b.split("\r\n").filter { it.isNotEmpty() }
            val sol = search(p, smudge)
            if (sol == 0L) {
                val q = p.transposeStrings()
                result += 100 * search(q, smudge)
            } else {
                result += sol
            }
        }
        return result
    }

    private fun search(block: List<String>, smudge: Int): Long {
        val line = block.first()
        line.indices.forEach { i ->
            val j = line.length - ((line.length - (i + 1)) * 2)
            val l1 = if (i < line.length/2) 0 else j
            val l2 = i + 1
            val r1 = i + 1
            val r2 = if (i < line.length/2) ((i + 1) * 2) else line.length

            val l = line.substring(l1, l2)
            val r = line.substring(r1, r2).reversed()

            val z = l.zip(r)
            val diff = z.count { it.first != it.second }
            val check = l == r || diff == smudge
            if (check && i < line.lastIndex) {
                var c = 0
                block.forEach {
                    val ll = it.substring(l1, l2)
                    val rr = it.substring(r1, r2).reversed()
                    ll.indices.forEach { lli ->
                        if (ll[lli] != rr[lli]) c++
                    }
                }
                if (c == smudge) return l2.toLong()
            }
        }
        return 0
    }
}
