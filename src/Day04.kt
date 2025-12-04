private val defaultValue = '0'

fun main() {
    fun part1(input: List<String>): Int {
        val matrix: Array<Array<Boolean>> = Array(input.size) {
            val row = input[it]
            Array(row.length) {
                row[it] == '@'
            }
        }
        var count = 0
        for (rowIndex in 0 until matrix.size) {
            val row = matrix[rowIndex]
            for (columnIndex in 0 until row.size) {
                if (!matrix[rowIndex][columnIndex]) {
                    continue
                }
                val neighbors = matrix.getNeighborsOf(rowIndex, columnIndex)
                if (neighbors.count { it } < 4) {
                    ++count
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val matrix: Array<Array<Boolean>> = Array(input.size) {
            val row = input[it]
            Array(row.length) {
                row[it] == '@'
            }
        }

        var removedCount = 0
        var count = 0
        do {
            count = 0
            for (rowIndex in 0 until matrix.size) {
                val row = matrix[rowIndex]
                for (columnIndex in 0 until row.size) {
                    if (!matrix[rowIndex][columnIndex]) {
                        continue
                    }
                    val neighbors = matrix.getNeighborsOf(rowIndex, columnIndex)
                    if (neighbors.count { it } < 4) {
                        matrix[rowIndex][columnIndex] = false
                        ++count
                    }
                }
            }

            removedCount += count
        } while(count > 0)

        return removedCount
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun <T> Array<Array<T>>.getNeighborsOf(targetRowIndex: Int, targetColumnIndex: Int): List<T> {
    val resultList = mutableListOf<T>()
    val deviation = 1
    for (rowIndex in targetRowIndex - deviation..targetRowIndex + deviation) {
        if (rowIndex !in 0..lastIndex) {
            continue
        }
        for (columnIndex in targetColumnIndex - deviation..targetColumnIndex + deviation) {
            if (rowIndex == targetRowIndex && columnIndex == targetColumnIndex) {
                continue
            }
            if (columnIndex !in 0..get(rowIndex).lastIndex) {
                continue
            }

            resultList.add(get(rowIndex).get(columnIndex))
        }
    }
    return resultList
}
