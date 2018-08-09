package org.magenta.test.application.entity

class Order(val geoPoint: GeoPoint, val weight: Double, val timeLoad: Int, val timeUnload: Int, val timeInterval: TimeInterval){
    override fun toString(): String {
        return "$timeInterval;$geoPoint"
    }
}