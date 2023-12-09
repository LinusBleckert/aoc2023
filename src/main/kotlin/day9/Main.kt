package day9

import day8.Location
import java.io.File



val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day9\\input.txt";

val map = File(filePath).useLines { lines ->
    lines.map {
        it.trim().split(" ").map { it.toInt() }.toList()
    }.toList()
}

fun AllElementsInListTheSame(list: List<Int>):Boolean {
    return list.all { it == list[0] }
}

fun extraPolateNextValue(data: List<List<Int>>): Int{

    var res = 0
    data.forEach {
        res += it.last
    }
    return res
}

fun extraPolatePreviousValue(data: List<List<Int>>): Int{

    var res = 0
    //var data2 = data.reversed()
    data.forEachIndexed{i, value ->
        if(i != data.size)
            if(i == 0)
                res = value.first
            else{
                res = data[i].first - res
            }
        else
            return res
    //println(value)
    }
    return res
}

fun getNextRow(row: List<Int>): MutableList<List<Int>>{
    val mutableList = mutableListOf<Int>();
    for(i in 0 until row.size-1){
        mutableList.add(row[i+1] - row[i])
    }
    if (AllElementsInListTheSame(mutableList))
        return mutableListOf(mutableList)
    else{
        var res = getNextRow(mutableList)
        res.add(mutableList)
        return res
    }
}


fun main(){

    fun Part1():Int{
        return map.map {
            var data = getNextRow(it)
            data.add(it)
            extraPolateNextValue(data)
        }.sum()
    }

    //print(Part1())

    fun Part2():Int{

        return map.map {

            var data = getNextRow(it)
            data.add(it)
            var res = extraPolatePreviousValue(data)
            //println(res)
            res
        }.sum()
    }

    println(Part2())

}