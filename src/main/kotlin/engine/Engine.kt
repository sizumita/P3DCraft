package engine

import com.jogamp.newt.opengl.GLWindow
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.awt.Robot

class Engine() {
    var world = World()
    private var renderer = Renderer(this)
    private var uiRenderer = UIRenderer(this)
    var player = Player(this)
    private var robot = Robot()
    var window = Window(this)
    private var isStarted = false

    fun start() {
        if (!isStarted) PApplet.runSketch(arrayOf("abc"), window)
        isStarted = true
    }

    // TODO: windowに移行する
    fun loadTexture(name: String): PImage {
        val img = window.loadImage(name)
        img.resize(1000, 1000)
        return img
    }

    fun initialize() {
        world.initialize()
        renderer.initialize()
    }

    fun draw() {
        player.update()
        renderer.renderWorld()
        uiRenderer.draw()
    }

    fun keyPressed(event: KeyEvent) {
        this.player.keyPressed(event)
    }

    fun keyReleased(event: KeyEvent) {
        this.player.keyReleased(event)
    }
    fun mousePressed(event: MouseEvent) {
        val face = player.getLookingAt() ?: return
        val eyePosition = World.toWorldPosition(player.getEyePosition())
        if ((face.getPosition() == eyePosition) or (face.getPosition().below() == eyePosition)) {
            return
        }
        when (event.button) {
            PConstants.LEFT -> world.removeBlock(face.x, face.y, face.z)
            PConstants.RIGHT -> world.putBlockNextToFace(face.x, face.y, face.z, face.face, Block(player.inventory.getSelectedBlockId()))
        }
    }

    fun mouseMoved(event: MouseEvent) {
        val frame = (window.surface.native as GLWindow)
        robot.mouseMove(frame.x+(window.width/2), frame.y+(window.height/2))
        val dx = event.x - window.width/2
        val dy = event.y - window.height/2
        this.player.mouseMoved(dx, dy)
    }
}
