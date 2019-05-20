package io.github.simonvar.cgengine.primitives

import io.github.simonvar.cgengine.matrix.RotationMatrix

data class Triangle(
    val v1: Vertex,
    val v2: Vertex,
    val v3: Vertex,
    var colorR: Int = 255,
    var colorG: Int = 255,
    var colorB: Int = 255
) : Transformable {

    val normal: Vertex
        get() {
            //obtain first vector
            val v1 = Vertex()
            v1.x = v2.x - v1.x
            v1.y = v2.y - v1.y
            v1.z = v2.z - v1.z

            //obtain second vector
            val v2 = Vertex()
            v2.x = v3.x - v1.x
            v2.y = v3.y - v1.y
            v2.z = v3.z - v1.z

            //calculate the cross product between the two vectors to obtain
            //a vector orthogonal to the others
            return crossProduct(v1, v2)
        }

    override fun scale(factor: Double) {
        v1.scale(factor)
        v2.scale(factor)
        v3.scale(factor)
    }

    override fun scale(vector: Vertex) {
        v1.scale(vector)
        v2.scale(vector)
        v3.scale(vector)
    }

    override fun rotate(rotationMatrix: RotationMatrix) {
        v1.rotate(rotationMatrix)
        v2.rotate(rotationMatrix)
        v3.rotate(rotationMatrix)
    }

    override fun translate(vector: Vertex) {
        v1.translate(vector)
        v2.translate(vector)
        v3.translate(vector)
    }

    override fun setColor(r: Int, g: Int, b: Int) {
        colorR = r
        colorG = g
        colorB = b
    }
}