package org.magenta.test.application.calculator

import org.magenta.test.application.entity.GeoPoint
import org.magenta.test.application.entity.TimeInterval
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

//Набор статических вспомогательных функций

//http://www.geodatasource.com/developers/java
fun calculteDistance(geoPoint1: GeoPoint, geoPoint2: GeoPoint): Double{
    val theta = geoPoint1.longitude - geoPoint2.longitude
    var dist = sin(deg2rad(geoPoint1.latitude)) * sin(deg2rad(geoPoint2.latitude)) + cos(deg2rad(geoPoint1.latitude)) * cos(deg2rad(geoPoint2.latitude)) * cos(deg2rad(theta))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist = dist * 60 * 1.1515
    return dist * 1.609344
}

fun deg2rad(deg: Double): Double {
    return (deg * Math.PI / 180.0)
}

fun rad2deg(rad: Double): Double {
    return (rad * 180 / Math.PI)
}

fun calculateTravelTime(currentPoint: GeoPoint, nextPoint: GeoPoint, speed: Int): Int{
    val distance = calculteDistance(currentPoint, nextPoint)
    var travelTime = distance.div(speed)
    travelTime *= 60
    //округление минут всегда в большую сторону
    return  if(travelTime%1>0) (travelTime + 1).toInt() else travelTime.toInt()
}

fun getMiddle(timeInterval: TimeInterval) = timeInterval.start + (timeInterval.end - timeInterval.start)/2
