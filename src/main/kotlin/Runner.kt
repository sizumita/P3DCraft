import engine.Block
import engine.BlockId
import engine.Engine
import processing.core.PApplet
import processing.event.KeyEvent

class Runner : PApplet() {
    private var engine = Engine(this)

    var controller = Controller(this)

    override fun settings() {
        size(800, 800, P3D)
    }

    override fun setup() {
        engine.initialize()
    }

    override fun keyPressed(event: KeyEvent?) {
        if (event == null) {
            return
        }
        controller.keyPressed(event.key, event.keyCode)
        controller.keyControl()
    }

    override fun keyReleased(event: KeyEvent?) {
        if (event == null) {
            return
        }
        controller.keyReleased(event.key, event.keyCode)
    }

    override fun draw() {
        background(0)
        engine.world.putBlock(0, 1, 0, Block(BlockId.Dirt))
        engine.world.putBlock(1, 1, 0, Block(BlockId.Dirt))
        engine.world.putBlock(0, 1, 1, Block(BlockId.Dirt))
        engine.draw()
        controller.keyControl()
    }
}
