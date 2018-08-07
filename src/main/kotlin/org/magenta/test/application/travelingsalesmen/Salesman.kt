package org.magenta.test.application.travelingsalesmen

import org.magenta.test.application.calculator.TimeExportCalculator
import org.magenta.test.application.entity.Order
import org.magenta.test.application.entity.Resource
import java.lang.Math.*

//https://annals-csis.org/Volume_6/pliks/311.pdf

class Salesman{

    //Жадный маршрут - едем на ближайший заказ с истекающим сроком исполнения( функция для создания начального маршрута в методе отжига)
    fun swapToGreedyRote(orders: MutableList<Order>){
        orders.sortBy { it.timeInterval.end }
    }

    //Маршрут получаемый методом иммитации отжига
    fun swapToAnnealingRote(resource: Resource){
        val timeExportCalculator = TimeExportCalculator(resource)
        //Берем жадный маршрут в качестве начального
        swapToGreedyRote(resource.orders)
        var temperature = 100.0
        val alfa = 0.8
        //эталонный маршрут с наименьшим временем
        var orders = resource.orders.toMutableList()
        //изменяемый маршрут
        var lastOrders = orders.toMutableList()
        //новый маршрут
        var newOrders = orders.toMutableList()
        //номера перестановочных городов
        var first = (orders.size * random()).toInt()
        var second = (orders.size * random()).toInt()
        //времена работы
        var c1 = timeExportCalculator.calculateStartTime().let { it.endTime - it.startTime }
        var C = c1
        var c2: Int
        var dc: Int
        while (temperature>0.1){
            newOrders[first] = lastOrders[second].also { lastOrders[second] = lastOrders[first]}
            resource.orders = newOrders
            if (timeExportCalculator.validateStartTime(newOrders.first().timeInterval.start)) {
                c2 = timeExportCalculator.calculateStartTime().let { it.endTime - it.startTime }
                dc = c2 - c1
                if (dc <= 0 || pow(E,-(dc/temperature)) > random()){
                    lastOrders = newOrders
                }
                if (C > c2){
                    orders = newOrders
                }
            }
            temperature *= alfa
        }
        resource.orders = orders
    }

}