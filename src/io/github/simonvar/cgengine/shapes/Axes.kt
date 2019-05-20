package io.github.simonvar.cgengine.shapes

import io.github.simonvar.cgengine.objects.SegmentObject
import io.github.simonvar.cgengine.primitives.Segment
import io.github.simonvar.cgengine.primitives.Vertex

class Axes(length: Double) : SegmentObject() {

    init {
        val o = Vertex(0.0, 0.0, 0.0) //origin vertex
        val x = Vertex(length, 0.0, 0.0) //X direction axis
        val y = Vertex(0.0, length, 0.0) //Y direction axis
        val z = Vertex(0.0, 0.0, length) //Z direction axis

        val ox = Segment(o, x)
        ox.setColor(255, 0, 0)
        val oy = Segment(o, y)
        oy.setColor(0, 255, 0)
        val oz = Segment(o, z)
        oz.setColor(0, 0, 255)

        parts.add(ox)
        parts.add(oy)
        parts.add(oz)
    }

}