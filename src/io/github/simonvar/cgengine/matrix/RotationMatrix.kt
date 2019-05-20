package io.github.simonvar.cgengine.matrix

class RotationMatrix(x_angle: Double, y_angle: Double, z_angle: Double) : Matrix(
    1.0, 0.0, 0.0, 0.0,
    0.0, Math.cos(x_angle), Math.sin(x_angle), 0.0,
    0.0, -Math.sin(x_angle), Math.cos(x_angle), 0.0,
    0.0, 0.0, 0.0, 1.0
) {
    init {

        //multiplies with the Y rotation matrix
        multiply(
            Matrix(
                Math.cos(y_angle), 0.0, -Math.sin(y_angle), 0.0,
                0.0, 1.0, 0.0, 0.0,
                Math.sin(y_angle), 0.0, Math.cos(y_angle), 0.0,
                0.0, 0.0, 0.0, 1.0
            )
        )

        //multiplies with the Z rotation matrix
        multiply(
            Matrix(
                Math.cos(z_angle), Math.sin(z_angle), 0.0, 0.0,
                -Math.sin(z_angle), Math.cos(z_angle), 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0
            )
        )
    }
}
