import androidx.annotation.Nullable
//import com.google.gson.Gson
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

data class Point3D(val x: Float, val y: Float, val z: Float)
data class Point2D(val x: Float, val y: Float, val visibility: Float)

class MetricSystem(asanaField: String) {
    private val coordinates: List<Point3D>

    init {
        coordinates = List(33){
            Point3D(0f, 0f, 0f)
        }
    }

    fun scoreMetric(
        poseLandmarkerResults: PoseLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,

    ): Double {
        return (10..100).shuffled().first() * 1.0
    }
}
//class MetricSystem(jsonString: String) {
////    private val coordinates: List<Point3D>
////    private lateinit var projection: List<Point2D>
////    private val torsoSize: Float
//    private val coordinates: List<Int>
//    private lateinit var projection: List<Point2D>
//    private val torsoSize: Float
//
//    init {
////        coordinates = parseJson(jsonString)
////        getXYProjection()
//        coordinates = (0..23).toList()
//        torsoSize = calculateTorsoSize()
//    }
//
//
//    fun scoreMetric(
//        poseLandmarkerResults: PoseLandmarkerResult,
//        imageHeight: Int,
//        imageWidth: Int): Double
//    {
//         return (10..100).shuffled().first() * 1.0
//    }
//
//    private fun calculateTorsoSize(): Float {
//        val shoulders = listOf(coordinates[11], coordinates[12])  // Индексы точек плеч
//        val hips = listOf(coordinates[23], coordinates[24])  // Индексы точек таза
//
////        val maxDistance = shoulders.flatMap { shoulder ->
////            hips.map { hip ->
////                euclideanDistanceTriple(shoulder, hip)
////            }
////        }.maxOrNull() ?: 0f
//
////        return maxDistance
//        return 1f
//    }
//
//    private fun parseJson(jsonString: String): List<Point3D> {
//        val gson = Gson()
//        return gson.fromJson(jsonString, Array<Point3D>::class.java).toList()
//    }
//
////    fun getXYProjection() {
////        this.projection = coordinates.map { Point2D(it.x, it.y, 1f) }
////    }
////
////    fun getXZProjection() {
////        this.projection = coordinates.map { Point2D(it.x, it.z, 1f) }
////    }
////
////
////    fun getYZProjection(){
////        this.projection = coordinates.map { Point2D(it.y, it.z, 1f) }
////    }
//
//    fun compareProjections(other: List<Pair<Float, Float>>): Float {
//        val distances = projection.mapIndexed { index, coord ->
//            euclideanDistance(Pair(coord.x, coord.y), other[index])
//        }
//
//        val angles = calculateAngles(projection)
//
//        val pck = calculatePCK(distances, 0.2f*torsoSize)
//        val angleScore = calculateAngleScore(angles, 180.0f)
//
//        return (pck + angleScore) / 2 * 100
//    }
//
//    private fun euclideanDistance(p1: Pair<Float, Float>, p2: Pair<Float, Float>): Float {
//        return sqrt((p1.first - p2.first).pow(2) + (p1.second - p2.second).pow(2))
//    }
//    private fun euclideanDistanceTriple(p1: Point3D, p2: Point3D): Float {
//        return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2) + (p1.z - p2.z).pow(2))
//    }
//
//    private fun calculateAngle(v1: Pair<Float, Float>, v2: Pair<Float, Float>): Float {
//        val dotProduct = v1.first * v2.first + v1.second * v2.second
//        val magnitude1 = sqrt(v1.first.pow(2) + v1.second.pow(2))
//        val magnitude2 = sqrt(v2.first.pow(2) + v2.second.pow(2))
//        return acos(dotProduct / (magnitude1 * magnitude2))
//    }
//
//    private fun calculateAngles(coords: List<Point2D>): List<Float> {
//        val angles = mutableListOf<Float>()
//        for (i in 1 until coords.size - 1) {
//            val v1 = Pair(coords[i].x - coords[i - 1].x, coords[i].y - coords[i - 1].y)
//            val v2 = Pair(coords[i + 1].x - coords[i].x, coords[i + 1].y - coords[i].y)
//            angles.add(calculateAngle(v1, v2))
//        }
//        return angles
//    }
//
//    private fun calculatePCK(distances: List<Float>, threshold: Float): Float {
//        val count = distances.count { it <= threshold }
//        return count.toFloat() / distances.size
//    }
//
//    private fun calculateAngleScore(angleDifferences: List<Float>, maxAngle: Float): Float {
//        val totalDifference = angleDifferences.sum()
//        return 1 - (totalDifference / (angleDifferences.size * maxAngle))
//    }
//}
