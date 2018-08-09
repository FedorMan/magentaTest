package org.magenta.test.application.entity

//Время на сутки представляется в виде минут от 0 до 1440
data class TimeInterval(var start: Int, var end: Int){
    fun inInterval(time: Int) = time >= start && time <= end
    override fun toString(): String {
        return "[$start,$end]"
    }
}