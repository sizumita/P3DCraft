import processing.core.PApplet
import processing.core.PConstants
import processing.core.PMatrix3D
import processing.opengl.PGraphics3D

class Controller(private val parent: PApplet) {
    private val moveSpeed = 10f
    private val rotationSpeed = 0.02f
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

    public fun keyControl() {
        if ( !parent.keyPressed ) return

        val M = PMatrix3D()
        val speed = this.moveSpeed

        if ( isPressedW ) {
            M.translate( 0f, 0f, speed );
        }
        if ( isPressedS ) {
            M.translate( 0f, 0f, -speed );
        }
        if ( isPressedA ) {
            M.translate( speed, 0f, 0f );
        }
        if ( isPressedD ) {
            M.translate( -speed, 0f, 0f );
        }
        if ( isPressedSpace ) {
            M.translate( 0f, speed, 0f );
        }
        if ( isPressedShift ) {
            M.translate( 0f, -speed, 0f );
        }
        if ( isPressedUp ) {
            M.rotateX(rotationSpeed);
        } else if ( isPressedDown ) {
            M.rotateX(-rotationSpeed);
        } else if ( isPressedRight ) {
            M.rotateY(rotationSpeed);
        } else if ( isPressedLeft ) {
            M.rotateY(-rotationSpeed);
        }

        val c = (parent.g as PGraphics3D).camera.get()
        c.preApply(M)

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
