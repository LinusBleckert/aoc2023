package day15

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day15\\testinput.txt";
val input = File(filePath).readText().split(',')
val hash = listOf("HASH");

fun getAsciiValue(char: Char):Int{
    return char.code
}

data class algo(val box: Int, val operation: Char, val focalLength: Int, val label: String)



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
            val box = it.substring(0,2).fold(0){
                acc, value->
                (((acc + getAsciiValue(value))*17) % 256)
            }
            if(it.length == 4)
                algo(box, it[2], it[3].digitToInt(), it.substring(0,2))
            else
                algo(box, it[2], -1, it.substring(0,2))
        }//.reduce{acc, value -> acc + value}

        asd.forEach {
            if(it.operation=='='){

                var list = hashmap[it.box]
                if(!list!!.contains(it))
                    hashmap[it.box]!!.add(it)
                else{
                    var toBeRemoved: Int? = null
                    list!!.forEachIndexed{ index, algoInBox ->
                        if(algoInBox.label == it.label)
                        //remove it?
                            toBeRemoved = index
                    }
                    if(toBeRemoved != null){
                        // GEt index and add it ther
                        //list.remove(toBeRemoved)
                        list.remove(list[toBeRemoved!!])
                        list.add(toBeRemoved!!, it)

                    }
                    hashmap[it.box] = list
                }

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