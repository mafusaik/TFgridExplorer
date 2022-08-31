package by.homework.hlazarseni.tfgridexplorer

import java.lang.Math.floor

class TimeConverter {

    private val YEAR_SEC = 31536000.0
    private val MONTH_SEC = 2628000.0
    private val WEEK_SEC = 604800.0
    private val DAY_SEC = 86400.0
    private val HOUR_SEC = 3600.0
    private val MINUTE_SEC = 60.0

    fun timeToString(secs: Long): String {
        var day = 0
        val month = floor(secs % YEAR_SEC / MONTH_SEC).toInt()
        val week = floor(secs % MONTH_SEC / WEEK_SEC).toInt()
        day = if (week == 0) {
            floor(secs % MONTH_SEC / DAY_SEC).toInt()
        } else floor(secs % WEEK_SEC / DAY_SEC).toInt()

        val hour = floor(secs % DAY_SEC / HOUR_SEC).toInt()
        val min = floor(secs % HOUR_SEC / MINUTE_SEC).toInt()

        val periodList: MutableList<Int> = mutableListOf(month, week, day, hour, min)

        val priorityMap = priorityPeriodMap(periodList)

        val values = priorityMap.keys

        val firstPeriodName = values.elementAt(0)
        val secondPeriodName = values.elementAt(1)

        val firstPeriod = priorityMap[firstPeriodName]
        val secondPeriod = priorityMap[secondPeriodName]


        return "$firstPeriod $firstPeriodName, $secondPeriod $secondPeriodName"

    }

    private fun priorityPeriodMap(periodList: List<Int>): Map<String, Int> {
        val periodMap = mutableMapOf<String, Int>()

        if (periodList[0] > 0) {
            periodMap["Month"] = periodList[0]
        }
        if (periodList[1] > 0) {
            periodMap["Weeks"] = periodList[1]
        }
        if (periodList[2] > 0) {
            periodMap["Days"] = periodList[2]
        }
        if (periodList[3] > 0) {
            periodMap["Hours"] = periodList[3]
        }
        if (periodList[4] > 0) {
            periodMap["Mins"] = periodList[4]
        }

        return periodMap
    }

}