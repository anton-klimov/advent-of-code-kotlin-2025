private val defaultValue = '0'

fun main() {
    fun part1(input: List<String>): Int {
        val fieldArea = Array<Array<Field>>(input.size) { index ->
            val row = input[index]
            Array(row.length) { columnIndex ->
                Field(
                    type = when (row[columnIndex]) {
                        '.' -> FieldType.Empty
                        'S' -> FieldType.StartPoint
                        '^' -> FieldType.Splitter
                        else -> throw IllegalArgumentException()
                    },
                    containsBeam = false
                )
            }
        }

        var count = 0
        for (index in 0 until fieldArea.lastIndex) {
            fieldArea[index].forEachIndexed { columnIndex, field ->
                when (field.type) {
                    FieldType.Empty -> {
                        if (field.containsBeam) {
                            fieldArea[index + 1][columnIndex].containsBeam = true
                        }
                    }

                    FieldType.StartPoint -> fieldArea[index + 1][columnIndex].containsBeam = true
                    FieldType.Splitter -> {
                        if (field.containsBeam) {
                            ++count
                        }
                        (columnIndex - 1).takeIf { it >= 0 }?.let {
                            fieldArea[index + 1][it].containsBeam = true
                        }
                        (columnIndex + 1).takeIf { it < fieldArea[index].size }?.let {
                            fieldArea[index + 1][it].containsBeam = true
                        }
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val fieldArea = Array<Array<Field>>(input.size) { index ->
            val row = input[index]
            Array(row.length) { columnIndex ->
                Field(
                    type = when (row[columnIndex]) {
                        '.' -> FieldType.Empty
                        'S' -> FieldType.StartPoint
                        '^' -> FieldType.Splitter
                        else -> throw IllegalArgumentException()
                    },
                    containsBeam = false
                )
            }
        }.filterNot { it.all { it.type == FieldType.Empty } }.toTypedArray()

        fun f(fieldArea: Array<Array<Field>>): Int {
            for (index in 0 until fieldArea.lastIndex) {
                fieldArea[index].forEachIndexed { columnIndex, field ->
                    when (field.type) {
                        FieldType.Empty -> {
                            if (field.containsBeam) {
                                fieldArea[index + 1][columnIndex].containsBeam = true
                            }
                        }

                        FieldType.StartPoint -> fieldArea[index + 1][columnIndex].containsBeam = true
                        FieldType.Splitter -> {
                            if (field.containsBeam) {
                                val remainingFieldArea = fieldArea.copy(index + 1)
                                return 2 +
                                        f(remainingFieldArea.apply {
                                            (columnIndex - 1).takeIf { it >= 0 }?.let {
                                                this[0][it].containsBeam = true
                                            }
                                        }) +
                                        f(remainingFieldArea.apply {
                                            (columnIndex + 1).takeIf { it < fieldArea[index].size }?.let {
                                                this[0][it].containsBeam = true
                                            }
                                        })
                            }
                        }
                    }
                }
            }

            return 0
        }

        return f(fieldArea)
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07_example")
    part1(input).println()
    part2(input).println()
}

private fun Array<Array<Field>>.copy(startIndex: Int): Array<Array<Field>> {
    return Array(size - startIndex) { rowIndex ->
        val row = get(startIndex + rowIndex)
        Array<Field>(row.size) { columnIndex ->
            row[columnIndex].copy()
        }
    }
}

private enum class FieldType {
    Empty, StartPoint, Splitter
}

private data class Field(val type: FieldType, var containsBeam: Boolean)
