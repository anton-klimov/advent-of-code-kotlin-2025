private val defaultValue = '0'

fun main() {
    fun part1(input: List<String>): Int {
        val emptyLineIndex = input.indexOf("")
        val ranges: List<LongRange> = input.subList(0, emptyLineIndex)
            .map { row -> row.split("-").let { it[0].toLong()..it[1].toLong() } }
        val products: List<Long> = input.subList(emptyLineIndex + 1, input.size)
            .map { it.toLong() }
        return products.count { product ->
            ranges.any { it.contains(product) }
        }
    }

    fun part2(input: List<String>): Long {
        val emptyLineIndex = input.indexOf("")
        val ranges: List<LongRange> = input.subList(0, emptyLineIndex)
            .map { row -> row.split("-").let { it[0].toLong()..it[1].toLong() } }
            .sortedBy { it.start }

        "Ranges count: ${ranges.count()}".println()

        val mergedRanges = mutableListOf<LongRange>()
        var currentRange: LongRange = ranges.first()
        for (index in 1 .. ranges.lastIndex) {
            val range = ranges[index]
            if (range.start >= currentRange.start) {
                if (range.start > currentRange.endInclusive) {
                    mergedRanges.add(currentRange)
                    currentRange = range
                } else if (range.endInclusive > currentRange.endInclusive) {
                    currentRange = LongRange(currentRange.start, range.endInclusive)
                }
            }
        }

        mergedRanges.add(currentRange)

        "Merged rages: $mergedRanges".println()

        return mergedRanges.sumOf { it.countLong() }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

fun LongRange.countLong() = endInclusive - start + 1
