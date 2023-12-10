package day10

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day10\\testinput.txt";

val map = File(filePath).readLines()



fun getNextPositionsFromTile(ch: Char):List<Pair<Int,Int>>{

    // Return all possible stuff, Create a map with all tiles in maze, replace lowest number of steps
    // Loop all
    when(ch) {
        '|' ->
            return listOf(Pair(1,0),Pair(-1,0))
        '-' ->
            return listOf(Pair(0,-1),Pair(0,1))
        'L' ->
            return listOf(Pair(0,1),Pair(-1,0))
        'J' ->
            return listOf(Pair(0,-1),Pair(-1,0))
        '7' ->
            return listOf(Pair(0,-1),Pair(1,0))
        'F' ->
            return listOf(Pair(0,1),Pair(1,0))
        else ->
            return emptyList()
    }
}

fun findCharPosition(char: Char, stringList: List<String>): Pair<Int, Int>? {
    stringList.forEachIndexed { rowIndex, str ->
        str.forEachIndexed { colIndex, currentChar ->
            if (currentChar == char) {
                return Pair(rowIndex, colIndex)
            }
        }
    }
    return null
}

fun replaceSWithCorrectChar(start:Pair<Int,Int>, map:List<String>){

    // check if surrounding connects?
    // Loop getNextPositionsFromTile and check which returns our pos?

}

fun main(){

    val startPos = findCharPosition('S', map)
    println(startPos)
    // Replace S?

}