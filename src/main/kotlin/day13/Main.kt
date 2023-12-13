package day13

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day13\\input.txt";

fun initMirrors(file: File): MutableList<MutableList<String>> {
    var mirrors = mutableListOf<MutableList<String>>()
    mirrors.add(mutableListOf<String>())
    file.readLines().forEach {
        if(it != "")
            mirrors.last.add(it)
        else{
            mirrors.add(mutableListOf<String>())
        }
    }
    return mirrors
}


fun getHorizontalReflects(line: Int, mirror:List<String>):Boolean{
    val above = mirror.subList(0, line).reversed()
    val below = mirror.subList(line, mirror.size)
    val loops = minOf(above.size, below.size)
    for(i in 0 until loops){
        if(above[i] != below[i])
            return false
    }
    return true
}

fun switchCharacters(input: List<String>): List<String> {
    var mutableList = mutableListOf<String>()
    for(i in 0 until input[0].length){
        var row = ""
        for(j in 0 until input.size){
            row += input[j][i]
        }
        mutableList.add(row)
    }
    return mutableList.toList()
}

fun getReflectionLine(mirror: List<String>):ULong{
    var result = 0UL
    for(i in 1 until mirror.size) {
        if (getHorizontalReflects(i, mirror)) {
            result = i.toULong()
            break
        }
    }
    return result

}

fun oneDifferent(str1: List<String>, str2: List<String>):Int{
    var differences = 0
    var indexDifferent = -1
    val loops = minOf(str1.size, str2.size)
    for (i in 0 until loops) {
        for (j in 0 until str1[i].length) {
            if (str1[i].get(j) != str2[i].get(j)) {
                differences++
                indexDifferent = i
                if (differences > 1) {
                    return -1  // More than one difference found
                }
            }
        }
    }
    return indexDifferent
}
fun checkDifferences(mirror: List<String>):Int{
    for(i in 1 until mirror.size) {
        val above = mirror.subList(0, i).reversed()
        val below = mirror.subList(i, mirror.size)
        //

        val line = oneDifferent(above,below)
        if(line != -1)
            return i
    }
    return -1
}
fun main(){

    fun Part1(){

        var mirrors = initMirrors(File(filePath))
        var result = 0UL
        mirrors.forEach {
            var rowResult = getReflectionLine(it)
            result += (rowResult*100UL)
            val mirrorInverted = switchCharacters(it)
            var columnResult = getReflectionLine(mirrorInverted)
            result += columnResult
        }

        println("TOTAL")
        println(result)
    }
    //Part1()

    fun Part2(){
        var mirrors = initMirrors(File(filePath))
        /*
        var firstMirror = mirrors[1]

        var originalRowResult = getReflectionLine(firstMirror)
        val mirrorInverted = switchCharacters(firstMirror)
        var originalColumnResult = getReflectionLine(mirrorInverted)
        // TODO, change every char and go?
        // Start by brute force ?
        // Check differences in list of string, if only one difference it should work
        val lineVertical = checkDifferences(firstMirror)
        val lineHorizontal = checkDifferences(mirrorInverted)
        if(lineVertical != -1)
            println(lineVertical)
        if(lineHorizontal != -1)
            println(lineHorizontal)
        */
        var result = 0UL
        mirrors.forEach {
            var rowResult = getReflectionLine(it)
            val mirrorInverted = switchCharacters(it)
            var columnResult = getReflectionLine(mirrorInverted)
            // Check differences in list of string, if only one difference it should work
            val lineVertical = checkDifferences(it)
            val lineHorizontal = checkDifferences(mirrorInverted)
            // How could it be same? Bug in my stuff?
            /*
            if(rowResult == lineVertical.toULong()){
                println("Is same??")
                it.forEach {
                    println(it)
                }
            }
            if(columnResult == lineHorizontal.toULong()){
                println("Is same??")
                it.forEach {
                    println(it)
                }
            }
            */
            if(lineVertical != -1){
                println(lineVertical)
                result += ((lineVertical).toULong()*100UL)
            }else if(lineHorizontal != -1){
                println(lineHorizontal)
                result += ((lineHorizontal).toULong())
            }else{
                println("ASDIOAHNSODINASDNIASND")

            }
        }
        println("TOTAL")
        println(result)
    }

    Part2()
}