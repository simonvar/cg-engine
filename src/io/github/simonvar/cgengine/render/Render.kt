package io.github.simonvar.cgengine.render

import io.github.simonvar.cgengine.objects.World
import java.awt.Image
import java.awt.image.ImageObserver

interface Render {

    var isRenderAxis: Boolean
    var isWireframed: Boolean
    var isFilled: Boolean

    /**
     * This method is called to effectively render a world
     * @param world world object containing the set of 3d objects
     * @param view off screen image to render the world in
     * @param observer image observer object necessary to get width and height of the
     * off screen image which can change in dimension dynamically
     */

    fun render(world: World, view: Image, observer: ImageObserver)

    /**
     * Sets the wire frame color
     * @param r red component
     * @param g green component
     * @param b blue component
     */
    fun setWireframeColor(r: Int, g: Int, b: Int)


}