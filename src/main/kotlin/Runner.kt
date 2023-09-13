import launcher.Launcher
import processing.core.PApplet


class Runner : PApplet() {
    private var launcher = Launcher(this)

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        noStroke()
        fill(255)
        rect(width/2F-100, 400F, 200F, 60F, 5F)
        textAlign(CENTER, CENTER)
        textSize(20F)
        launcher.setup()
    }

    override fun mousePressed() {
        println(mouseX)
        println(mouseY)
        if (width/2F-100 < mouseX && mouseX < width/2F+100 && 400 < mouseY && mouseY < 460) {
            println("ok")
            launcher.createNew()
        }
    }

    override fun draw() {
        fill(255)
        rect(width/2F-100, 400F, 200F, 60F, 5F)
        fill(0)
        text("Create New", width/2F-100, 400F, 200F, 60F)
    }
}
