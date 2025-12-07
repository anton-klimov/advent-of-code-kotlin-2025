private val defaultValue = '0'

fun main() {
    fun part1(input: List<String>): Long {
        val splitPattern = Regex("\\s+")
        val operationsList: List<String> = input.last().split(splitPattern).filter { it.isNotEmpty() }
        val valuesMatrix: List<List<Long>> = input
            .take(input.size - 1)
            .map { row ->
                row.split(splitPattern).filter { it.isNotEmpty() }.map { it.toLong() }
            }

        val valuesRange = IntRange(0, input.lastIndex - 1)
        return operationsList.foldIndexed(0L) { index, acc, operation ->
            acc + when (operation) {
                "+" -> valuesRange.fold(0L) { acc, rowIndex -> acc + valuesMatrix[rowIndex][index] }
                "*" -> valuesRange.fold(1L) { acc, rowIndex -> acc * valuesMatrix[rowIndex][index] }
                else -> throw IllegalArgumentException()
            }
        }
    }

    fun part2(input: List<String>): Long {
        val rotatedInput: List<String> = List(input.first().length) { columnIndex ->
            (0 until input.lastIndex).fold("") { acc, rowIndex ->
                acc + input[rowIndex][columnIndex]
            }
        }
        val operations = input.last().split(Regex("\\s+")).filter { it.isNotEmpty() }

        var chunkStartIndex = 0
        return operations.fold(0L) { acc, operation ->
            var nextEmptyLineIndex = 0

            for (index in chunkStartIndex..rotatedInput.lastIndex) {
                if (rotatedInput[index].isBlank()) {
                    nextEmptyLineIndex = index
                    break
                }
                nextEmptyLineIndex = index + 1
            }

            val values = rotatedInput.subList(chunkStartIndex, nextEmptyLineIndex)
            chunkStartIndex = nextEmptyLineIndex + 1
            acc + when (operation) {
                "+" -> values.fold(0L) { acc, value -> acc + value.trim().toLong() }
                "*" -> values.fold(1L) { acc, value -> acc * value.trim().toLong() }
                else -> throw IllegalArgumentException()
            }
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
