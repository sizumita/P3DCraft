package engine

import engine.enums.Key
import engine.structs.Position
import engine.structs.RealFace
import processing.core.PConstants
import processing.core.PMatrix3D
import processing.core.PVector
import processing.event.KeyEvent
import processing.opengl.PGraphics3D
import java.util.EnumSet

class Player(private val engine: Engine) {
    companion object {
        private const val mouseRotationSpeed = 0.002f
        private const val moveSpeed = 10f
    }

    private val pressedKeys = EnumSet.noneOf(Key::class.java)
    var lookingAt: Position? = null
    val inventory = Inventory(engine)

    private fun unProject(): PVector? {
        val projection = (engine.window.g as PGraphics3D).projection
        val modelView = (engine.window.g as PGraphics3D).modelview
        val viewport = PMatrix3D()
        viewport.m00 = engine.window.width / 2F
        viewport.m03 = engine.window.width / 2F
        viewport.m11 = -engine.window.height / 2F
        viewport.m13 = engine.window.height / 2F

        viewport.apply(projection)
        viewport.apply(modelView)
        viewport.invert()

        val out = FloatArray(4)
        viewport.mult(floatArrayOf(engine.window.width / 2F, engine.window.height / 2F, -1F, 1F), out)
        if (out[3] == 0F) {
            return null
        }
        return PVector(
            out[0] / out[3], out[1] / out[3], out[2] / out[3]
        )
    }

    fun getEyePosition(): PVector {
        val c = (engine.window.g as PGraphics3D).camera.get()
        c.invert()
        return PVector(c.m03, c.m13, c.m23)
    }

    fun getEyeVector(): PVector? {
        val eye = getEyePosition()
        val screenPoint = unProject() ?: return null
        return screenPoint.sub(eye)
    }

    fun getLookingAt(): RealFace? {
        val eye = getEyePosition()
        val faces = engine.world.getNearFaces(World.toWorldPosition(eye))
        val screenPoint = unProject() ?: return null

        val detectedFaces = mutableListOf<RealFace>()

        for (face in faces) {
            val w = screenPoint.copy()
            val n = face.getNVector()
            val f = when (face.face) {
                Face.Bottom -> PVector(face.x * 100F + 50, face.y * -100F + 100, face.z * 100F + 50)
                Face.Top -> PVector(face.x * 100F + 50, face.y * -100F, face.z * 100F + 50)
                Face.South -> PVector(face.x * 100F + 50, face.y * -100F + 50, face.z * 100F + 100)
                Face.North -> PVector(face.x * 100F + 50, face.y * -100F + 50, face.z * 100F)
                Face.West -> PVector(face.x * 100F, face.y * -100F + 50, face.z * 100F + 50)
                Face.East -> PVector(face.x * 100F + 100, face.y * -100F + 50, face.z * 100F + 50)
            }
            f.sub(eye)
            w.sub(eye)
            w.mult(n.dot(f) / n.dot(w))
            w.add(eye)

            if (face.isPointContains(w)) {
                detectedFaces.add(face)
            }
        }
        if (detectedFaces.isEmpty()) return null
        detectedFaces.sortBy {
            PVector(it.x.toFloat(), it.y.toFloat(), it.z.toFloat()).dist(eye)
        }
        detectedFaces.reverse()
        return detectedFaces[0]
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

        engine.window.camera(ex, ey, ez, cx, cy, cz, 0f, 1f, 0f)
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
        lookingAt = getLookingAt()?.getPosition()
        if (pressedKeys.isEmpty()) return
        val m = PMatrix3D()
        val c0 = (engine.window.g as PGraphics3D).camera.get()
        c0.invert()
        val speed = if (pressedKeys.contains(Key.R)) moveSpeed * 2 else moveSpeed

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
        var ey: Float = c0.m13 + (if (moveToUp) (-10) else 0) + (if (moveToDown) (10) else 0)
        val ez: Float = c.m23
        val cx: Float = -c.m02 + ex
        var cy: Float = -c0.m12 + ey
        val cz: Float = -c.m22 + ez

        if (engine.world.getBlock(World.toWorldPosition(PVector(ex, ey, ez)))?.id != BlockId.Air) {
//            if (pressedKeys.contains(Key.W) || pressedKeys.contains(Key.S)) {
//                ex = c0.m03
//                cx = -c0.m02 + ex
//            }
//            if (pressedKeys.contains(Key.A) || pressedKeys.contains(Key.D)) {
//                ez = c0.m23
//                cz = -c0.m22 + ez
//            }
            if (pressedKeys.contains(Key.Space) || pressedKeys.contains(Key.Shift)) {
                ey = c0.m13
                cy = -c0.m12 + ey
            }
        }

        engine.window.camera(ex, ey, ez, cx, cy, cz, 0f, 1f, 0f)
    }
}
