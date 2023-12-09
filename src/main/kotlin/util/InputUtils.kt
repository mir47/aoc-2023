package util

fun String.readInts() = split(' ').map { it.toInt() }

fun List<String>.readInts() = map { it.readInts() }
