package engine.structs

import engine.Face
import processing.core.PVector

data class RealFace(val x: Int, val y: Int, val z: Int, val face: Face) {
    /**
     * 法線ベクトルを取得します。
     */
    fun getNVector() = when (face) {
        Face.Top -> PVector(0F, -1F, 0F)
        Face.Bottom -> PVector(0F, 1F, 0F)
        Face.North -> PVector(0F, 0F, -1F)
        Face.South -> PVector(0F, 0F, 1F)
        Face.East -> PVector(1F, 0F, 0F)
        Face.West -> PVector(-1F, 0F, 0F)
    }

    /**
     * 指定した座標がこの面の内側に存在するかどうかを取得します。
     */
    fun isPointContains(vector: PVector) = when (face) {
        Face.Top ->
            (x * 100 <= vector.x) and (vector.x <= x * 100 + 100) and
                    (z * 100 <= vector.z) and (vector.z <= z * 100 + 100) and (y * -100F == vector.y)
        Face.Bottom ->
            (x * 100 <= vector.x) and (vector.x <= x * 100 + 100) and
                    (z * 100 <= vector.z) and (vector.z <= z * 100 + 100) and (y * -100F + 100 == vector.y)
        Face.South ->
            (x * 100 <= vector.x) and (vector.x <= x * 100 + 100) and
                    (y * -100 + 100 >= vector.y) and (vector.y >= -(y * 100)) and (z * 100F + 100 == vector.z)
        Face.North ->
            (x * 100 <= vector.x) and (vector.x <= x * 100 + 100) and
                    (y * -100 + 100 >= vector.y) and (vector.y >= -(y * 100)) and (z * 100F == vector.z)
        Face.West ->
            (y * -100 + 100 >= vector.y) and (vector.y >= -(y * 100)) and
                    (z * 100 <= vector.z) and (vector.z <= z * 100 + 100) and (x * 100F == vector.x)
        Face.East ->
            (y * -100 + 100 >= vector.y) and (vector.y >= -(y * 100)) and
                    (z * 100 <= vector.z) and (vector.z <= z * 100 + 100) and (x * 100F + 100 == vector.x)
    }
}
