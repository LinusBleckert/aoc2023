package day11

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day11\\input.txt";
val map = File(filePath).readLines()


fun doubleColumn(data: List<String>): List<Int>{

    var res = mutableListOf<String>()

    for(i in 0 until data[0].length){
        val stringBuilder = StringBuilder()
        data.forEach {
            stringBuilder.append(it[i])
        }
        res.add(stringBuilder.toString())
    }

    var toBeInverted = doubleRow(res)
    return toBeInverted
    /*
    res = mutableListOf<String>()
    for(i in 0 until toBeInverted[0].length){
        val stringBuilder = StringBuilder()
        toBeInverted.forEach {
            stringBuilder.append(it[i])
        }
        res.add(stringBuilder.toString())
    }
    return res
    */
}

fun doubleRow(data: List<String>):List<Int>{

    //var res = mutableListOf<String>()
    var indexesWithLongStuff = mutableListOf<Int>()
    data.forEachIndexed{index, it->
        var containsGalaxy = false
        it.forEachIndexed { index2, ch ->
            if(ch != '.')
                containsGalaxy = true
        }
        if(!containsGalaxy)
            indexesWithLongStuff.add(index)
    }
    return indexesWithLongStuff;
}

data class Coordinate(val index:Int, val x:Int, val y:Int)
fun Coordinate.GetDistance(diff: Coordinate, rowLongTraverse:List<Int>, columnLongTraverse:List<Int>, traverseLength:Int): Int{

    // Calculate row and column differences

    val columnTraverse = (minOf(this.x, diff.x) until maxOf(this.x, diff.x)).toList()
    val rowTraverse = (minOf(this.y, diff.y) until maxOf(this.y, diff.y)).toList()

    var rowSize = 0
    rowTraverse.forEach {
        if(rowLongTraverse.contains(it))
            rowSize += traverseLength
        else
            rowSize+=1
    }
    var columnSize = 0
    columnTraverse.forEach {
        if(columnLongTraverse.contains(it))
            columnSize += traverseLength
        else
            columnSize+=1
    }

    return (rowSize + columnSize)

}

fun main(){

    fun Part1(){
        var rowLongTraverse = doubleRow(map)
        var columnLongTraverse = doubleColumn(map)

        val traverseSize = 1000000
        val list = mutableListOf<Coordinate>();
        map.forEachIndexed() { indexY, it ->
            it.forEachIndexed { indexX, ch ->
                if(ch == '#')
                    list.add(Coordinate(list.size + 1, indexX, indexY))
            }
        }

        var sum = 0UL
        for(i in 0 until list.size){
            for(j in i+1 until list.size){
                var start = list[i]
                var end = list[j]
                var distance = start.GetDistance(end, rowLongTraverse, columnLongTraverse,traverseSize)
                //if(distance<shortest)
                //shortest = distance
                sum += distance.toULong()
            }
            //println(shortest)
        }
        println("Sum")
        println(sum)
    }

    Part1()
}