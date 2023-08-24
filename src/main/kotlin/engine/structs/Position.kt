package engine.structs

data class Position(val x: Int, val y: Int, val z: Int) {
    fun below() = when (y) {
        0 -> null
        else -> Position(x, y-1, z)
    }
}
