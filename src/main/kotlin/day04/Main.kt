package day04;

import java.io.File

fun get_list(str: String) : Pair<List<String>,List<String>>
{
    var first_part_removed = str.split(":").last().split("|");
    var list = first_part_removed[0].trim().split(" ")
    var list2 = first_part_removed[1].trim().split(" ")
    return Pair(list.filter { it.isNotEmpty() },list2.filter { it.isNotEmpty() })
    //return first_part_removed;
}

fun get_points_of_scratch(pair: Pair<List<String>,List<String>>) : Int
{
    var points = 0;
    pair.first.forEach { str ->
        if (str in pair.second) {
            points++;
        }
    }
    return points;
}

fun get_id(str: String) : Int
{
    var id = str.split(":").first().split(" ").last().toInt();
    return id;
}
fun main() {


    val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day4\\input.txt";
    var total = 0;
    var map = mutableMapOf<Int, Int>();
    var id = 1;
    try {
        for(j in 1 until 205)
            map[j] = 1;
        File(filePath).forEachLine { line ->
            // Process each line here
            var pair = get_list(line);
            id = get_id(line);
            var points = get_points_of_scratch(pair)
            var current_scratchcards = map[id];

            for (i in 1 until points+1)
            {
                map[id+i] = map[id+i]!! + 1*current_scratchcards!!;
                //println(i)
            }
            total += points
            //println("First row: $points")

        }

        // REMEMBER TO REMOVE ALL LINES AFTER
        for(k in id+1 until map.size+1)
            map.remove(k)

        val sumOfValues = map.values.sum()
        println(sumOfValues)
    } catch (e: Exception) {
        println("An error occurred: ${e.stackTrace}")
    }

}
