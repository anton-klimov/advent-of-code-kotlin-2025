import java.lang.Math.pow
import kotlin.math.absoluteValue
import kotlin.math.log10
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Long {
        val ranges: List<LongRange> = input
            .flatMap { it.split(',') }
            .map {
                val rangeBounds = it.split('-')
                LongRange(rangeBounds[0].toLong(), rangeBounds[1].toLong())
            }

        return ranges.sumOf { range: LongRange ->
            range.sumOf { id: Long ->
                val log10: Long = log10(id.toDouble()).toLong() + 1
                if (log10 > 1 && log10 % 2 == 0L) {
                    val halfBase: Long = 10.0.pow(log10 / 2.0).toLong()
                    if (id / halfBase == id % halfBase) {
                        id
                    } else {
                        0L
                    }
                } else {
                    0L
                }
            }
        }
    }

    fun part2(input: List<String>): Long {
        val ranges: List<LongRange> = input
            .flatMap { it.split(',') }
            .map {
                val rangeBounds = it.split('-')
                LongRange(rangeBounds[0].toLong(), rangeBounds[1].toLong())
            }

        return ranges.sumOf { range: LongRange ->
            range.sumOf { id: Long ->
                val idString = id.toString()
                for (patternLength in 1..idString.length / 2) {
                    val patternToCheck: String = idString.take(patternLength)
                    if (idString.replace(patternToCheck, "").isEmpty()) {
                        return@sumOf id
                    }
                }

                0L
            }
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
