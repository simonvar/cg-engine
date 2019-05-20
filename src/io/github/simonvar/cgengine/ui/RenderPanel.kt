package io.github.simonvar.cgengine.ui

import io.github.simonvar.cgengine.matrix.RotationMatrix
import io.github.simonvar.cgengine.objects.Object3D
import io.github.simonvar.cgengine.objects.World
import io.github.simonvar.cgengine.primitives.Transformable
import io.github.simonvar.cgengine.primitives.Vertex
import io.github.simonvar.cgengine.render.Render
import io.github.simonvar.cgengine.render.ZBufferRender
import io.github.simonvar.cgengine.shapes.Axes
import io.github.simonvar.cgengine.shapes.GridPlane
import io.github.simonvar.cgengine.shapes.Sphere
import io.github.simonvar.cgengine.shapes.Torus
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.image.ImageObserver
import javax.swing.JPanel

class RenderPanel : JPanel(), Transformable, Render {
    private val objectList = hashMapOf<String, Object3D>()
    private var world: World = World()
    private var renderer: Render = ZBufferRender()

    init {
        val plane = GridPlane(200.0, 200.0, 20, 20)
        plane.translate(Vertex(-100.0, -100.0, 0.0))
        plane.axes = Axes(100.0)

        val sphere = Sphere(1.0, 50, 50)
        sphere.scale(90.0)
        sphere.setColor(0, 0, 255)
        sphere.axes = Axes(180.0)

        val torus = Torus(15.0, 30, 5.0, 20)
        torus.scale(5.0)
        torus.axes = Axes(50.0)

        objectList["GridPlane"] = plane
        objectList["Sphere"] = sphere
        objectList["Torus"] = torus

        world.add(plane)
        world.add(sphere)
        world.add(torus)
    }

    override var isRenderAxis: Boolean
        get() = renderer.isRenderAxis
        set(value) {
            renderer.isRenderAxis = value
        }

    override var isWireframed: Boolean
        get() = renderer.isWireframed
        set(value) {
            renderer.isWireframed = value
        }

    override var isFilled: Boolean
        get() = renderer.isFilled
        set(value) {
            renderer.isFilled = value
        }


    /**
     * Superclass method override.
     * This method creates an offscreen image and renders offscreen first
     * before effectively drawing the result in the panel graphics for speed and
     * image flickering problem
     * @see javax.swing.JPanel#paint(java.awt.Graphics)
     * @param gr Graphics object used to paint
     */
    override fun paint(g: Graphics?) {
        //create an offscreen image of the size of this panel
        val view = createImage(this.width, this.height)

        val igr = view.graphics
        igr.color = Color.black
        igr.fillRect(this.x, this.y, this.width, this.height)

        renderer.render(world, view, this)
        g?.drawImage(view, 0, 0, this)
    }

    /**
     * It is invoked to select and change the current rendered object
     * @param objName the key of the object contained in the map
     */
    fun select(objName: String) {
        world.clear()
        world.add(objectList[objName] as Object3D)
        repaint()
    }

    /**
     * Gets the iterator object of the key set of the map containing the objects.
     * This is useful to the control panel that will be using this one to know the objects
     * contained herein
     * @return the key set iterator object
     */
    fun getShapeList(): Iterator<String> {
        return objectList.keys.iterator()
    }

    fun render() {
        repaint()
    }


    override fun scale(factor: Double) {
        world.scale(factor)
        repaint()
    }

    override fun scale(vector: Vertex) {
        world.scale(vector)
        repaint()
    }

    override fun rotate(rotationMatrix: RotationMatrix) {
        world.rotate(rotationMatrix)
        repaint()
    }

    override fun translate(vector: Vertex) {
        world.translate(vector)
        repaint()
    }

    override fun setWireframeColor(r: Int, g: Int, b: Int) {
        renderer.setWireframeColor(r, g, b)
        repaint()
    }

    override fun render(world: World, view: Image, observer: ImageObserver) {}

    override fun setColor(r: Int, g: Int, b: Int) {}


}