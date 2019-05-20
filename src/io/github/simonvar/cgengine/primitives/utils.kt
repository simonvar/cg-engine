package io.github.simonvar.cgengine.primitives

fun dist(v1: Vertex, v2: Vertex): Double {
    val x = v1.x - v2.x
    val y = v1.y - v2.y
    val z = v1.z - v2.z
    return Math.sqrt(x * x + y * y + z * z)
}

/**
 * Calculates the dot product between two vectors
 * @param v1 first vector
 * @param v2 second vector
 * @return dot product value
 */
fun dotProduct(v1: Vertex, v2: Vertex): Double {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
}

/**
 * Calculates the cross product between two vectors
 * @param v1 first vector
 * @param v2 second vector
 * @return cross product vector
 */
fun crossProduct(v1: Vertex, v2: Vertex): Vertex {
    return Vertex(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x)
}
