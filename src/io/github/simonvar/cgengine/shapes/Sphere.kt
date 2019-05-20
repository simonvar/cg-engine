package io.github.simonvar.cgengine.shapes

import io.github.simonvar.cgengine.primitives.Triangle
import io.github.simonvar.cgengine.primitives.Vertex

class Sphere(r: Double, detailU: Int, detailV: Int) : GridPlane(2 * Math.PI, Math.PI, detailU, detailV) {

    init {
        for (i in 0 until parts.size) {
            val tri = parts[i] as Triangle
            calculateSphere(tri.v1, r)
            calculateSphere(tri.v2, r)
            calculateSphere(tri.v3, r)
        }
    }

    /**
     * Calculates the sphere position of a point.
     * This position is obtained by multiplying the point(vertex) with the two
     * elementary rotation matrixes
     * the result is that of the unit sphere
     * x=cos(x)*cos(y)
     * y=cos(x)*sin(y)
     * z=sin(x)
     * of course is only necessary to multiply with the radius length to
     * obtain a sphere with the desired radius
     * @param v vertex to be transformed
     * @param r sphere radius
     */
    private fun calculateSphere(v: Vertex, r: Double) {
        val x: Double = v.x

        //calculate and update new values
        v.x = r * Math.cos(x) * Math.cos(v.y)
        v.y = r * Math.cos(x) * Math.sin(v.y)
        v.z = r * Math.sin(x)
    }
}