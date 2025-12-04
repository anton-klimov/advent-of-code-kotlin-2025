private val defaultValue = '0'

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { item ->
            var firstMaxDigitValue: Int = 0
            var firstMaxDigitIndex: Int = -1
            item.forEachIndexed { index, char ->
                val charInt = char.toString().toInt()
                if (charInt > firstMaxDigitValue) {
                    firstMaxDigitValue = charInt
                    firstMaxDigitIndex = index
                }
            }
            val isLastCharLargest = firstMaxDigitIndex == item.lastIndex
            val secondMaxDigitValue: Int = item
                .substring(
                    startIndex = if (isLastCharLargest) 0 else firstMaxDigitIndex + 1,
                    endIndex = if (isLastCharLargest) item.length - 1 else item.length
                )
                .maxOf { it.toString().toInt() }

            "$firstMaxDigitValue$secondMaxDigitValue".let { if (isLastCharLargest) it.reversed() else it}.toInt()
        }
    }

    fun part2(input: List<String>): Long {
        val batterySize = 12
        return input.sumOf { item ->
            val chars: CharArray = item.toCharArray()
            var batteryList: List<Char> = List(chars.size) { defaultValue }

            repeat(batterySize) {
                var maxValue = batteryList.toMutableList()

                chars.forEachIndexed { index, character ->
                    if (batteryList[index] == defaultValue) {
                        val newValue = batteryList.toMutableList().apply { this[index] = character }
                        if (newValue.toLong() > maxValue.toLong()) {
                            maxValue = newValue

                        }
                    }
                }

                batteryList = maxValue
            }

            batteryList.toLong()
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
//    part1(input).println()
    part2(input).println()
}

private fun List<Char>.toLong(): Long {
    return asSequence()
        .filter { it != defaultValue }
        .joinToString(separator = "") { it.toString() }
        .takeIf { it.isNotEmpty() }
        ?.toLong()
        ?: 0
}
