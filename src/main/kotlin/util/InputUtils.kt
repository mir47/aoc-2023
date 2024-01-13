package util

fun String.readInts() = split(' ').map { it.toInt() }

fun List<String>.readInts() = map { it.readInts() }

fun <T>List<List<T>>.transpose(): List<List<T>> {
    return (first().indices).map { i -> indices.map { j -> this[j][i] } }
}

fun List<String>.transposeStrings(): List<String> {
    return map { it.toList() }.transpose().map { it.joinToString("") }
}
