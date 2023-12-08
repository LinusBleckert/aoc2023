package day8
import java.io.File

data class Location(val location: String, val left:String, val right:String)
val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day8\\input.txt";

val directions = File(filePath).readLines().first().toCharArray();
val map = File(filePath).useLines { lines ->
    lines.drop(2).map { line ->
            val threeCharWords = "\\b\\w{3}\\b".toRegex().findAll(line)
                .map { it.value }
                .toList()
                threeCharWords[0] to Location(threeCharWords[0],threeCharWords[1],threeCharWords[2])
            }.toMap()
}

fun AllLocationEndInZ(locations: List<Location>): Boolean{
    locations.forEach {
        if(!it.location.endsWith('Z'))
            return false
    }
    return true
}

fun GetAllStartLocation(locations: List<Location>): MutableList<Location>{
    return locations.filter {
        it.location.last() == 'A'
    }.toMutableList()
}

fun findLowestCommonMultiple(numbers: List<ULong>): ULong {
    require(numbers.isNotEmpty()) { "Input list must not be empty" }
    return numbers.reduce { acc, num -> lcm(acc, num) }
}

fun gcd(a: ULong, b: ULong): ULong = if (b == 0UL) a else gcd(b, a % b)

fun lcm(a: ULong, b: ULong): ULong = if (a == 0UL || b == 0UL) 0UL else (a * b) / gcd(a, b)

fun main(){
    fun StepsRequired(location22: Location):ULong{
        var steps = 0UL
        var location = location22
        for (i in 0 until Int.MAX_VALUE) {
            directions.forEach {

                if (it == 'L')
                    location = map[location.left]!!
                else if (it == 'R')
                    location = map[location.right]!!
                else
                    throw Exception("ASD")
                steps++
                if (AllLocationEndInZ(listOf(location))){
                    return steps
                }
            }
        }
        return steps
    }

    var numbers = mutableListOf<ULong>()
    GetAllStartLocation(map.values.toList()).forEach{ location ->
        var nr = StepsRequired(location)
        numbers.add(nr)
    }
    val lowestMultiple = findLowestCommonMultiple(numbers)
    println("Lowest common multiple: $lowestMultiple")
    //println(sum)
}