import engine.Block
import engine.BlockId
import engine.Engine
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.awt.Robot

class Runner : PApplet() {
    private var engine = Engine(this)

    private var robot = Robot()


    override fun settings() {
        size(1280, 800, P3D)
    }

    override fun setup() {
        engine.initialize()
        camera(0F, -4000F, 0F, 0F, 0F, 0F, -1F, 0F, 0F)
    }

    override fun keyPressed(event: KeyEvent?) {
        if (event == null || !focused) return;
        engine.keyPressed(event)
    }

    override fun keyReleased(event: KeyEvent?) {
        if (event == null || !focused) return;
        engine.keyReleased(event)
    }

    override fun mouseMoved(event: MouseEvent?) {
        if (event == null || !focused) return
        engine.mouseMoved(event)
    }

    override fun mouseDragged(event: MouseEvent?) {
        if (event == null || !focused) return
        engine.mouseMoved(event)
    }

    override fun mousePressed(event: MouseEvent?) {
        if (event == null || !focused) return
        engine.mousePressed(event)
    }

    override fun draw() {
//        if (frameCount == 10) {
//            val frame = (getSurface().native as GLWindow)
//            // 画面の中心にカーソルを合わせる処理。 NOTE: これにはアクセシビリティ機能の有効化が必要
//            robot.mouseMove(frame.x+(width/2), frame.y+(height/2))
//        }
        background(0)
        engine.world.putBlock(0, 31, 0, Block(BlockId.Dirt))
//        engine.world.putBlock(0, 32, 0, Block(BlockId.Dirt))
//        engine.world.putBlock(1, 31, 0, Block(BlockId.Dirt))
//        engine.world.putBlock(0, 31, 1, Block(BlockId.Stone))
        engine.draw()
    }
}
