package search

import java.io.File
import java.nio.charset.Charset

fun find(map: MutableMap<String, MutableList<Int>>, list: MutableList<String>): String {
    println("\nSelect a matching strategy: ALL, ANY, NONE")
    val mod = readln().lowercase()
    println("\nEnter a name or email to search all matching people.")
    val str = readln().lowercase().split(' ').toMutableList()
    var res = "NULL"
    when (mod) {
        "all" -> {
            for (s in str) {
                if (map.containsKey(s) && !res.contains(s, ignoreCase = true)) {
                    res = "${map.getOrDefault(s, mutableListOf()).size} person found:\n"
                    for (i in map.getOrDefault(s, mutableListOf())) res += "${list[i]}\n"
                }
            }
            if (res.contains("NULL")) res = "No matching people found."
        }
        "any" -> {
            var s1: String = ""
            var count = 0
            for (s in str)
                if (map.containsKey(s) && !s1.contains(s, ignoreCase = true)) {
                    for (i in map.getOrDefault(s, mutableListOf())) {
                        s1 += "${list[i]}\n"
                        ++count
                    }
                }
            res = if (count != 0 ) "$count person found:\n$s1" else "No matching people found."
        }
        else -> {
            var l = mutableListOf<Int>()
            for (s in str)
                if (map.containsKey(s)) for (i in map.getOrDefault(s, mutableListOf())) l.add(i)
            l.sort()
            for (i in l.size - 1 downTo 0) list.removeAt(l[i])
            res = if (l.size == 0) "No matching people found."
            else "${list.size} person found:\n${list.joinToString("\n")}"

        }
    }
    return res
}

fun printAll(list: MutableList<String>): String {
    println("\n=== List of people ===")
    for (i in list) println(i)
    return ""
}

fun main(args: Array<String>) {
    val file = File(args[1])
    var list = file.readLines(charset = Charset.defaultCharset()).toMutableList()
    val map = mutableMapOf<String, MutableList<Int>>()
    for (i in list.indices) {
        val arr = list[i].split(' ')
        for (j in arr)
            if (map.containsKey(j.lowercase())) map.getOrDefault(j.lowercase(), mutableListOf())
                .add(i) else map += j.lowercase() to mutableListOf(i)
    }
    //for (a in map) println("${a.key} -> ${a.value}")
    var choice: String
    do {
        println("\n=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
        choice = readln()
        when (choice) {
            "1" -> println(find(map, list))
            "2" -> printAll(list)
            "0" -> println("\nBye!")
            else -> println("\nIncorrect option! Try again.")
        }
    } while (choice != "0");
}