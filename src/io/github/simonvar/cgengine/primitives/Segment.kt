package io.github.simonvar.cgengine.primitives

import io.github.simonvar.cgengine.matrix.RotationMatrix

data class Segment(
    val start: Vertex,
    val end: Vertex,
    var colorR: Int = 255,
    var colorG: Int = 255,
    var colorB: Int = 255
) : Transformable {

    override fun scale(factor: Double) {
        start.scale(factor)
        end.scale(factor)
    }

    override fun scale(vector: Vertex) {
        start.scale(vector)
        end.scale(vector)
    }

    override fun rotate(rotationMatrix: RotationMatrix) {
        start.rotate(rotationMatrix)
        end.rotate(rotationMatrix)
    }

    override fun translate(vector: Vertex) {
        start.translate(vector)
        end.translate(vector)
    }

    override fun setColor(r: Int, g: Int, b: Int) {
        colorR = r
        colorG = g
        colorB = b
    }

}