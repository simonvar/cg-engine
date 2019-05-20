package io.github.simonvar.cgengine.matrix

import io.github.simonvar.cgengine.primitives.Vertex

open class Matrix(
    var e11: Double = 0.0,
    var e12: Double = 0.0,
    var e13: Double = 0.0,
    var e14: Double = 0.0,
    var e21: Double = 0.0,
    var e22: Double = 0.0,
    var e23: Double = 0.0,
    var e24: Double = 0.0,
    var e31: Double = 0.0,
    var e32: Double = 0.0,
    var e33: Double = 0.0,
    var e34: Double = 0.0,
    var e41: Double = 0.0,
    var e42: Double = 0.0,
    var e43: Double = 0.0,
    var e44: Double = 0.0
) {


    /**
     * Creates a copy of the matrix given as parameter
     * @param parentMatrix matrix to be copied
     */
    constructor(parentMatrix: Matrix) : this(
        parentMatrix.e11,
        parentMatrix.e12,
        parentMatrix.e13,
        parentMatrix.e14,
        parentMatrix.e21,
        parentMatrix.e22,
        parentMatrix.e23,
        parentMatrix.e24,
        parentMatrix.e31,
        parentMatrix.e32,
        parentMatrix.e33,
        parentMatrix.e34,
        parentMatrix.e41,
        parentMatrix.e42,
        parentMatrix.e43,
        parentMatrix.e44
    )

    /**
     * Multiplies itself with another matrix
     * @param matrix the matrix to be multiplied with
     */
    fun multiply(matrix: Matrix) {
        //creates a copy of itself to use for the calulations
        val a = Matrix(this)

        /*
         matrix multiplication is made directly and not using for cycle which
         would take less code but become slower
         */
        this.e11 = a.e11 * matrix.e11 + a.e12 * matrix.e21 + a.e13 * matrix.e31 + a.e14 * matrix.e41
        this.e21 = a.e21 * matrix.e11 + a.e22 * matrix.e21 + a.e23 * matrix.e31 + a.e24 * matrix.e41
        this.e31 = a.e31 * matrix.e11 + a.e32 * matrix.e21 + a.e33 * matrix.e31 + a.e34 * matrix.e41
        this.e41 = a.e41 * matrix.e11 + a.e42 * matrix.e21 + a.e43 * matrix.e31 + a.e44 * matrix.e41

        this.e12 = a.e11 * matrix.e12 + a.e12 * matrix.e22 + a.e13 * matrix.e32 + a.e14 * matrix.e42
        this.e22 = a.e21 * matrix.e12 + a.e22 * matrix.e22 + a.e23 * matrix.e32 + a.e24 * matrix.e42
        this.e32 = a.e31 * matrix.e12 + a.e32 * matrix.e22 + a.e33 * matrix.e32 + a.e34 * matrix.e42
        this.e42 = a.e41 * matrix.e12 + a.e42 * matrix.e22 + a.e43 * matrix.e32 + a.e44 * matrix.e42

        this.e13 = a.e11 * matrix.e13 + a.e12 * matrix.e23 + a.e13 * matrix.e33 + a.e14 * matrix.e43
        this.e23 = a.e21 * matrix.e13 + a.e22 * matrix.e23 + a.e23 * matrix.e33 + a.e24 * matrix.e43
        this.e33 = a.e31 * matrix.e13 + a.e32 * matrix.e23 + a.e33 * matrix.e33 + a.e34 * matrix.e43
        this.e43 = a.e41 * matrix.e13 + a.e42 * matrix.e23 + a.e43 * matrix.e33 + a.e44 * matrix.e43

        this.e14 = a.e11 * matrix.e14 + a.e12 * matrix.e24 + a.e13 * matrix.e34 + a.e14 * matrix.e44
        this.e24 = a.e21 * matrix.e14 + a.e22 * matrix.e24 + a.e23 * matrix.e34 + a.e24 * matrix.e44
        this.e34 = a.e31 * matrix.e14 + a.e32 * matrix.e24 + a.e33 * matrix.e34 + a.e34 * matrix.e44
        this.e44 = a.e41 * matrix.e14 + a.e42 * matrix.e24 + a.e43 * matrix.e34 + a.e44 * matrix.e44
    }

    /**
     * Multiplies itself with a vertex, the most used operation
     * applied to all object vertexes during transformations
     * @param vertex the vertex to be transformed
     */
    fun multiply(vertex: Vertex) {
        //creates a copy of the old values necessary for the calculations
        val x = vertex.x
        val y = vertex.y
        val z = vertex.z
        val w = vertex.w

        //calculates the values updating directly the vertex given as parameter
        //and not by creating a new object, for memory and performance issues
        vertex.x = this.e11 * x + this.e12 * y + this.e13 * z + this.e14 * w
        vertex.y = this.e21 * x + this.e22 * y + this.e23 * z + this.e24 * w
        vertex.z = this.e31 * x + this.e32 * y + this.e33 * z + this.e34 * w
        vertex.w = this.e41 * x + this.e42 * y + this.e43 * z + this.e44 * w
    }

}