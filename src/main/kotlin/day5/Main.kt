package day5
import java.io.File

fun get_next_number_from_map(map: MutableList<List<Long?>>, nr: Long): Long
{
    var result: Long = nr;
    for(value in map)
    {
        val dest_range = value[0]!!
        val source_range = value[1]!!
        val range = value[2]!!

        if(nr >= source_range && nr <= source_range+range)
        {
            val diff = nr - source_range
            result = (diff + dest_range)
            break
        }

    }
    return result;
}

fun reverse_get_next_number_from_map(map: MutableList<List<Long?>>, nr: Long): Long
{
    var result: Long = nr;
    for(value in map)
    {
        val dest_range = value[0]!!
        val source_range = value[1]!!
        val range = value[2]!!

        if(nr >= dest_range && nr <= dest_range+range)
        {
            val diff = nr - dest_range
            result = (diff + source_range)
            break
        }

    }
    return result;
}

fun extract_map(str: String) : List<Long>
{
    var result = str.split(" ").map { it.toLong() }
    return result;
}
fun new_seeds(numbers: List<Long>) : List<Long>
{
    var result = mutableListOf<Long>();
    for (index in numbers.indices step 2) {
        val element = numbers[index]
        val nextelement = numbers[index+1]
        val listOfInts = (element..nextelement+element-1L).toList()
        result.addAll(listOfInts);
    }
    return result;
}

fun get_initial_seeds(str: String):  List<Long>
{
    var result = str.split(":")[1].trim().split(" ").map { it.toLong() }
    return result;
}

fun pair_seeds(numbers: List<Long>) : List<Pair<Long,Long>>
{
    var result = mutableListOf<Pair<Long,Long>>();
    for (index in numbers.indices step 2) {
        val element = numbers[index].toLong()
        val nextelement = numbers[index+1].toLong()
        var pair = Pair(element, (element+nextelement).toLong());
        result.add(pair);
    }
    return result;
}
fun find_if_seed_exists(seeds: List<Pair<Long,Long>>, nr: Long) : Boolean
{

    seeds.forEach { (key, value) ->
        if(key <= nr && nr < value)
            return true;
    }
    return false;
}

fun reverse_it(seeds: List<Long>, map: List<MutableList<List<Long?>>>)
{
    var count = 1L
    var pairs = pair_seeds(seeds);

    var maxSize = seeds.max() + 1L

    while (count < maxSize) {

        var test = count.toLong();
        for(value in 0 until map.size)
        {
            test = reverse_get_next_number_from_map(map[value], test);
        }
        var found = find_if_seed_exists(pairs, test);
        if(found)
        {
            println(count)
            break
        }

        count++
    }
}

fun get_all_maps(fileName: String) : MutableList<MutableList<List<Long?>>>
{
    var result = mutableListOf(mutableListOf(listOf<Long?>()));
    var current_map = mutableListOf(listOf<Long?>());
    result.clear();

    try {

        current_map.clear();
        File(fileName).forEachLine { line ->
            if("map" !in line && "seeds" !in line){

                if(line == "")
                {
                    if(current_map.size > 0)
                    {
                        result.add(current_map);
                        current_map = mutableListOf(listOf<Long?>());
                        current_map.clear();
                    }
                }
                else
                {
                    var row = extract_map(line);
                    current_map.add(row);
                }

            }
        }
        return result
    }
    catch (e: Exception) {
        println("An error occurred: ${e.stackTrace}")
    }

    return result;
}

fun main()
{

    fun part2()
    {

        // Record the start time
        val startTime = System.currentTimeMillis()

        val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day5\\input.txt";
        val firstLine = File(filePath).bufferedReader().useLines { lines ->
            lines.firstOrNull()
        }
        var seeds = get_initial_seeds(firstLine!!)
        var maps = get_all_maps(filePath)
        var reversed_map = maps.reversed()
        var answer = reverse_it(seeds, reversed_map);
        //var answer2 = reverse_it(maps);
        // Record the end time
        val endTime = System.currentTimeMillis()

        // Calculate the elapsed time
        val elapsedTime = endTime - startTime

        println("My function took $elapsedTime milliseconds to run.")

    }

    part2()

    fun part1() {
        val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day5\\input.txt";
        var next_map = false;
        var first = true;
        var map_found = false;
        var numbers: List<Long> = mutableListOf()

        var map = mutableListOf(listOf<Long?>());
        map.removeAt(0)


        try {
            File(filePath).forEachLine { line ->
                if(first)
                {
                    numbers = get_initial_seeds(line);
                    numbers = new_seeds(numbers);
                    first = false;
                }
                if("map" in line){
                    // Needed?
                    next_map = true;
                    map_found = true;
                }
                else if(map_found && line != "")
                {
                    var row = extract_map(line);
                    map.add(row);
                    //numbers = numbers.map { get_next_number_from_row(row, it) }
                }
                // This means end of map(unless its first)
                if(line == "" && map_found)
                {
                    // Loop all numbers with map?
                    numbers = numbers.map { get_next_number_from_map(map, it) }
                    //println(numbers)
                    map.clear();
                }
            }

            println(numbers.min())
        } catch (e: Exception) {
            println("An error occurred: ${e.stackTrace}")
        }
    }

}