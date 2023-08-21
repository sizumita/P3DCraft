import processing.core.PApplet
import processing.core.PConstants
import processing.core.PMatrix3D
import processing.opengl.PGraphics3D

class Controller(private val parent: PApplet) {
    private val moveSpeed = 10f
    private val rotationSpeed = 0.02f
    private val mouseRotationSpeed = 0.002f
    private var isPressedW = false
    private var isPressedA = false
    private var isPressedS = false
    private var isPressedD = false
    var isPressedE = false
    var isPressedC = false
    private var isPressedUp = false
    private var isPressedDown = false
    private var isPressedLeft = false
    private var isPressedRight = false
    private var isPressedShift = false
    private var isPressedSpace = false

    private fun changeKeyState(nextState: Boolean, key: Char, keyCode: Int) {
        if ( key == 'w' ) {
            isPressedW = nextState
        } else if ( key == 's' ) {
            isPressedS = nextState
        } else if ( key == 'a' ) {
            isPressedA = nextState
        } else if ( key == 'd' ) {
            isPressedD = nextState
        } else if ( key == 'e' ) {
            isPressedE = nextState
        } else if ( key == 'c' ) {
            isPressedC = nextState
        } else if (key == ' ') {
            isPressedSpace = nextState
        }
        if ( keyCode == PConstants.UP ) {
            isPressedUp = nextState
        } else if ( keyCode == PConstants.DOWN ) {
            isPressedDown = nextState
        } else if ( keyCode == PConstants.RIGHT ) {
            isPressedRight = nextState
        } else if ( keyCode == PConstants.LEFT ) {
            isPressedLeft = nextState
        } else if (keyCode == PConstants.SHIFT) {
            isPressedShift = nextState
        }
    }

    fun keyPressed(key: Char, keyCode: Int) {
        changeKeyState(true, key, keyCode)
    }

    fun keyReleased(key: Char, keyCode: Int) {
        changeKeyState(false, key, keyCode)
    }

    fun mouseControl(dx: Int, dy: Int) {
        val m = PMatrix3D()
        m.rotateX(mouseRotationSpeed * dy * -1);
        m.rotateY(mouseRotationSpeed * dx)

        val c = (parent.g as PGraphics3D).camera.get()
        c.preApply(m)

        c.invert()

        val ex: Float = c.m03
        val ey: Float = c.m13
        val ez: Float = c.m23
        val cx: Float = -c.m02 + ex
        val cy: Float = -c.m12 + ey
        val cz: Float = -c.m22 + ez

        parent.camera( ex, ey, ez, cx, cy, cz, 0f, 1f, 0f )
    }

    public fun keyControl() {
        if ( !parent.keyPressed ) return

        val m = PMatrix3D()
        val speed = this.moveSpeed

        if ( isPressedW ) {
            m.translate( 0f, 0f, speed );
        }
        if ( isPressedS ) {
            m.translate( 0f, 0f, -speed );
        }
        if ( isPressedA ) {
            m.translate( speed, 0f, 0f );
        }
        if ( isPressedD ) {
            m.translate( -speed, 0f, 0f );
        }
        if ( isPressedSpace ) {
            m.translate( 0f, speed, 0f );
        }
        if ( isPressedShift ) {
            m.translate( 0f, -speed, 0f );
        }
        if ( isPressedUp ) {
            m.rotateX(rotationSpeed);
        } else if ( isPressedDown ) {
            m.rotateX(-rotationSpeed);
        } else if ( isPressedRight ) {
            m.rotateY(rotationSpeed);
        } else if ( isPressedLeft ) {
            m.rotateY(-rotationSpeed);
        }

        val c = (parent.g as PGraphics3D).camera.get()
        c.preApply(m)

        c.invert()

        val ex: Float = c.m03
        val ey: Float = c.m13
        val ez: Float = c.m23
        val cx: Float = -c.m02 + ex
        val cy: Float = -c.m12 + ey
        val cz: Float = -c.m22 + ez

        parent.camera( ex, ey, ez, cx, cy, cz, 0f, 1f, 0f )
    }
}
