package engine

import processing.core.PVector

enum class Direction {
    /**
     * Z-
     */
    North,
    /**
     * Z+
     */
    South,
    /**
     * X+
     */
    East,
    /**
     * X-
     */
    West;

    companion object {
        fun getNearDirection(vector: PVector): Direction {
            var d = North
            var angle = PVector.angleBetween(vector, PVector(0F, 0F, -1F))
            val angleSouth = PVector.angleBetween(vector, PVector(0F, 0F, 1F))
            if (angleSouth < angle) {
                d = South
                angle = angleSouth
            }
            val angleEast = PVector.angleBetween(vector, PVector(1F, 0F, 0F))
            if (angleEast < angle) {
                d = East
                angle = angleEast
            }
            val angleWest = PVector.angleBetween(vector, PVector(-1F, 0F, 0F))
            if (angleWest < angle) {
                d = West
            }
            return d
        }
    }
}
