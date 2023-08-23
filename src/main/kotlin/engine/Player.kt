package engine

import com.jogamp.newt.opengl.GLWindow
import engine.enums.Key
import processing.core.PMatrix3D
import processing.event.KeyEvent
import processing.event.MouseEvent
import processing.opengl.PGraphics3D
import java.util.EnumSet

class Player(private val engine: Engine) {
    companion object {
        private const val mouseRotationSpeed = 0.002f
        private const val moveSpeed = 10f
    }
    private val pressedKeys = EnumSet.noneOf(Key::class.java)

    fun mousePressed(event: MouseEvent) {

    }

    fun mouseMoved(dx: Int, dy: Int) {
        val m = PMatrix3D()
        m.rotateX(mouseRotationSpeed * dy * -1);
        m.rotateY(mouseRotationSpeed * dx)

        val c = (engine.window.g as PGraphics3D).camera.get()
        c.preApply(m)

        c.invert()

        val ex: Float = c.m03
        val ey: Float = c.m13
        val ez: Float = c.m23
        val cx: Float = -c.m02 + ex
        val cy: Float = -c.m12 + ey
        val cz: Float = -c.m22 + ez

        engine.window.camera( ex, ey, ez, cx, cy, cz, 0f, 1f, 0f )
    }

    fun keyPressed(event: KeyEvent) {
        var key = Key.fromKey(event.key)
        if (key == null) key = Key.fromKeyCode(event.keyCode)
        if (key == null) return
        pressedKeys.add(key)
    }

    fun keyReleased(event: KeyEvent) {
        var key = Key.fromKey(event.key)
        if (key == null) key = Key.fromKeyCode(event.keyCode)
        if (key == null) return
        pressedKeys.remove(key)
    }

    fun update() {
        if (pressedKeys.isEmpty()) return
        val m = PMatrix3D()
        val speed = if (pressedKeys.contains(Key.Ctrl)) moveSpeed * 2 else moveSpeed

        var moveToUp = false
        var moveToDown = false

        if (pressedKeys.contains(Key.W))
            m.translate(0f, 0f, speed)
        if (pressedKeys.contains(Key.S))
            m.translate(0f, 0f, -speed)
        if (pressedKeys.contains(Key.A))
            m.translate(speed, 0f, 0f)
        if (pressedKeys.contains(Key.D))
            m.translate(-speed, 0f, 0f)
        if (pressedKeys.contains(Key.Space))
            moveToUp = true
        if (pressedKeys.contains(Key.Shift))
            moveToDown = true

        val c = (engine.window.g as PGraphics3D).camera.get()
        c.preApply(m)

        c.invert()

        val ex: Float = c.m03
        val ey: Float = c.m13 + (if (moveToUp) (-10) else 0) + (if (moveToDown) (10) else 0)
        val ez: Float = c.m23
        val cx: Float = -c.m02 + ex
        val cy: Float = -c.m12 + ey
        val cz: Float = -c.m22 + ez

        engine.window.camera( ex, ey, ez, cx, cy, cz, 0f, 1f, 0f )
    }
}
