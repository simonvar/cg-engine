package io.github.simonvar.cgengine.primitives

import io.github.simonvar.cgengine.matrix.RotationMatrix

interface Transformable {

    /**
     * This method scales all 3 dimensions by the same quantity
     * @param factor scale factor
     */
    fun scale(factor: Double)

    /**
     * This method scales each dimension indipendently
     * x,y and z values of the Vertex object are used separately to scale
     * each dimension respectively
     * @param vector Vertex value
     */
    fun scale(vector: Vertex)

    /**
     * This method rotates the object multiplicating with rotation matrix
     * wich must be created and prepared previously
     * @param rotationMatrix rotation matrix
     */
    fun rotate(rotationMatrix: RotationMatrix)

    /**
     * This method translates an object by the x,y,z values of the
     * vector in the x,y,z directions
     * @param vector translation vector
     */
    fun translate(vector: Vertex)

    /**
     * Sets the color of this transformable
     * @param r red (0..255)
     * @param g green (0..255)
     * @param b blue (0..255)
     */
    fun setColor(r: Int, g: Int, b: Int)

}