package day3
import java.io.File

fun is_valid_symbol(c : Char): Boolean {

    return (c.isDigit() || c == '.')
}

fun is_symbol(c : Char): Boolean {
    return c == '*'
    //return !(c.isDigit() || c == '.')
}
fun is_symbol_adjacent(char : Char): Boolean
{
    return false;
}

fun convert_input_to_char_matrix(filename : String): Array<Array<Char>>
{

    var result = arrayOf<Array<Char>>();

    val file = File(filename)
    try {
        // Use forEachLine to loop through each line of the text file
        file.forEachLine { line ->
            var row = arrayOf<Char>();
            for (char in line) {
                row += (char);
            }

            result += row;
        }


    } catch (e: Exception) {
        // Handle any exceptions that may occur during file processing
        e.printStackTrace()
    }

    return result;
}



fun get_char_array_from_input(str : String) : CharArray
{
    val charArray2 = str.toCharArray()
    return charArray2;
}
fun main() {

    val filePath = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day3\\testinput.txt";
    try {
        val mutableMap = HashMap<String, MutableList<Int>>()
        val char_matrix = convert_input_to_char_matrix(filePath);
        val numCols = char_matrix.size;
        val numRows = char_matrix[0].size;

        var totalpoints = 0L;
        for (row in char_matrix.indices)
        {
            var int_builder = "";
            var success = false;
            var adjacent = false;
            var int_to_gears = HashSet<String>();

            for (col in char_matrix[row].indices)
            {

                // get surrounding chars
                // i wish i had python here :(
                // get surroudning chars?


                if(is_valid_symbol(char_matrix[row][col]))
                {
                    if(char_matrix[row][col].isDigit())
                    {
                        int_builder += char_matrix[row][col]


                    val row_above = row - 1;
                    val col_left = col - 1;
                    val row_below = row + 1;
                    val col_right = col + 1;

                    if (row_above >= 0)
                    {
                        if(col_left >= 0)
                        {
                            var c = char_matrix[row_above][col_left];
                            if(is_symbol(c))
                            {
                                adjacent = true;
                                int_to_gears.add("$row_above:$col_left")
                            }
                        }
                        var c = char_matrix[row_above][col];
                        if(is_symbol(c))
                        {
                            adjacent = true;
                            int_to_gears.add("$row_above:$col")
                        }

                        if(col_right < numRows)
                        {
                            var c = char_matrix[row_above][col_right];
                            if(is_symbol(c))
                            {
                                adjacent = true;
                                int_to_gears.add("$row_above:$col_right")
                            }
                        }
                    }
                        if(col_left >= 0)
                        {
                            var c = char_matrix[row][col_left];
                            if(is_symbol(c))
                            {
                                adjacent = true;
                                int_to_gears.add("$row:$col_left")
                            }
                        }
                        if(col_right < numRows)
                        {
                            var c = char_matrix[row][col_right];
                            if(is_symbol(c))
                            {
                                adjacent = true;
                                int_to_gears.add("$row:$col_right")
                            }
                        }

                        if (row_below < numCols)
                        {
                            if(col_left >= 0)
                            {
                                var c = char_matrix[row_below][col_left];
                                if(is_symbol(c))
                                {
                                    adjacent = true;
                                    int_to_gears.add("$row_below:$col_left")
                                }
                            }
                            var c = char_matrix[row_below][col];
                            if(is_symbol(c))
                            {
                                adjacent = true;
                                int_to_gears.add("$row_below:$col")
                            }

                            if(col_right < numRows)
                            {
                                var c = char_matrix[row_below][col_right];
                                if(is_symbol(c))
                                {
                                    adjacent = true;
                                    int_to_gears.add("$row_below:$col_right")
                                }
                            }
                        }
                    }
                    else
                    {
                        if(int_builder != "")
                        {
                            // done and collect points if adjacent was true?
                            // also break if end of row? but not here probavbly
                            /*
                            if(adjacent)
                            {
                                totalpoints += int_builder.toLong();
                                // reset
                            }
                            */
                            for (value in int_to_gears)
                            {
                                if(value in mutableMap)
                                {
                                    mutableMap.get(value)!!.add(int_builder.toInt());
                                }
                                else
                                {
                                    mutableMap[value] = mutableListOf<Int>(int_builder.toInt());

                                }
                            }
                            int_to_gears.clear();

                        }

                        int_builder = "";
                        success = false;
                        adjacent = false;


                    }

                    // Replace surrounding stuff with .?
                }
                else
                {
                    if(int_builder != "")
                    {
                        // done and collect points if adjacent was true?
                        // also break if end of row? but not here probavbly
                        if(adjacent)
                        {
                            totalpoints += int_builder.toLong();
                            // reset
                        }
                        for (value in int_to_gears)
                        {
                            if(value in mutableMap)
                            {
                                mutableMap.get(value)!!.add(int_builder.toInt());
                            }
                            else
                            {
                                mutableMap[value] = mutableListOf<Int>(int_builder.toInt());

                            }
                        }
                        int_to_gears.clear();
                    }

                    int_builder = "";
                    success = false;
                    adjacent = false;
                }
                //val test = is_symbol_adjacent(ch);
                //print(ch);

            }
            //println()
            if(int_builder != "")
            {
                // done and collect points if adjacent was true?
                // also break if end of row? but not here probavbly
                if(adjacent)
                {
                    //totalpoints += int_builder.toLong();
                    // reset
                    success = false;
                    adjacent = false;
                }
                for (value in int_to_gears)
                {
                    if(value in mutableMap)
                    {
                        mutableMap.get(value)!!.add(int_builder.toInt());
                    }
                    else
                    {
                        mutableMap[value] = mutableListOf<Int>(int_builder.toInt());
                    }
                }
            }
            int_to_gears.clear();
            int_builder = "";

        }
        //val content: String = File(filePath).readText();
        //val charMatrix = stringToCharMatrix(content);
        var res = 0;
        for (key in mutableMap.keys) {
            val valueList = mutableMap[key]
            println("Values for key $key: $valueList")
            var temp = 0
            if(valueList!!.size == 2)
            {
                temp = valueList[0] * valueList[1];
            }

            res += temp;
        }

        println(res)

    } catch (e: Exception) {
        println("An error occurred: ${e.printStackTrace()}")
    }
}