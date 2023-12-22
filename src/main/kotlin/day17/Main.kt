package main.kotlin.day17

import com.sun.jdi.IntegerValue
import day16.Direction
import day16.cache
import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day17\\testinput.txt";
val input = File(filePath).readLines()

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

data class Coordinate(val x:Int, val y:Int)
fun getNodeValue(coord: Coordinate):Int{
    return input[coord.y].get(coord.x).digitToInt()
}

fun getNextCoords(currentCoord: Coordinate): List<Pair<Coordinate, Direction>> {
    return listOf(
        Pair(Coordinate(currentCoord.x - 1, currentCoord.y),Direction.WEST),
        Pair(Coordinate(currentCoord.x + 1, currentCoord.y),Direction.EAST),
        Pair(Coordinate(currentCoord.x, currentCoord.y-1),Direction.NORTH),
        Pair(Coordinate(currentCoord.x, currentCoord.y+1),Direction.SOUTH)
    )
}
fun search(coord: Coordinate, nr:Int, dir: Direction, visited: HashSet<Coordinate>): Int{

    if(visited.contains(coord))
        return -1
    if(nr > 3)
        return -1
    if(coord.x == input[0].length-1 && coord.y == input.size -1)
        return getNodeValue(coord)
    if (coord.x in 0 until input[0].length) {
        if (coord.y in 0 until input.size) {
            var currentValue = Int.MAX_VALUE
            // 4 directions
            val list = getNextCoords(coord)
            list.forEach {
                var length = nr
                if(it.second == dir)
                    length++
                var clonedSet = HashSet(visited)
                clonedSet.add(coord)
                val asd = search(it.first,length,it.second, clonedSet)
                if(asd != Int.MAX_VALUE && asd != -1)
                    if(asd < currentValue)
                        currentValue =  asd + getNodeValue(coord)
            }

            if(currentValue != Int.MAX_VALUE)
                return currentValue
            return -1
        }
    }
    return -1
}
fun main(){

    fun Part1(){
        var set = HashSet<Coordinate>();
        val res = search(Coordinate(0,0), 0, Direction.NORTH, set)
        println(res)
    }

    Part1()
}