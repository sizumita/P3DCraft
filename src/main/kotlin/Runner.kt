import engine.Block
import engine.BlockId
import engine.Engine
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent


class Runner : PApplet() {
    private var engine = Engine()

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        noStroke()
        fill(255)
        rect(width/2F-100, 400F, 200F, 60F, 5F)
    }

    override fun mousePressed() {
        println(mouseX)
        println(mouseY)
        if (width/2F-100 < mouseX && mouseX < width/2F+100 && 400 < mouseY && mouseY < 460) {
            println("ok")
            engine.start()
        }
    }

    override fun draw() {

    }
}
