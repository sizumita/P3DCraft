package engine

import com.jogamp.opengl.GL
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent
import processing.opengl.*;

class Window(private val engine: Engine) : PApplet() {
    override fun settings() {
        size(1280, 800, P3D)
    }

    override fun setup() {
        engine.initialize()
        camera(10F, -4000F, 10F, 0.5F, 0F, 0.5F, 0F, 1F, 0F)
        engine.world.putBlock(0, 31, 0, Block(BlockId.Stone))
        noCursor()
        textureMode(NORMAL)
        // TODO: テクスチャどうにかする
        (g as PGraphics3D).pgl.texParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST)
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
        background(139F, 197F, 250F)
        engine.draw()
    }
}