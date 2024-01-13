package util

import java.io.File

object InputReader {

    fun getInputAsString(day: Int): String {
        return fromResources(day).readText()
    }

    fun getInputAsList(day: Int): List<String> {
        return fromResources(day).readLines()
    }

    private fun fromResources(day: Int, part: Int = 0): File {
        val d = day.toString().padStart(2, '0')
        val p = if (part > 0) "_part_$part" else ""
        val file = "input_day_$d$p.txt"
        return File(javaClass.classLoader.getResource(file).toURI())
    }
}
