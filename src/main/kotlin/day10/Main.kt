package day10

import java.io.File
import java.util.*
import kotlin.collections.HashSet

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day10\\testinput.txt";

val map = File(filePath).readLines()
val XMaxSize = map[0].length
val YMaxSize = map.size

data class Coordinate(val x:Int, val y:Int)
fun Coordinate.GetNewCoordinate(diff: Coordinate): Coordinate{
    return Coordinate(this.x + diff.x, this.y + diff.y)
}


fun getNextPositionsFromTile(pos: Coordinate):List<Coordinate>{

    val ch = map[pos.y][pos.x]
    // Return all possible stuff, Create a map with all tiles in maze, replace lowest number of steps
    // Loop all
    var pair = mutableListOf<Coordinate>();
    when(ch) {
        '|' ->
            pair = mutableListOf(Coordinate(0,1),Coordinate(0,-1))
        '-' ->
            pair = mutableListOf(Coordinate(1,0),Coordinate(-1,0))
        'L' ->
            pair =  mutableListOf(Coordinate(0,-1),Coordinate(1,0))
        'J' ->
            pair =  mutableListOf(Coordinate(-1,0),Coordinate(0,-1))
        '7' ->
            pair =  mutableListOf(Coordinate(-1,0),Coordinate(0,1))
        'F' ->
            pair =  mutableListOf(Coordinate(1,0),Coordinate(0,1))
        'S' ->
            // S == F
            pair =  mutableListOf(Coordinate(0,1),Coordinate(1,0))
        else ->
            return emptyList()
    }

    return pair.map {
        pos.GetNewCoordinate(it)
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



data class Node(val data: Coordinate,
           var left: Node? = null,
           var right: Node? = null)

fun setCanReachEdge(set: Set<Coordinate>):Boolean{
    set.forEach{
        if(it.x == 0 || it.x == XMaxSize- 1)
            return true
        if(it.y == 0 || it.y == YMaxSize-1)
            return true
    }
    return false
}

var allSurroundingCoords = listOf(
    Coordinate(1,0),
    Coordinate(0,1),
    Coordinate(-1,0),
    Coordinate(0,-1)
)

fun main(){
    //var hashMap = mutableMapOf<Pair<Int,Int>, Int>();

    fun Part1():Set<Coordinate>{
        val startPos = findCharPosition('S', map)
        println(startPos)
        //val rootNode = Node(Coordinate(startPos!!.second, startPos.first), null, null)
        val queue: Queue<Coordinate> = LinkedList()
        var currentCoord = Coordinate(startPos!!.second, startPos.first)
        queue.add(currentCoord)
        queue.poll()
        //val currentNode = rootNode
        val set = HashSet<Coordinate>();
        set.add(currentCoord)
        var found = false

        var i = 0
        var previousNode: Coordinate ?= null
        while(!found){
            var pair = getNextPositionsFromTile(currentCoord)
            var left = pair[0]
            var right = pair[1]
            if(left !in set)
                queue.add(left)
            if(right !in set)
                queue.add(right)
            previousNode = currentCoord
            set.add(currentCoord)
            if(queue.isEmpty())
                return set
            currentCoord = queue.poll()
            i++
            if (currentCoord == previousNode)
                return set

        }
        return set
    }

    var totalSet = HashSet<Coordinate>();
    map.forEachIndexed{indexy, y ->
        y.forEachIndexed {indexx, x ->
            totalSet.add(Coordinate(indexx,indexy))
        }
    }
    var loopSet = Part1()

    var notLoopSet = totalSet-(loopSet)

    //Loop totalset until none remains

    fun GetAllConnectingCoords(startCoord: Coordinate, loopSet: Set<Coordinate>): Set<Coordinate>{
        val queue: Queue<Coordinate> = LinkedList()
        var currentCoord = startCoord
        queue.add(currentCoord)
        val set = HashSet<Coordinate>();
        set.add(currentCoord)
            //var pair = getNextPositionsFromTile(currentCoord)
        while(queue.isNotEmpty()){
            currentCoord = queue.poll()
                allSurroundingCoords.forEach {
                    var newCoord = currentCoord.GetNewCoordinate(it)
                    if(newCoord.x >= 0 && newCoord.x < XMaxSize)
                        if(newCoord.y >= 0 && newCoord.y < YMaxSize)
                            if(newCoord !in set)
                            // If this coord does not exist in loopCoords
                                if(newCoord !in loopSet)
                                    // check for pipe?? lmfao
                                    queue.add(newCoord)
                }
            set.add(currentCoord)
        }
        return set
    }



    val charArray2D = Array(YMaxSize) { CharArray(XMaxSize) }
    var asd = true
    var str = "ASDASDASD"
    //str[2]

    loopSet.forEach {
        charArray2D[it.y][it.x] = map[it.y][it.x]
    }
    //val newMap = mutableListOf(map.)
    while(notLoopSet.isNotEmpty()){
        val currentCoord = notLoopSet.first()
        // Get all connecting coords?
        val asd2 = GetAllConnectingCoords(currentCoord, loopSet)
        notLoopSet = notLoopSet - asd2
        if(setCanReachEdge(notLoopSet))
            notLoopSet.forEach {
                charArray2D[it.y][it.x] = '0'
                //var originalStringBuilder = map[it.y]
                //originalStringBuilder.setCharAt(indexToChange, charToReplace)
                //map.get(it.y) = "asd"
            }
        else
            notLoopSet.forEach {
                charArray2D[it.y][it.x] = '1'
            }

    }
    for (row in charArray2D) {
        for (char in row) {
            print("$char ")
        }
        println()
    }


}