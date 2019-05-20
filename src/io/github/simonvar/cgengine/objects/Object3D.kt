package io.github.simonvar.cgengine.objects

import io.github.simonvar.cgengine.matrix.RotationMatrix
import io.github.simonvar.cgengine.primitives.Transformable
import io.github.simonvar.cgengine.primitives.Vertex
import io.github.simonvar.cgengine.shapes.Axes

abstract class Object3D(
    val parts: MutableList<Transformable> = mutableListOf(),
    var axes: Axes? = null
) : Transformable {

    fun add(part: Transformable) {
        parts += part
    }

    override fun scale(factor: Double) {
        parts.forEach {
            it.scale(factor)
        }
        axes?.scale(factor)
    }

    override fun scale(vector: Vertex) {
        parts.forEach {
            it.scale(vector)
        }
        axes?.scale(vector)
    }

    override fun rotate(rotationMatrix: RotationMatrix) {
        parts.forEach {
            it.rotate(rotationMatrix)
        }
        axes?.rotate(rotationMatrix)
    }

    override fun translate(vector: Vertex) {
        parts.forEach {
            it.translate(vector)
        }
        axes?.translate(vector)
    }

    override fun setColor(r: Int, g: Int, b: Int) {
        parts.forEach {
            it.setColor(r, g, b)
        }
    }

}