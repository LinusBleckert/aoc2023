package day16

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day16\\input.txt";
val input = File(filePath).readLines()
var cleanData = input.map {
    '.'.toString().repeat(it.length)
}.toMutableList()

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

//fun updateEnergizedCell()

fun getCharFromCurrentCoord(currentCoord: Coordinate): Char{

    return input[currentCoord.y].get(currentCoord.x)
}
fun getNextCoords(dir: Direction, currentCoord: Coordinate):List<Pair<Coordinate,Direction>>{

    when(getCharFromCurrentCoord(currentCoord)){
        '.' ->{
            when(dir){
                Direction.NORTH -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH))
                Direction.SOUTH -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH))
                Direction.WEST -> return listOf(Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST))
                Direction.EAST -> return listOf(Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST))
            }
        }
        '-'->{
            when(dir){
                Direction.NORTH -> return listOf(
                    Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST),
                    Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST)
                )
                Direction.SOUTH -> return listOf(
                        Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST),
                        Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST)
                )
                Direction.WEST -> return listOf(Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST))
                Direction.EAST -> return listOf(Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST))
            }
        }
        '|'->{
            when(dir){
                Direction.NORTH -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH))
                Direction.SOUTH -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH))

                Direction.WEST -> return listOf(
                    Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH),
                    Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH)
                    )
                Direction.EAST -> return listOf(
                    Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH),
                    Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH)
                )
            }
        }
        '/' -> {
            when(dir){
                Direction.NORTH -> return listOf(Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST))
                Direction.SOUTH -> return listOf(Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST))
                Direction.WEST -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH))
                Direction.EAST -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH))
            }
        }
        '\\' -> {
            when(dir){
                Direction.NORTH -> return listOf(Pair(Coordinate(currentCoord.x-1, currentCoord.y),Direction.WEST))
                Direction.SOUTH -> return listOf(Pair(Coordinate(currentCoord.x+1, currentCoord.y),Direction.EAST))
                Direction.WEST -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH))
                Direction.EAST -> return listOf(Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH))
            }
        }
    }

    throw Exception("asd")
}

data class Coordinate(val x:Int, val y:Int)

var cache = mutableSetOf<Pair<Coordinate,Direction>>();

fun energize(coord: Coordinate, data:List<String>, dir:Direction){

    if(cache.contains(Pair(coord,dir)))
        return
    else
        cache.add(Pair(coord,dir))
    if(coord.x in 0 until data[0].length){
        if(coord.y in 0 until data.size){
            var charArray = cleanData[coord.y].toCharArray()
            charArray[coord.x] = '#'
            var str = String(charArray)
            cleanData[coord.y] = str
        }
    }

    val asd = getNextCoords(dir, coord)

    asd.forEach {
        if(it.first.x in 0 until data[0].length){
            if(it.first.y in 0 until data.size) {
                energize(it.first, data, it.second)
            }
        }
    }
}

fun countEnergized():Int{
    var res = 0
    cleanData.forEach {
        it.forEach { ch->
            if(ch == '#')
                res++
        }
    }
    return res
}

fun resetCleanData(){
    cleanData = input.map {
        '.'.toString().repeat(it.length)
    }.toMutableList()
    cache.clear()
}
fun main(){


    fun Part1(){
        cleanData.forEach {
            println(it)
        }
        println("-----------------")
        energize(Coordinate(0,0), input, Direction.EAST)
        cleanData.forEach {
            println(it)
        }

        println()
        println(countEnergized())
    }

    fun Part2(){

        var maxCount = 0

        val maxX = cleanData[0].length
        val maxY = cleanData.size

        for(i in 0 until maxX){
            energize(Coordinate(i,0),input, Direction.SOUTH)
            var res = countEnergized()
            resetCleanData()
            if(res > maxCount)
                maxCount = res
        }

        for(i in 0 until maxX){
            energize(Coordinate(i, maxY-1),input, Direction.NORTH)
            var res = countEnergized()
            resetCleanData()
            if(res > maxCount)
                maxCount = res
        }

        for(i in 0 until maxY){
            energize(Coordinate(0, i),input, Direction.EAST)
            var res = countEnergized()
            resetCleanData()
            if(res > maxCount)
                maxCount = res
        }

        for(i in 0 until maxY){
            energize(Coordinate(maxX-1, i),input, Direction.WEST)
            var res = countEnergized()
            resetCleanData()
            if(res > maxCount)
                maxCount = res
        }

        println("Result")
        println(maxCount)
    }
    Part2()
}
