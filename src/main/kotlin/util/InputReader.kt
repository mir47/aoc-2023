package util

import java.io.File

object InputReader {

    fun getInputAsString(day: Int): String {
        return fromResources(day).readText()
    }

    fun getInputAsList(day: Int): List<String> {
        return fromResources(day).readLines()
    }

    private fun fromResources(day: Int): File {
        val d = day.toString().padStart(2, '0')
        val file = "input_day_$d.txt"
        return File(javaClass.classLoader.getResource(file).toURI())
    }
}
