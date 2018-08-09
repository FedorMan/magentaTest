package org.magenta.test.application.entity

class GeoPoint(val latitude: Double, val longitude: Double){
    override fun toString(): String {
        return "{$latitude;$longitude}"
    }
}
