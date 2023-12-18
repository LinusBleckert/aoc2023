package day16

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day16\\testinput.txt";
val input = File(filePath).readLines()
val cleanData = input.map {
    '.'.toString().repeat(it.length)
}.toMutableList()

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

//fun updateEnergizedCell()

fun getCharFromCurrentCoord(currentCoord: Coordinate): Char{

    return input[currentCoord.y].get(currentCoord.x)
}
fun getNextCoords(dir: Direction, currentCoord: Coordinate):Pair<Coordinate,Direction>{

    when(getCharFromCurrentCoord(currentCoord)){
        '.' ->{
            when(dir){
                Direction.NORTH -> return Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH)
                Direction.SOUTH -> return Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH)
                Direction.WEST -> return Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST)
                Direction.EAST -> return Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST)
            }
        }
        '-'->{
            when()
        }
    }

}

data class Coordinate(val x:Int, val y:Int)

fun energize(coord: Coordinate, data:List<String>, dir:Direction){

    if(coord.x in 0 until data[0].length){
        if(coord.y in 0 until data.size){
            val charArray = cleanData[coord.y].toCharArray()
            charArray[coord.x] = '#'
            cleanData[coord.y] = charArray.toString()



        }
    }

    return

}

fun main(){


    fun Part1(){

        energize(Coordinate(0,0), input, Direction.EAST)
    }
}
