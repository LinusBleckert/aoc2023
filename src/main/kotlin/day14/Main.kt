package day14

import java.io.File


val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day14\\testinput.txt";
val input = File(filePath).readLines()


fun rotateInput(input: List<String>): List<String> {
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

fun slideStones(data: List<String>): List<String> {

    return data.map {
        var split = it.split('#')
        var result = split.map{
            var stones = it.fold(0){acc, value ->
                acc + if (value == 'O') 1 else 0
            }
            var stringBuilder = StringBuilder();
            for(i in 0 until it.length - stones)
                stringBuilder.append('.')
            for(i in 0 until stones)
                stringBuilder.append('O')
            stringBuilder.toString()
        }.reduce { acc, s -> "$acc#$s" }
        result
    }

}

fun calculateScore(data:List<String>):ULong{
    var res = 0UL
    for(i in 0 until data.size){
        var stones = data[i].fold(0){acc, value ->
            acc + if (value == 'O') 1 else 0
        }
        res += stones.toULong() * (data.size - i).toULong()
    }
    return res
}

fun reverseData(data: List<String>):List<String>{

    return data.map {
        it.reversed()
    }

}

fun rotateAccordingToPart2(data: List<String>):List<String>{
    return reverseData(rotateInput(reverseData(rotateInput(reverseData(rotateInput(data))))))
}

fun rotatePlus90(data: List<String>):List<String>{
    var mutableList = mutableListOf<String>()
    for(i in 0 until data[0].length){
        var row = ""
        for(j in 0 until data.size){
            row += data[data.size - j - 1][i]
        }
        mutableList.add(row)
    }
    return mutableList.toList()
}

fun rotateMinus90(data: List<String>):List<String>{
    var mutableList = mutableListOf<String>()
    for(i in 0 until data[0].length){
        var row = ""
        for(j in 0 until data.size){
            row += data[j][data[0].length - i - 1]
        }
        mutableList.add(row)
    }
    return mutableList.toList()
}

fun main(){

    fun Part1(){

        //var res = slideStones(input)

        var north = reverseData(rotateInput(input))
        north.forEach {
            println(it)
        }
        var data = slideStones(north)
        println()
        data.forEach {
            println(it)
        }
        var backToNormal = rotateInput(data)
        backToNormal.forEach {
            println(it)
        }
        println(calculateScore(backToNormal))
    }

    //Part1()

    fun Part2(){

        fun printStuff(data: List<String>){
            data.forEach {
                println(it)
            }
        }

        var start = input
        println()
        var cycle = 1
        for(i in 0UL until 200UL*4UL) {
            when(i % 4UL){
                // This is what?
                0UL->{
                    start = rotatePlus90(start)
                    start = slideStones(start)
                    start = rotateMinus90(start)
                }
                1UL->{
                    start = rotatePlus90(start)
                    start = rotatePlus90(start)
                    start = slideStones(start)
                    start = rotateMinus90(start)
                    start = rotateMinus90(start)
                }
                2UL -> {
                    start = rotatePlus90(start)
                    start = rotatePlus90(start)
                    start = rotatePlus90(start)
                    start = slideStones(start)
                    start = rotateMinus90(start)
                    start = rotateMinus90(start)
                    start = rotateMinus90(start)
                }
                3UL->{
                    start = slideStones(start)
                    val score = calculateScore(start)
                    println("$cycle : $score")
                    cycle++
                }
            }
        }

    }
    Part2()
}
