package covidInfo

import java.util.*

data class TimePoint(
    val date: Date,
    val count: Int
)

fun convertFromMapToListOfTimePoints(map: Map<String, Int>): List<TimePoint> {
    val list = mutableListOf<TimePoint>()

    for ((dateString, count) in map) {
        val dateList = dateString.split("/")
        val (month, day, year) = dateList

        list.add(
            TimePoint(
                Calendar
                    .Builder()
                    .setDate(2000 + year.toInt(), month.toInt() - 1, day.toInt())
                    .build()
                    .time,
                count
            )
        )
    }

    return list.toList()
}
