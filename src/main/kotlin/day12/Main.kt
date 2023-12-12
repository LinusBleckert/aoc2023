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

var cacheMap = HashMap<Pair<String, List<Int>>,Int>();
// Go down path, if

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
    if(cacheMap.contains(Pair(str.replaceFirst('?', '#'),list)))
        res += cacheMap[Pair(str.replaceFirst('?', '#'),list)]!!
    else{
        val asd = getAmountOfArrangements(str.replaceFirst('?', '#'),list)
        cacheMap[Pair(str.replaceFirst('?', '#'),list)] = asd
        res += asd

    }
    if(cacheMap.contains(Pair(str.replaceFirst('?', '.'),list)))
        res += cacheMap[Pair(str.replaceFirst('?', '.'),list)]!!
    else{
        val asd = getAmountOfArrangements(str.replaceFirst('?', '.'),list)
        cacheMap[Pair(str.replaceFirst('?', '.'),list)] = asd
        res += asd
    }
    //val oneSide = getAmountOfArrangements(str.replaceFirst('?', '#'),list)
    //val otherSide = getAmountOfArrangements(str.replaceFirst('?', '.'),list)
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

    //ManualTesting()
    //return
    //val str = "#.#.###"
    //val list = listOf(1,1,3)
    //println(testString(str, list))
    //getAmountOfArrangements(str,list)
    // ???.### 1,1,3
    fun Part1(): ULong{
        return map.fold(0UL) { acc, value ->

            val split = value.split(" ")
            //val normalStr = split[0]
            //val strEndQM = split[0] + '?'
            val strStartQM = split[0]
            val str = strStartQM// + strStartQM + strStartQM+ strStartQM+ split[0]
            val list = split[1].split(',').map { it.toInt() }
            val list3 = list// + list + list + list + list
            var result = getAmountOfArrangements(str, list3)
            //var resultStart = getAmountOfArrangements(strStartQM, list3)
            //var result = 0UL
            /*if(resultEnd > resultStart)
                result = resultEnd.toULong()
            else
                result = resultStart.toULong()
              */
            // Add ? to end or start, get wahtever is highest and multply by 4?
            //result = result*result*result*result
            //result *= getAmountOfArrangements(normalStr, list).toULong()
            cacheMap.clear()
            println(result)
            result.toULong() + acc
        }
    }

    //Part1()
    var asd = Part1()
    println("Total sum: $asd")
}