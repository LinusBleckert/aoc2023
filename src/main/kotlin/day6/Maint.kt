package day6
import java.io.File

// Time:      7  15   30
// Distance:  9  40  200


data class Race(val distance: Long, val time:Long)

fun Race.GetPossibleSolutions():Long{
    return (0..this.time)
        .map{
            calculate_time(this.distance, it, this.time)
        }.count{
            it
        }.toLong()
}


fun calculate_time(distance: Long, velocity: Long, totalTime:Long):Boolean{
    var distance_traveled = velocity*(totalTime-velocity);
    return (distance_traveled>distance);
}

fun get_race_pairs(input: List<String>):List<Race>
{
    val times = input[0].split(":").last.trim().split("\\s+".toRegex()).map { it.toLong() }
    val distances = input[1].split(":").last.trim().split("\\s+".toRegex()).map { it.toLong() }
    val races = mutableListOf<Race>();
    for(i in 0 until times.size){

        races.add(Race(distances[i],times[i]))
    }
    return races;
}
fun get_race_part2(input: List<String>):Race
{
    val time = input[0].split(":").last.replace("\\s".toRegex(), "").toLong()
    val distance = input[1].split(":").last.replace("\\s".toRegex(), "").toLong()
    return Race(distance, time);
}

val fileName = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day6\\input.txt"

val data1 = get_race_pairs(File(fileName).readLines())
val data2 = get_race_part2(File(fileName).readLines())

fun main(){
    //val race1 = Race(9,7)
    fun part1(){
        val res = data1.map {it.GetPossibleSolutions() }.reduce{acc, value -> acc*value};
        println(res)
    }
    fun part2(){
        println(data2)
        println(data2.GetPossibleSolutions())
    }
    part2()

}