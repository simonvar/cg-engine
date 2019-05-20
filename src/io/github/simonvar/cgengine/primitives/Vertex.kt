package io.github.simonvar.cgengine.primitives

import io.github.simonvar.cgengine.matrix.RotationMatrix

data class Vertex(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var w: Double = 1.0
) : Transformable {

    val length: Double
        get() = Math.sqrt(x * x + y * y + z * z)

    val unitVector: Vertex
        get() {
            val unit = Vertex()
            unit.x = x / length
            unit.y = y / length
            unit.z = z / length
            return unit
        }

    fun cos(v: Vertex): Double = dotProduct(unitVector, v.unitVector)

    override fun scale(factor: Double) {
        this.x *= factor
        this.y *= factor
        this.z *= factor
    }

    override fun scale(vector: Vertex) {
        this.x *= vector.x
        this.y *= vector.y
        this.z *= vector.z
    }

    override fun rotate(rotationMatrix: RotationMatrix) {
        rotationMatrix.multiply(this)
    }

    override fun translate(vector: Vertex) {
        this.x += vector.x
        this.y += vector.y
        this.z += vector.z
    }


    override fun setColor(r: Int, g: Int, b: Int) {
        //not implemented for the moment
        //will be useful when implementing phong shading
    }

}