package org.magenta.test.application.calculator

import org.magenta.test.application.entity.*

// Логика поиска нужного времени строится на предположении, что нужно выехать на первый заказ как можно позже,
// чтобы успеть на все другие заказы, для поиска точки выезда использован алгоритм бинарного поиска
class TimeExportCalculator(val resource: Resource){

    fun calculateStartTime(): TimeTable{
        val firstInterval = resource.orders.first().timeInterval.copy()
        while (firstInterval.end  - firstInterval.start > 1) {
            val startTime = getMiddle(firstInterval)
            if (validateStartTime(startTime)) {
                firstInterval.start = startTime
            } else {
                firstInterval.end = startTime
            }
        }
        return calculateTimeTable(firstInterval.end)
    }

    fun validateStartTime(startTime: Int):Boolean{
        var currentGeoPoint = DistributionCenter.geoPoint
        var time = startTime
        var distance: Double
        var travelTime: Double
        resource.orders.forEach {
            time += calculateTravelTime(currentGeoPoint, it.geoPoint, resource.speed)
            if (time < it.timeInterval.start) time = it.timeInterval.start
            time += it.timeUnload
            if (time > it.timeInterval.end) return false
            currentGeoPoint = it.geoPoint
        }
        time += calculateTravelTime(currentGeoPoint, DistributionCenter.geoPoint, resource.speed)
        if (time > DistributionCenter.timeInterval.end) return false
        return true
    }

    private fun calculateTimeTable(startTime: Int): TimeTable{
        val timeTable = TimeTable(startTime, mutableListOf<TimeInterval>(), 0)
        timeTable.startTime = startTime - resource.orders.sumBy { it.timeLoad } - calculateTravelTime(DistributionCenter.geoPoint, resource.orders.first().geoPoint, resource.speed)
        var currentGeoPoint = DistributionCenter.geoPoint
        var time = startTime
        resource.orders.forEach {
            val timeInterval = TimeInterval(0,0)
            time += calculateTravelTime(currentGeoPoint, it.geoPoint, resource.speed)
            timeInterval.start = time
            if (time < it.timeInterval.start) time = it.timeInterval.start
            time += it.timeUnload
            timeInterval.end = time
            currentGeoPoint = it.geoPoint
            timeTable.timeExecuteOrders.add(timeInterval)
        }
        time += calculateTravelTime(currentGeoPoint, DistributionCenter.geoPoint, resource.speed)
        timeTable.endTime = time
        return timeTable
    }
}