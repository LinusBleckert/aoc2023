package day2
import java.io.File

var initial_state = {

}

// only 12 red cubes, 13 green cubes, and 14 blue cubes
val max_state = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

fun main() {
    // Specify the path to your text file
    val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day2\\testinput.txt";

    // Create a File object
    val file = File(filePath)
    var endValue = 0;
    try {
        // Use forEachLine to loop through each line of the text file
        file.forEachLine { line ->
            // Process each line as needed

            var legal = true;
            var split = line.split(":");
            var currentId = split[0].split(" ")[1].toInt();
            //print(currentId)
            var eachHand = split[1].split(";")
            val mutableMap = HashMap<String, Int>()

            // Adding key-value pairs to the map
            mutableMap["green"] = 0;
            mutableMap["blue"] = 0;
            mutableMap["red"] = 0;
            //println(it)
            eachHand.forEach({
                // Creating a mutable map using the HashMap constructor

                var eachBall = it.split(",");
                for (ball in eachBall){
                    var ballSplit = ball.split(" ");
                    var amount = ballSplit[1].toInt()
                    var color = ballSplit[2];
                    //println("$amount for $color")
                    if(color in max_state)
                    {
                        if(mutableMap[color]!! < amount)
                        {
                            mutableMap[color] = amount;
                        }
                    }
                }

            })
            val product = mutableMap.values.fold(1) { acc, value ->
                acc * value
            }
            println(product)
            if(legal)
                endValue += currentId;
        }
        //print(endValue);
    } catch (e: Exception) {
        // Handle any exceptions that may occur during file processing
        e.printStackTrace()
    }
}