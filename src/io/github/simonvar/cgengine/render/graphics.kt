package io.github.simonvar.cgengine.render

import java.awt.Graphics


/**
 * Fills a triangle using the scan line algorithm and the z-buffer algorithm
 * @param gr graphic object
 * @param x x coordinates for all 3 vertexes
 * @param y y coordinates for all 3 vertexes
 * @param z z coordinates for all 3 vertexes
 * @param zbuffer z depth buffer
 */
fun Graphics.fillTriangle(x: IntArray, y: IntArray, z: IntArray, zbuffer: Array<IntArray>) {
    /**
     * sort the arrays according the y value
     * y[0]=min
     * y[2]=max
     */
    if (y[0] > y[1]) {
        //swap values using xor
        y[0] = y[0] xor y[1]
        y[1] = y[1] xor y[0]
        y[0] = y[0] xor y[1]
        //swap values using xor
        x[0] = x[0] xor x[1]
        x[1] = x[1] xor x[0]
        x[0] = x[0] xor x[1]
        //swap values using xor
        z[0] = z[0] xor z[1]
        z[1] = z[1] xor z[0]
        z[0] = z[0] xor z[1]
    }
    if (y[1] > y[2]) {
        //swap values using xor
        y[1] = y[1] xor y[2]
        y[2] = y[2] xor y[1]
        y[1] = y[1] xor y[2]
        //swap values using xor
        x[1] = x[1] xor x[2]
        x[2] = x[2] xor x[1]
        x[1] = x[1] xor x[2]
        //swap values using xor
        z[1] = z[1] xor z[2]
        z[2] = z[2] xor z[1]
        z[1] = z[1] xor z[2]
    }
    if (y[0] > y[1]) {
        //swap values using xor
        y[0] = y[0] xor y[1]
        y[1] = y[1] xor y[0]
        y[0] = y[0] xor y[1]
        //swap values using xor
        x[0] = x[0] xor x[1]
        x[1] = x[1] xor x[0]
        x[0] = x[0] xor x[1]
        //swap values using xor
        z[0] = z[0] xor z[1]
        z[1] = z[1] xor z[0]
        z[0] = z[0] xor z[1]
    }

    var p1x: Int
    var p2x: Int
    var p1z: Int
    var p2z: Int //intersection points with the triangle edges
    var py: Int //y and z coordinates of the current scan line

    val dif1X: Int
    var dif2X: Int
    val dif1Y: Int
    var dif2Y: Int
    val dif1Z: Int
    var dif2Z: Int

    if (y[0] == y[1]) {
        //pre-calculate some values that don't change during the cycle
        //for optimization purposes
        dif1X = x[2] - x[0]
        dif2X = x[2] - x[1]
        dif1Y = y[2] - y[0]
        dif1Z = z[2] - z[0]
        dif2Z = z[2] - z[1]
        py = y[1] + 1
        while (py <= y[2]) {
            //calculate intersection points
            dif2Y = y[2] - py
            p1x = x[2] - dif1X * dif2Y / dif1Y
            p2x = x[2] - dif2X * dif2Y / dif1Y
            p1z = z[2] - dif1Z * dif2Y / dif1Y
            p2z = z[2] - dif2Z * dif2Y / dif1Y
            //start z-buffer
            drawHorizontalLine(py, p1x, p1z, p2x, p2z, zbuffer)
            py++
            //end z-buffer

        }
    } else if (y[1] == y[2]) {
        //pre-calculate some values that don't change during the cycle
        //for optimization purposes
        dif1X = x[2] - x[0]
        dif2X = x[1] - x[0]
        dif1Y = y[1] - y[0]
        dif1Z = z[2] - z[0]
        dif2Z = z[1] - z[0]
        py = y[0]
        while (py <= y[1]) {
            //calculate intersection points
            p1x = x[2] - dif1X * (y[2] - py) / dif1Y
            p2x = x[1] - dif2X * (y[1] - py) / dif1Y
            p1z = z[2] - dif1Z * (y[2] - py) / dif1Y
            p2z = z[1] - dif2Z * (y[1] - py) / dif1Y
            //start z-buffer
            drawHorizontalLine(py, p1x, p1z, p2x, p2z, zbuffer)
            py++
            //end z-buffer
        }

    } else {
        //pre-calculate some values that don't change during the cycle
        //for optimization purposes
        dif1X = x[2] - x[0]
        dif2X = x[1] - x[0]
        dif1Y = y[2] - y[0]
        dif2Y = y[1] - y[0]
        dif1Z = z[2] - z[0]
        dif2Z = z[1] - z[0]
        py = y[0]
        while (py <= y[1]) {
            //calculate intersection points
            p1x = x[2] - dif1X * (y[2] - py) / dif1Y
            p2x = x[1] - dif2X * (y[1] - py) / dif2Y
            p1z = z[2] - dif1Z * (y[2] - py) / dif1Y
            p2z = z[1] - dif2Z * (y[1] - py) / dif2Y
            //start z-buffer
            drawHorizontalLine(py, p1x, p1z, p2x, p2z, zbuffer)
            py++
            //end z-buffer
        }

        //re-calculate what changes in the second cycle
        dif2X = x[2] - x[1]
        dif2Y = y[2] - y[1]
        dif2Z = z[2] - z[1]
        py = y[1] + 1
        while (py <= y[2]) {
            //calculate intersection points
            p1x = x[2] - dif1X * (y[2] - py) / dif1Y
            p2x = x[2] - dif2X * (y[2] - py) / dif2Y
            p1z = z[2] - dif1Z * (y[2] - py) / dif1Y
            p2z = z[2] - dif2Z * (y[2] - py) / dif2Y
            //start z-buffer
            drawHorizontalLine(py, p1x, p1z, p2x, p2z, zbuffer)
            py++
            //end z-buffer
        }

    }

}

/**
 * Draws a line using the z-buffer algorithm
 * @param x1 x coordinate of the first vertex
 * @param y1 y coordinate of the first vertex
 * @param z1 z coordinate of the first vertex
 * @param x2 x coordinate of the second vertex
 * @param y2 y coordinate of the second vertex
 * @param z2 z coordinate of the second vertex
 * @param zbuffer z depth buffer
 */
fun Graphics.drawLine(v1x: Int, v1y: Int, v1z: Int, v2x: Int, v2y: Int, v2z: Int, zbuffer: Array<IntArray>) {
    var x1 = v1x
    var y1 = v1y
    var z1 = v1z
    var x2 = v2x
    var y2 = v2y
    var z2 = v2z
    val dimX = zbuffer.size
    val dimY = zbuffer[0].size
    val difX: Int
    val difY: Int
    val difZ: Int
    var px: Int
    var pz: Int
    var py: Int

    if (y1 > y2) {
        y1 = y1 xor y2
        y2 = y2 xor y1
        y1 = y1 xor y2
        x1 = x1 xor x2
        x2 = x2 xor x1
        x1 = x1 xor x2
        z1 = z1 xor z2
        z2 = z2 xor z1
        z1 = z1 xor z2
    }

    if (x1 in 1 until dimX && y1 > 0 && y1 < dimY && zbuffer[x1][y1] >= z1) {
        zbuffer[x1][y1] = z1
        fillRect(x1, y1, 1, 1)
    }

    difX = x2 - x1
    difY = y2 - y1
    difZ = z2 - z1

    val compDifX: Int = if (x2 > x1) x2 - x1 else x1 - x2

    if (compDifX < y2 - y1) {
        py = y1 + 1
        while (py < y2) {
            px = x1 + (py - y1) * difX / difY
            pz = z1 + (py - y1) * difZ / difY
            if ((px in 1 until dimX) && (py in 1 until dimY) && (zbuffer[px][py] >= pz)) {
                zbuffer[px][py] = pz
                fillRect(px, py, 1, 1)
            }
            py++
        }
    } else if (x1 > x2) {
        px = x2 + 1
        while (px < x1) {
            py = y1 + (px - x1) * difY / difX
            pz = z1 + (px - x1) * difZ / difX
            if ((px in 1 until dimX) && (py in 1 until dimY) && (zbuffer[px][py] >= pz)) {
                zbuffer[px][py] = pz
                fillRect(px, py, 1, 1)
            }
            px++
        }
    } else {
        px = x1 + 1
        while (px < x2) {
            py = y1 + (px - x1) * difY / difX
            pz = z1 + (px - x1) * difZ / difX
            if ((px in 1 until dimX) && (py in 1 until dimY) && (zbuffer[px][py] >= pz)) {
                zbuffer[px][py] = pz
                fillRect(px, py, 1, 1)
            }
            px++
        }
    }

    if ((x2 in 1 until dimX) && (y2 in 1 until dimY) && (zbuffer[x2][y2] >= z2)) {
        zbuffer[x2][y2] = z2
        fillRect(x2, y2, 1, 1)
    }

}

/**
 * Draws a line using the z-buffer algorithm
 * @param y y coordinate
 * @param x1 x coordinate of the first vertex
 * @param z1 z coordinate of the first vertex
 * @param x2 x coordinate of the second vertex
 * @param z2 z coordinate of the second vertex
 * @param zbuffer z depth buffer
 */
fun Graphics.drawHorizontalLine(y: Int, x1: Int, z1: Int, x2: Int, z2: Int, zbuffer: Array<IntArray>) {
    val dimX = zbuffer.size
    val dimY = zbuffer[0].size
    val difX: Int = x2 - x1
    val difZ: Int = z2 - z1
    var px: Int
    var pz: Int

    if ((x1 in 1 until dimX) && (y in 1 until dimY) && (zbuffer[x1][y] >= z1)) {
        zbuffer[x1][y] = z1
        fillRect(x1, y, 1, 1)
    }

    if (x1 > x2) {
        px = x2 + 1
        while (px < x1) {
            pz = z1 + (px - x1) * difZ / difX
            if ((px in 1 until dimX) && (y in 1 until dimY) && (zbuffer[px][y] >= pz)) {
                zbuffer[px][y] = pz
                fillRect(px, y, 1, 1)
            }
            px++
        }
    } else {
        px = x1 + 1
        while (px < x2) {
            pz = z1 + (px - x1) * difZ / difX
            if ((px in 1 until dimX) && (y in 1 until dimY) && (zbuffer[px][y] >= pz)) {
                zbuffer[px][y] = pz
                fillRect(px, y, 1, 1)
            }
            px++
        }
    }

    if ((x2 in 1 until dimX) && (y in 1 until dimY) && (zbuffer[x2][y] >= z2)) {
        zbuffer[x2][y] = z2
        fillRect(x2, y, 1, 1)
    }
}