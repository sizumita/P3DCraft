package engine.structs

import engine.World

data class Position(val x: Int, val y: Int, val z: Int) {
    fun below() = when (y) {
        0 -> null
        else -> Position(x, y-1, z)
    }

    fun isInWorld(): Boolean {
        return 0 <= x && x < World.X_LENGTH
                && 0 <= y && y < World.Y_LENGTH
                && 0 <= z && z < World.Z_LENGTH
    }
}
