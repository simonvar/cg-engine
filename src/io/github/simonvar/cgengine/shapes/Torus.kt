package io.github.simonvar.cgengine.shapes

import io.github.simonvar.cgengine.primitives.Triangle
import io.github.simonvar.cgengine.primitives.Vertex

class Torus(r1: Double, detail1: Int, r2: Double, detail2: Int) :
    GridPlane(2 * Math.PI, 2 * Math.PI, detail2, detail1) {

    init {
        //transforms the grid domain into a tube
        for (i in 0 until parts.size) {
            val tri = parts[i] as Triangle
            calculateTube(tri.v1, r2)
            calculateTube(tri.v2, r2)
            calculateTube(tri.v3, r2)
        }

        //translates the tube domain by r1
        this.translate(Vertex(r1, 0.0, 0.0))

        //transforms the tube into a torus
        for (j in 0 until parts.size) {
            val tri = parts[j] as Triangle
            calculateTorus(tri.v1)
            calculateTorus(tri.v2)
            calculateTorus(tri.v3)
        }
    }

    /**
     * Calculates the firts phase of torus creation, that of a tube
     * It transforms only the x and z coordinates with the circle equation
     * creating a tube in the y direction
     * @param v vertex to be transformed
     * @param r2 secondary radius
     */
    private fun calculateTube(v: Vertex, r2: Double) {
        v.z = r2 * Math.sin(v.x)
        v.x = r2 * Math.cos(v.x)

    }

    /**
     * transforms the tube vertexes into a torus by applying this time
     * the circle equation only to the x and y coordinates
     * @param v vertex to be transformed
     */
    private fun calculateTorus(v: Vertex) {
        val x: Double
        x = v.x

        v.x = x * Math.cos(v.y)
        v.y = x * Math.sin(v.y)
    }
}