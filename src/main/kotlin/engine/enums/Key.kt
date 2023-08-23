package engine.enums

import processing.core.PConstants

enum class Key {
    W,
    S,
    A,
    D,
    Up,
    Down,
    Left,
    Right,
    Shift,
    Space,
    Ctrl;

    companion object {
        fun fromKey(key: Char): Key? {
            return when (key) {
                'w', 'W' -> W
                's', 'S' -> S
                'a', 'A' -> A
                'd', 'D' -> D
                ' ' -> Space
                else -> null
            }
        }

        fun fromKeyCode(keyCode: Int): Key? {
            return when (keyCode) {
                PConstants.UP -> Up
                PConstants.DOWN -> Down
                PConstants.LEFT -> Left
                PConstants.RIGHT -> Right
                PConstants.SHIFT -> Shift
                PConstants.CONTROL -> Ctrl
                else -> null
            }
        }
    }
}
