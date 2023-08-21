import com.jogamp.newt.opengl.GLWindow
import engine.Block
import engine.BlockId
import engine.Engine
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.awt.Robot

class Runner : PApplet() {
    private var engine = Engine(this)

    private var controller = Controller(this)

    private var robot = Robot()


    override fun settings() {
        size(1280, 800, P3D)
    }

    override fun setup() {
//        surface.setResizable(true)
        engine.initialize()
        camera(0F, -4000F, 0F, 0F, 0F, 0F, -1F, 0F, 0F)
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

    override fun mouseMoved(event: MouseEvent?) {
        if (event == null || !focused) return
        val frame = (getSurface().native as GLWindow)
        // 画面の中心にカーソルを合わせる処理。 NOTE: これにはアクセシビリティ機能の有効化が必要
        robot.mouseMove(frame.x+(width/2), frame.y+(height/2))

        val dx = event.x - width/2
        val dy = event.y - height/2
        controller.mouseControl(dx, dy)
    }

    override fun draw() {
        if (frameCount == 0) {
            val frame = (getSurface().native as GLWindow)
            // 画面の中心にカーソルを合わせる処理。 NOTE: これにはアクセシビリティ機能の有効化が必要
            robot.mouseMove(frame.x+(width/2), frame.y+(height/2))
        }
        background(0)
        engine.world.putBlock(0, 31, 0, Block(BlockId.Dirt))
        engine.world.putBlock(0, 32, 0, Block(BlockId.Dirt))
        engine.world.putBlock(1, 31, 0, Block(BlockId.Dirt))
        engine.world.putBlock(0, 31, 1, Block(BlockId.Dirt))
        engine.draw()
        controller.keyControl()
    }
}
