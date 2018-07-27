package org.magenta.test.application.entity

class TimeTable(var startTime: Int, var timeExecuteOrders: MutableList<TimeInterval>, var endTime: Int){
    override fun toString(): String {
        var s = "Время начала погрузки: $startTime \n"
        timeExecuteOrders.forEach { s += "Время прибытия: ${it.start} Время окончания обслуживания: ${it.end} \n" }
        s += "Время возвращения: $endTime"
        return s
    }
}