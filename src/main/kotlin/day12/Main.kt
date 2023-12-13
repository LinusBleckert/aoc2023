package day12

import java.io.File

val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day12\\testinput.txt";
val map = File(filePath).readLines()

fun testString2(str: String, list:List<Int>):Int{
    val splitAndRemoveDots = str.split('.').filter { it.isNotEmpty()}
    if(splitAndRemoveDots.size != list.size)
        return 0
    splitAndRemoveDots.forEachIndexed { index, it->
        if(it.length != list[index])
            return 0
    }
    return 1
}
fun testString(str: String, list:List<Int>):Int{
    val splitAndRemoveDots = str.split('.').filter { it.isNotEmpty()}
    if(splitAndRemoveDots.size != list.size)
        return 0
    splitAndRemoveDots.forEachIndexed { index, it->
        if(it.length != list[index])
            return 0
    }
    return 1
}

var cacheMap = HashMap<Pair<String, List<Int>>,ULong>();
// Go down path, if

fun getAmountOfArrangementsWithCache(str: String, list:List<Int>):ULong{
    if (str == "")
        return if (list.isEmpty()) 1UL else 0UL

    if(list.size == 0)
        return if ("#" in str) 0UL else 1UL

    if(Pair(str, list) in cacheMap)
     return cacheMap.get(Pair(str, list))!!

    var result = 0UL

    if(str[0] in ".?")
        result += getAmountOfArrangementsWithCache(str.drop(1), list)
    if(str[0] in "#?")
        if(list[0] <= str.length && '.' !in str.substring(0, list[0]) && (list[0] == str.length || str[list[0]] != '#')){
            //if(list.size == 1 && (str.length == list[0] || str.length == list[0] + 1))
            //    result += getAmountOfArrangementsWithCache("" ,list.drop(1))
            //else{
            if(list[0] >= str.length)
                result += getAmountOfArrangementsWithCache("",list.drop(1))
            else{
                val newString = str.substring(list[0] + 1,str.length)
                val droppedFirst = list.drop(1)
                result += getAmountOfArrangementsWithCache(newString,droppedFirst)
            }
            //}
        }
    cacheMap[Pair(str, list)] = result
    return result
}

// Need to reverse the code kind of
// If the first part is found, remove it from list and that part of the string aswell
// This way it can be properly cached
fun getAmountOfArrangements(str: String, list:List<Int>):Int{
    val questionMarkIndex = str.indexOfFirst { it == '?' }
    if(questionMarkIndex == -1)
        return testString(str,list)
    val splitAndRemoveDots = str.substring(0, questionMarkIndex).split('.').filter { it.isNotEmpty()}
    if(splitAndRemoveDots.size > list.size)
        return 0
    splitAndRemoveDots.forEachIndexed { index, it ->
        if(it.length > list[index])
            return 0
    }
    var res = 0
    val oneSide = getAmountOfArrangements(str.replaceFirst('?', '#'),list)
    val otherSide = getAmountOfArrangements(str.replaceFirst('?', '.'),list)
    res += oneSide
    res += otherSide
    return res
}

fun main(){

    fun ManualTesting(){
        // ???.### 1,1,3
        val str = "????.###"
        val list = listOf(1,1,3)
        var result = getAmountOfArrangements(str, list)
        println(result)
    }

    //val str = "#.#.###"
    //val list = listOf(1,1,3)
    //println(testString(str, list))
    //getAmountOfArrangements(str,list)
    // ???.### 1,1,3
    fun Part1(): ULong{
        return map.fold(0UL) { acc, value ->
            val split = value.split(" ")
            //val normalStr = split[0]
            var str = split[0] + '?'
            str = str + str + str + str + split[0]
            val list = split[1].split(',').map { it.toInt() }
            val list3 = list + list + list + list + list
            var result = getAmountOfArrangements(str, list3)
            //cacheMap.clear()
            println(result)
            result.toULong() + acc

        }
    }

    //Part1()
    var asd = Part1()
    println("Total sum: $asd")
}