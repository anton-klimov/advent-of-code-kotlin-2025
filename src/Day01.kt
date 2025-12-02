import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .scan(initial = 50) { result: Int, item: String ->
                val parsedResult: Int = item.substring(1).toInt() * if (item[0] == 'L') -1 else 1
                (result + parsedResult).mod(100)
            }
            .count { it == 0 }
    }

    fun part2(input: List<String>): Int {
        var password: Int = 0
        input.fold(initial = 50) { result: Int, item: String ->
            val parsedResult: Int = item.substring(1).toInt() * if (item[0] == 'L') -1 else 1
            val newValue: Int = result + parsedResult
            password += (newValue / 100).absoluteValue

            if (newValue == 0 || result > 0 && newValue < 0) {
                ++password
            }

            newValue.mod(100)
        }
        return password
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
