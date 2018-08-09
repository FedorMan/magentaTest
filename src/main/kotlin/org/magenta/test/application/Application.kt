package org.magenta.test.application

import org.magenta.test.application.calculator.TimeExportCalculator
import org.magenta.test.application.entity.*
import org.magenta.test.application.travelingsalesmen.Salesman

fun main(args: Array<String>) {
    DistributionCenter.timeInterval = TimeInterval(0, 1440)
    DistributionCenter.geoPoint = GeoPoint(53.2443, 50.202)

    val orders = mutableListOf<Order>()
    orders.add(Order(GeoPoint(53.242028, 50.210301), 15.0, 12, 12, TimeInterval(320, 500)))
    orders.add(Order(GeoPoint(53.225468, 50.174400), 15.0, 12, 12, TimeInterval(120, 1200)))
    orders.add(Order(GeoPoint(53.211840, 50.210192), 15.0, 12, 12, TimeInterval(720, 800)))
    orders.add(Order(GeoPoint(53.234149, 50.281016), 15.0, 12, 12, TimeInterval(380, 1300)))
    orders.add(Order(GeoPoint(53.248669, 50.249799), 15.0, 12, 12, TimeInterval(120, 940)))
    orders.add(Order(GeoPoint(53.247294, 50.190148), 15.0, 12, 12, TimeInterval(520, 720)))

    val resource = Resource(60, 90.0, orders)

    val salesman = Salesman()

    salesman.swapToAnnealingRote(resource)
//    salesman.swapToGreedyRote(resource.orders)

    val timeExportCalculator = TimeExportCalculator(resource)
    resource.orders.forEach { println(it) }
    println(timeExportCalculator.calculateStartTime())
//    val list = ArrayList<Int>()
//    list.add(0)
//    list.add(1)
//    list[0] = list[1].also { list[1] = list[0] }
//    print(list)
}