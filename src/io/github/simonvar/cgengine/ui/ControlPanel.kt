package io.github.simonvar.cgengine.ui

import io.github.simonvar.cgengine.matrix.RotationMatrix
import java.awt.Color
import java.awt.GridLayout
import java.awt.Point
import java.awt.event.*
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JSplitPane

class ControlPanel : JSplitPane(), ItemListener, MouseMotionListener, MouseListener {
    private val renderPanel = RenderPanel()

    private val enableFilled = JCheckBox("Filled", renderPanel.isFilled)
    private var enableWireframed = JCheckBox("Wireframed", renderPanel.isWireframed)
    private var enableAxes = JCheckBox("Show Coordinate Axes", renderPanel.isRenderAxis)

    private var rendererOptionPanel = JPanel(GridLayout(3, 1)).apply {
        add(enableFilled)
        add(enableWireframed)
        add(enableAxes)
    }

    //object selection panel
    private var selection = JComboBox<String>()

    private var startPoint: Point? = null //used to save the previous point when the mouse is draged
    private var endPoint: Point? = null //used to save the last point when the mouse is draged

    private val controlPanel = JPanel().apply {
        background = Color.lightGray
        add(selection)
        add(rendererOptionPanel)
    }

    init {
        initUserInterface()
    }

    /**
     * All user interface component initialization and configuration
     * is done here.
     */
    private fun initUserInterface() {
        renderPanel.addMouseListener(this)
        renderPanel.addMouseMotionListener(this)

        //renderer options panel
        enableFilled.addChangeListener {
            renderPanel.isFilled = enableFilled.isSelected
            renderPanel.render()
        }

        enableWireframed.addChangeListener {
            renderPanel.isWireframed = enableWireframed.isSelected
            renderPanel.render()
        }

        enableAxes.addChangeListener {
            renderPanel.isRenderAxis = enableAxes.isSelected
            renderPanel.render()
        }


        //object selection panel
        selection.setSize(100, 50)
        renderPanel.getShapeList().forEach {
            selection.addItem(it)
        }
        selection.addItemListener(this)


        //main content split pane
        setDividerSize(3)
        resizeWeight = 1.0
        autoscrolls = false
        isContinuousLayout = true
        setLastDividerLocation(-1)
        isRequestFocusEnabled = true
        //add renderer panel on the left
        setLeftComponent(renderPanel)
        //add the control panel on the right
        setRightComponent(controlPanel)

    }


    override fun itemStateChanged(itemEvent: ItemEvent) {
        val o = itemEvent.item
        renderPanel.select(o as String)
    }

    private fun rotateFromDrag() {
        val angleMultiplyFactor = 0.02
        val angleY = angleMultiplyFactor * (endPoint!!.getX() - startPoint!!.getX())
        val angleX = angleMultiplyFactor * (endPoint!!.getY() - startPoint!!.getY())
        renderPanel.rotate(RotationMatrix(-angleX, angleY, 0.0))
    }

    private fun scaleFromDrag() {
        val scaleMultiplyFactor = 0.01
        val scaleFactor = scaleMultiplyFactor * (endPoint!!.getY() - startPoint!!.getY())
        renderPanel.scale(1 + scaleFactor)
    }

    /**
     * Invoked when the mouse button is pressed
     * @param event mouse button pressed event
     */
    override fun mousePressed(event: MouseEvent) {
        startPoint = event.point
    }

    /**
     * Invoked when the mouse is draged
     * @param event mouse draged event
     */
    override fun mouseDragged(event: MouseEvent) {
        endPoint = event.point

        if (event.modifiers and InputEvent.BUTTON1_MASK == InputEvent.BUTTON1_MASK) {
            rotateFromDrag()
        } else if (event.modifiers and InputEvent.BUTTON3_MASK == InputEvent.BUTTON3_MASK) {
            scaleFromDrag()
        }

        startPoint = endPoint
    }


    override fun mouseMoved(event: MouseEvent) {}
    override fun mouseClicked(event: MouseEvent) {}
    override fun mouseEntered(event: MouseEvent) {}
    override fun mouseExited(event: MouseEvent) {}
    override fun mouseReleased(event: MouseEvent) {}
}