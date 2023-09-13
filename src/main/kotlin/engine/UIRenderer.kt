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
        drawInventory()
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
        engine.window.pushMatrix()
        engine.window.noStroke()
        engine.window.fill(133F, 130F, 130F)
        val height = engine.window.height
        val width = engine.window.width
        engine.window.rect(width/2-360F, height-80F, 720F, 85F, 5F)
        var offset = 0F
        engine.player.inventory.blocks.forEach {
            val tex = engine.textureManager?.getTexture(it, Face.Top) ?: return
            if (it == engine.player.inventory.getSelectedBlockId()) {
                engine.window.fill(255)
                engine.window.rect(width/2-360F+offset-3F, height-80F-3F, 86F, 90F, 5F)
            }
            engine.window.image(tex, width/2-360F+offset+5, height-80F+5, 70F, 70F)
            offset += 80F
        }

        engine.window.popMatrix()
    }
}