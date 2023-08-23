package engine

import com.jogamp.newt.opengl.GLWindow
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.awt.Robot

class Engine(var window: PApplet) {
    var world = World()
    private var renderer = Renderer(this)
    private var player = Player(this)
    var robot = Robot()

    fun initialize() {
        world.initialize()
    }

    fun draw() {
        renderer.renderWorld()
        player.update()
    }

    fun keyPressed(event: KeyEvent) {
        this.player.keyPressed(event)
    }

    fun keyReleased(event: KeyEvent) {
        this.player.keyReleased(event)
    }

    fun mouseMoved(event: MouseEvent) {
        val frame = (window.surface.native as GLWindow)
        robot.mouseMove(frame.x+(window.width/2), frame.y+(window.height/2))
        val dx = event.x - window.width/2
        val dy = event.y - window.height/2
        this.player.mouseMoved(dx, dy)
    }
}
