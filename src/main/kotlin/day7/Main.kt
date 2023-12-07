package day7

import day6.get_race_pairs
import java.io.File

enum class Type
{
    FIVE,
    FOUR,
    HOUSE,
    THREE,
    TWO,
    ONE,
    HIGH
}

val type_dict = mapOf<Type,Int>(
    Pair<Type, Int>(Type.FIVE, 6),
    Pair<Type, Int>(Type.FOUR, 5),
    Pair<Type, Int>(Type.HOUSE, 4),
    Pair<Type, Int>(Type.THREE, 3),
    Pair<Type, Int>(Type.TWO, 2),
    Pair<Type, Int>(Type.ONE, 1),
    Pair<Type, Int>(Type.HIGH, 0),
)

val joker_options = ('2'..'9').toList()+ listOf('A', 'K', 'Q', 'T')


class Hand(str: String): Comparable<Hand> {

    private var Cards: String = ""
    private var Bid: Int = 0
    public var type: Int = 0


    init  {
        var (Cards, Bid) = this.parseInput(str);
        this.Cards = Cards;
        this.Bid = Bid;

        // TODO check if there are joker cards, loop all possible outcomes for the biggest type?
        // Type decided on J but its value is 1
        this.type = get_best_hand(Cards);
        val a = 0
    }

    public fun GetBid() : Int
    {
        return this.Bid;
    }
    private fun parseInput(str: String): Pair<String,Int>{
        val cards = str.split(" ").first
        val bid = str.split(" ").last.toInt()
        return Pair(cards, bid)
    }


    private fun get_best_hand(str: String): Int{
        if('J' in str){
            val res =  joker_options.fold(0) { acc: Int, value: Char ->
                val one = str.replaceFirst('J', value)
                val two = get_best_hand(one)
                if (acc < two)
                    two
                else
                    acc
            }
            return res
        }else{
            val type = get_hand_type(str)
            return type;
        }
    }

    // This is wrong, two of a pair the exception only?
    private fun get_hand_type(str: String):Int{
        val charCountMap = str.groupingBy { it }.eachCount()
        // Find the maximum count
        val maxCount = charCountMap.values.maxOrNull() ?: 0
        if(maxCount >= 4)
            return maxCount+1
        else if(maxCount == 3)
        {
            val numbers = charCountMap.size
            if(numbers == 2)
                return 4
            else
                return 3
        }
        else if(maxCount == 2)
        {
            val severalTwo = charCountMap.values.count{it == 2}
            if(severalTwo == 2)
                return 2
            else
                return 1
        }
        return 0
    }

    fun countInts(list: List<Int>): Map<Int, Int> {
        // Use groupingBy and eachCount to count occurrences of each integer
        return list.groupingBy { it }.eachCount()
    }

    fun CardValue(c: Char): Int{

        if(c.isDigit())
            return c.digitToInt()

        when (c) {
            'A' -> return 14
            'K' -> return 13
            'Q' -> return 12
            'T' -> return 10
            'J' -> return 1
            else -> throw Exception("ASD")
        }
    }

    // Assume it cant be draw for now?
    override fun compareTo(other: Hand):Int{
        if(this.type>other.type)
            return 1;
        else if(this.type<other.type)
            return -1
        // Equal hand, check highest card

        for (i in 0 until 5){
            if(CardValue(this.Cards[i]) > CardValue(other.Cards[i]))
                return 1
            else if(CardValue(this.Cards[i]) < CardValue(other.Cards[i]))
                return -1
        }
        return 0
    }
}


fun main(){


    val fileName = "C:\\Users\\Bleckert\\IdeaProjects\\aoc2023\\src\\main\\kotlin\\day7\\input.txt"
    val data1 = File(fileName).readLines().map { Hand(it) }

    // Test house 4 and 5


    fun part1()
    {

        data1.forEach { println(it.type) }

        val sorted = data1.sorted()
        var sum = 0
        for(i in 0 until sorted.size)
        {
            sum += sorted[i].GetBid()*(i+1)
            println(sorted[i].GetBid())
        }
        print("Total ")
        println(sum)

    }

    part1()
}