package engine

import processing.core.PConstants.DISABLE_DEPTH_TEST
import processing.core.PConstants.ENABLE_DEPTH_TEST
import processing.opengl.PGraphicsOpenGL

class UIRenderer(val engine: Engine) {
    fun draw() {
        engine.window.pushMatrix()
        engine.window.resetMatrix()
        engine.window.translate(
            (-engine.window.width / 2.0).toFloat(), (-engine.window.height / 2.0).toFloat(),
            ((engine.window.g as PGraphicsOpenGL).projection.m11 * engine.window.height / 2.0).toFloat()
        )
        engine.window.hint(DISABLE_DEPTH_TEST)
        drawCrossHair()
        engine.window.textSize(50F)
        engine.window.text("This is test.", 30F, 60F)
        engine.window.hint(ENABLE_DEPTH_TEST)
        engine.window.popMatrix()
    }

    private fun drawCrossHair() {
        engine.window.pushMatrix()
        engine.window.strokeWeight(2F)
        engine.window.stroke(255)
        engine.window.line(engine.window.width/2F, engine.window.height/2-15F, engine.window.width/2F, engine.window.height/2+15F)
        engine.window.line(engine.window.width/2F-15F, engine.window.height/2F, engine.window.width/2F+15F, engine.window.height/2F)
        engine.window.popMatrix()
    }

    private fun drawInventory() {

    }
}