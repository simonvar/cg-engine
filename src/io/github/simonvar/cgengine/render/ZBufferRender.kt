package io.github.simonvar.cgengine.render

import io.github.simonvar.cgengine.objects.Object3D
import io.github.simonvar.cgengine.objects.SegmentObject
import io.github.simonvar.cgengine.objects.TriangleObject
import io.github.simonvar.cgengine.objects.World
import io.github.simonvar.cgengine.primitives.Segment
import io.github.simonvar.cgengine.primitives.Triangle
import io.github.simonvar.cgengine.primitives.Vertex
import java.awt.Color
import java.awt.Image
import java.awt.image.ImageObserver

class ZBufferRender : Render {

    override var isRenderAxis: Boolean = false
    override var isWireframed: Boolean = false
    override var isFilled: Boolean = true

    private var wireframeRed = 255 //red wireframe color component
    private var wireframeGreen = 0 //green wireframe color component
    private var wireframeBlue = 0  //blue wireframe color component

    override fun render(world: World, view: Image, observer: ImageObserver) {

        val w = view.getWidth(observer) //obtain image width
        val h = view.getHeight(observer) //obtain image height

        val zbuffer = Array(w) {
            IntArray(h)
        }

        //initialize zbuffer with tha max depth value
        for (k in 0 until w) {
            for (n in 0 until h) {
                zbuffer[k][n] = 65534
            }
        }
        for (i in 0 until world.parts.size) {
            val object3d = world.parts[i]

            //render the object appropriately according to their type
            when (object3d) {
                is SegmentObject -> {
                    renderSegmentBased(object3d, view, observer, zbuffer)
                    if (isRenderAxis) renderSegmentBased(object3d.axes, view, observer, zbuffer)
                }
                is TriangleObject -> {
                    renderTriangleBased(object3d, view, observer, zbuffer)
                    if (isRenderAxis) renderSegmentBased(object3d.axes, view, observer, zbuffer)
                }
            }

        }
    }

    /**
     * Renders segment based objects
     * @param object3d object to be rendered
     * @param view off screen image
     * @param observer image observer object
     * @param zbuffer z depth buffer
     */
    private fun renderSegmentBased(
        object3d: Object3D?,
        view: Image,
        observer: ImageObserver,
        zbuffer: Array<IntArray>
    ) {
        val gr = view.graphics //obtain graphic object from off screen image

        val w = view.getWidth(observer) //obtain image width
        val h = view.getHeight(observer) //obtain image height

        var i = 0
        while (object3d != null && i < object3d.parts.size) {
            val segment = object3d.parts[i] as Segment

            //sets the color of the graphic object used to draw
            gr.color = Color(segment.colorR, segment.colorG, segment.colorB)

            //calculate perspective transformation for all vertexes
            //and for x,y and z coordinates
            val x1: Int = ((segment.start.x / (1 + segment.start.z / D)) + w / 2).toInt()
            val x2: Int = ((segment.end.x / (1 + segment.end.z / D)) + w / 2).toInt()

            val y1: Int = ((segment.start.y / (1 + segment.start.z / D)) + h / 2).toInt()
            val y2: Int = ((segment.end.y / (1 + segment.end.z / D)) + h / 2).toInt()

            val z1: Int = segment.start.z.toInt()
            val z2: Int = segment.end.z.toInt()

            gr.drawLine(x1, y1, z1, x2, y2, z2, zbuffer)
            i++
        }
    }

    /**
     * Renders triangle based objects
     * @param object3d object to be rendered
     * @param view off screen image
     * @param observer image observer object
     * @param zbuffer z depth buffer
     */
    private fun renderTriangleBased(
        object3d: Object3D,
        view: Image,
        observer: ImageObserver,
        zbuffer: Array<IntArray>
    ) {
        val gr = view.graphics //obtain graphic object from offscreen image

        val w = view.getWidth(observer) //obtain image width
        val h = view.getHeight(observer) //obtain image height

        for (i in 0 until object3d.parts.size) {
            val tri = object3d.parts[i] as Triangle

            //calculate perspective transformation for all vertexes
            //and for x,y and z coordinates
            val x = IntArray(3)
            x[0] = ((tri.v1.x / (1 + tri.v1.z / D)) + w / 2).toInt()
            x[1] = ((tri.v2.x / (1 + tri.v2.z / D)) + w / 2).toInt()
            x[2] = ((tri.v3.x / (1 + tri.v3.z / D)) + w / 2).toInt()

            val y = IntArray(3)
            y[0] = ((tri.v1.y / (1 + tri.v1.z / D)) + h / 2).toInt()
            y[1] = ((tri.v2.y / (1 + tri.v2.z / D)) + h / 2).toInt()
            y[2] = ((tri.v3.y / (1 + tri.v3.z / D)) + h / 2).toInt()

            val z = IntArray(3)
            z[0] = tri.v1.z.toInt()
            z[1] = tri.v2.z.toInt()
            z[2] = tri.v3.z.toInt()


            /*
			calculates cos between triangle normal and light direction vector
			and calculate color intensity in function of cos value
			 */
            val cos = Math.abs(
                tri.normal.cos(
                    Vertex(
                        Math.cos(Math.PI / 2),
                        Math.cos(Math.PI / 2) / 2,
                        Math.cos(Math.PI / 2)
                    )
                )
            )
            val r: Int = (cos * tri.colorB).toInt()
            val g: Int = (cos * tri.colorG).toInt()
            val b: Int = (cos * tri.colorB).toInt()

            gr.color = Color(r, g, b)

            if (isFilled) gr.fillTriangle(x, y, z, zbuffer)

            if (isWireframed) {
                gr.color = Color(wireframeRed, wireframeGreen, wireframeBlue)
                gr.drawLine(x[0], y[0], z[0], x[1], y[1], z[1], zbuffer)
                gr.drawLine(x[1], y[1], z[1], x[2], y[2], z[2], zbuffer)
                gr.drawLine(x[2], y[2], z[2], x[0], y[0], z[0], zbuffer)
            }

        }
    }

    override fun setWireframeColor(r: Int, g: Int, b: Int) {
        if (r in 0..255) this.wireframeRed = r
        if (g in 0..255) this.wireframeGreen = g
        if (b in 0..255) this.wireframeBlue = b
    }


    companion object {
        const val D = 400.0
    }
}