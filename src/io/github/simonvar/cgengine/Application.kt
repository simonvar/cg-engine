package io.github.simonvar.cgengine

import io.github.simonvar.cgengine.ui.ControlPanel
import javax.swing.JFrame
import javax.swing.WindowConstants

class Engine3DApplication private constructor() : JFrame() {
    private val controlPanel = ControlPanel()

    init {
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.setBounds(100, 60, 700, 500)
        this.contentPane = controlPanel
        this.title = "Kotlin Simple 3D Engine"
        this.isVisible = true

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Engine3DApplication()
        }

    }


}