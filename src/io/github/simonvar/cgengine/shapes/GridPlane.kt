package io.github.simonvar.cgengine.shapes

import io.github.simonvar.cgengine.objects.TriangleObject
import io.github.simonvar.cgengine.primitives.Triangle
import io.github.simonvar.cgengine.primitives.Vertex

open class GridPlane(u: Double, v: Double, detailU: Int, detailV: Int) : TriangleObject() {

    init {
        val deltaU: Double = u / detailU
        val deltaV: Double = v / detailV
        for (i in 0 until detailU) {
            for (j in 0 until detailV) {
                parts.add(
                    Triangle(
                        Vertex(i * deltaU, j * deltaV, 0.0),
                        Vertex(i * deltaU, (j + 1) * deltaV, 0.0),
                        Vertex((i + 1) * deltaU, (j + 1) * deltaV, 0.0)
                    )
                )
                parts.add(
                    Triangle(
                        Vertex(i * deltaU, j * deltaV, 0.0),
                        Vertex((i + 1) * deltaU, j * deltaV, 0.0),
                        Vertex((i + 1) * deltaU, (j + 1) * deltaV, 0.0)
                    )
                )
            }
        }
    }

}