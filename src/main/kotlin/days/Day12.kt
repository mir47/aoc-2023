package days

class Day12 : Day(11) {

    override fun partOne(): Long {

        return 0L
    }


    private fun parseGroups(groups: String): List<Pair<Char, Int>> {
        val map = mutableListOf<Pair<Char, Int>>()

        return map
    }

    override fun partTwo(): Long {
        return 0L
    }

    enum class State { UNKNOWN, OPERATIONAL, DAMAGED }
}

/*

???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1

*/
