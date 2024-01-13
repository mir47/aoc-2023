package days

class Day15 : Day(15) {

    override fun partOne() = parse().map(::hash).sum()

    override fun partTwo(): Int {
        val boxes = List(256) { mutableMapOf<String, Int>() }
        parse().forEach { step ->
            val (label, focalLength) = step.split("=", "-")
            if ('-' in step) {
                boxes[hash(label)] -= label
            } else {
                boxes[hash(label)][label] = focalLength.toInt()
            }
        }

        return boxes.withIndex().sumOf { (i, box) ->
            (i + 1) * box.values.withIndex().sumOf { (j, fl) ->
                (j + 1) * fl
            }
        }
    }

    private fun parse() = inputList.first().split(',')

    private fun hash(string: String) = string.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }
}
