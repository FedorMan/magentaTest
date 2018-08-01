package org.magenta.test.application.calculator

import org.magenta.test.application.entity.*
import kotlin.math.roundToInt

// Логика поиска нужного времени строится на предположении, что нужно выехать на первый заказ как можно позже,
// чтобы успеть на все другие заказы, для поиска точки выезда использован алгоритм бинарного поиска
class TimeExportCalculator(val resource: Resource){

    fun calculateStartTime(): TimeTable{
        val orders = resource.orders
        val firstInterval = orders.first().timeInterval.copy()
        while (firstInterval.start  - firstInterval.end >= 1) {
            val startTime = getMiddle(firstInterval)
            if (validateStartTime(orders, startTime, resource.speed)) {
                firstInterval.start = startTime
            } else {
                firstInterval.end = startTime
            }
        }
        return calculateTimeTable(orders, firstInterval.end, resource.speed)
    }

    private fun getMiddle(timeInterval: TimeInterval) = timeInterval.start + (timeInterval.end - timeInterval.start)/2

    private fun validateStartTime(orders: List<Order>, startTime: Int, speed: Int):Boolean{
        var currentGeoPoint = DistributionCenter.geoPoint
        var time = startTime
        var distance: Double
        var travelTime: Double
        orders.forEach {
            time += calculateTravelTime(currentGeoPoint, it.geoPoint, speed)
            if (time < it.timeInterval.start) time = it.timeInterval.start
            time += it.timeUnload
            if (time > it.timeInterval.end) return false
            currentGeoPoint = it.geoPoint
        }
        time += calculateTravelTime(currentGeoPoint, DistributionCenter.geoPoint, speed)
        if (time > DistributionCenter.timeInterval.end) return false
        return true
    }

    private fun calculateTimeTable(orders: List<Order>, startTime: Int, speed: Int): TimeTable{
        val timeTable = TimeTable(startTime, mutableListOf<TimeInterval>(), 0)
        timeTable.startTime = startTime - orders.sumBy { it.timeLoad } - calculateTravelTime(DistributionCenter.geoPoint, orders.first().geoPoint, speed)
        var currentGeoPoint = DistributionCenter.geoPoint
        var time = startTime
        var distance: Double
        var travelTime: Double
        orders.forEach {
            val timeInterval = TimeInterval(0,0)
            time += calculateTravelTime(currentGeoPoint, it.geoPoint, speed)
            timeInterval.start = time
            if (time < it.timeInterval.start) time += it.timeInterval.start - time
            time += it.timeUnload
            timeInterval.end = time
            currentGeoPoint = it.geoPoint
            timeTable.timeExecuteOrders.add(timeInterval)
        }
        time += calculateTravelTime(currentGeoPoint, DistributionCenter.geoPoint, speed)
        timeTable.endTime = time
        return timeTable
    }

    private fun calculateTravelTime(currentPoint: GeoPoint, nextPoint: GeoPoint, speed: Int): Int{
        val distance = calculteDistance(currentPoint, nextPoint)
        var travelTime = distance.div(speed)
        travelTime *= 60
        //округление минут всегда в большую сторону
        return  if(travelTime%1>0) (travelTime + 1).toInt() else travelTime.toInt()
    }
}