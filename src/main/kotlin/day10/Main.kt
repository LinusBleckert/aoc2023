package day10


enum class DIRECTION{
    SOUTH,
    EAST,
    NORTH,
    WEST
}

fun getNextPositionsFromTile(direction: DIRECTION, ch: Char):List<Pair<Int,Int>>{

    // Return all possible stuff, Create a map with all tiles in maze, replace lowest number of steps
    // Loop all
    when(ch) {
        '|' ->{
            listOf( Pair(1,0), Pair(-1,0))
        }
    }
}


fun main(){

}