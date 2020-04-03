package covidInfo

import java.util.*
import kotlin.math.abs

fun generateGraph(message: String, points: List<TimePoint>): String {
    val sortedPoints = points.sortedBy { it.date }

    val minTimePoint = sortedPoints.first()
    val maxTimePoint = sortedPoints.last()

    val height = 50

    val delta = (maxTimePoint.count - minTimePoint.count).toDouble() / height

    /**
     * Detect, should we to draw a star (*) under highest star in column.
     * If we don't do it, graph will be consists of points, not lines.
     *
     * 0 - before draw star
     * 1 - need to draw star
     * 2 - finished to draw star
     */
    val drawStarState = MutableList(sortedPoints.size) { 0 }

    val border = "|${"-".repeat(sortedPoints.size)}|"

    val resultStringBuilder = StringBuilder()

    resultStringBuilder
        .appendln(message)
        .appendln(border)

    // draw a graph
    for (i in height downTo 0) {
        resultStringBuilder.append("|")

        for (j in sortedPoints.indices) {
            val diff = sortedPoints[j].count.toDouble() - delta * i
            if (diff >= 0 && abs(diff) <= delta) {
                resultStringBuilder.append("*")

                if (drawStarState[j] == 0) drawStarState[j] = 1
                if (j != sortedPoints.size - 1) drawStarState[j + 1] = 2
            } else if (drawStarState[j] == 1) {
                resultStringBuilder.append("*")
            } else {
                resultStringBuilder.append(" ")
            }
        }
        resultStringBuilder.appendln("| ${(delta * i).toInt()}")
    }

    // draw dates under the graph
    resultStringBuilder.append("|")

    val dateSize = formatDate(minTimePoint.date).length

    val datePointsCount = sortedPoints.size / (dateSize + 1)
    val spacesAtStart = " ".repeat(sortedPoints.size - datePointsCount * (dateSize + 1))

    resultStringBuilder.append(spacesAtStart)
    resultStringBuilder.append(" ".repeat(dateSize).plus("^").repeat(datePointsCount))
    resultStringBuilder.appendln("|")

    resultStringBuilder.append("|")
    resultStringBuilder.append(spacesAtStart)

    for (i in 1..datePointsCount) {
        resultStringBuilder.append(" ".plus(formatDate(sortedPoints[spacesAtStart.length + i * (dateSize + 1) - 1].date)))
    }
    resultStringBuilder.appendln("|")

    resultStringBuilder.appendln(border)

    return resultStringBuilder.toString()
}

private fun formatDate(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date

    return calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0') +
        "." +
        (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0') +
        "." +
        (calendar.get(Calendar.YEAR)).toString()
}