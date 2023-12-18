package day15

import java.io.File
import java.lang.IllegalArgumentException

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day15\\input.txt";
val input = File(filePath).readText().split(',')
val hash = listOf("HASH");

fun getAsciiValue(char: Char):Int{
    return char.code
}

data class algo(val box: Int, val operation: Char, var focalLength: Int, val label: String)



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
    //Part1()

    fun Part2(){
        val hashmap = mutableMapOf<Int, MutableList<algo>>()
        // Populate the hashmap with keys as integers and values from 0 to 255
        for (i in 0..255) {
            hashmap[i] = mutableListOf<algo>()
        }

        var asd = input.map{

            var label = it.substring(0,it.length-2)
            var operation = it[it.length-2]
            var focal = -1
            if(it.last().isDigit()){
                focal = it.last().digitToInt()
            }
            else{
                operation = it[it.length-1]
                label = it.substring(0,it.length-1)
            }
            val box = label.fold(0){
                acc, value->
                (((acc + getAsciiValue(value))*17) % 256)
            }
            algo(box, operation, focal, label)
        }//.reduce{acc, value -> acc + value}

        asd.forEach {
            if(it.operation=='='){
                var list = hashmap[it.box]
                // this is wrong, need to loop list and see if its label exists?
                var replaced = false
                list!!.forEach{ algoInBox ->
                    if(algoInBox.label == it.label){
                        replaced = true
                        //remove it?
                        algoInBox.focalLength = it.focalLength
                    }
                }
                if(!replaced)
                    list.add(it)


            }
            else{
                var list = hashmap[it.box]
                var toBeRemoved: algo? = null
                list!!.forEach{ algoInBox ->
                    if(algoInBox.label == it.label)
                        //remove it?
                        toBeRemoved = algoInBox
                }
                if(toBeRemoved != null)
                    list.remove(toBeRemoved)
                hashmap[it.box] = list
            }
        }

        var result = 0
        for ((key, value) in hashmap) {

            if(value.isNotEmpty()){
                println("Key: $key, Value: $value")
                value.forEachIndexed{ index, box ->
                    val part = ((key+1)*(index+1)*box.focalLength)
                    println(part)
                    result += part
                }
            }
        }
        println(result)
    }

    Part2()
}