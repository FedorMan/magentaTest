package org.magenta.test.application

import org.magenta.test.application.calculator.TimeExportCalculator
import org.magenta.test.application.entity.*

fun main(args: Array<String>) {
    DistributionCenter.timeInterval = TimeInterval(530, 1320)
    DistributionCenter.geoPoint = GeoPoint(53.2443, 50.202)

    val orders = mutableListOf<Order>()
    orders.add(Order(GeoPoint(53.233, 50.199847), 15.0, 12, 12, TimeInterval(720, 840)))
    orders.add(Order(GeoPoint(53.2451141, 50.190082), 15.0, 12, 12, TimeInterval(1000, 1320)))

    val resource = Resource(60, 90.0, orders)

    val timeExportCalculator = TimeExportCalculator(resource)

    println(timeExportCalculator.calculateStartTime())
}