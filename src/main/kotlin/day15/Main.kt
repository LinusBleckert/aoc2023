package day15

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day15\\input.txt";
val input = File(filePath).readText().split(',')
val hash = listOf("HASH");

fun getAsciiValue(char: Char):Int{
    return char.code
}

fun main()
{
    fun Part1(){

        var asd = input.map{
            it.fold(0){ acc , value ->
                ((acc + getAsciiValue(value))*17) % 256
            }
        }.reduce{acc, value -> acc + value}

        println(asd)

    }
    Part1()
}