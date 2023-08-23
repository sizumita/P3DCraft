package engine

import engine.structs.RealFace
import processing.core.PVector
import java.util.EnumSet

class World {
    companion object {
        const val X_LENGTH = 64
        const val Y_LENGTH = 64
        const val Z_LENGTH = 64
        const val LOOK_LENGTH = 5
    }
    /**
     * x,y,zの順番の三次元配列。
     * yが上下、xが左右、zが前後。
     * processingは右手系だが、我々は左手系に慣れているため実際に反映させるRendererでは座標は-1をかけたものになる。
     * 参考: https://yoppa.org/proga10/1301.html
     */
    var blocks = Array(64) { Array(64) { Array(64) { Block(BlockId.Air) } } }

    /**
     * - y=0にbedrockを置く
     * - y=1~30にブロックを自動生成する
     */
    fun initialize() {
        for (x in 0 until X_LENGTH) {
            for (z in 0 until Z_LENGTH) {
                blocks[x][0][z] = Block(BlockId.Bedrock)
            }
        }
        for (x in 0 until X_LENGTH) {
            for (y in 1 .. 20) {
                for (z in 0 until Z_LENGTH) {
                    blocks[x][y][z] = Block(BlockId.Stone)
                }
            }
            for (y in 21..30) {
                for (z in 0 until Z_LENGTH) {
                    blocks[x][y][z] = Block(BlockId.Dirt)
                }
            }
        }
    }

    fun putBlock(x: Int, y: Int, z: Int, block: Block) {
        blocks[x][y][z] = block
    }

    fun putBlockNextToFace(x: Int, y: Int, z: Int, face: Face, block: Block) {
        when (face) {
            Face.Top -> if (y+1 < Y_LENGTH) blocks[x][y+1][z] = block
            Face.Bottom -> if (y-1 >= 0) blocks[x][y-1][z] = block
            Face.North -> if (z-1 >= 0) blocks[x][y][z-1] = block
            Face.South -> if (z+1 < Z_LENGTH) blocks[x][y][z+1] = block
            Face.East -> if (x+1 < X_LENGTH) blocks[x+1][y][z] = block
            Face.West -> if (x-1 >= 0) blocks[x-1][y][z] = block
        }
    }

    /**
     * 与えられた座標から見ることができる面で、x,y,zそれぞれ±LOOK_LENGTHにある面を返す
     */
    fun getNearFaces(cx: Int, cy: Int, cz: Int): MutableList<RealFace> {
        val nearFaces = mutableListOf<RealFace>()
        // TODO この仕様だと、１ブロック超えた先に空洞があった場合そちらに置かれてしまう可能性がある。
        for (x in (if (cx - LOOK_LENGTH < 0) 0 else cx-LOOK_LENGTH) until (if (cx+LOOK_LENGTH >= X_LENGTH) X_LENGTH else cx+LOOK_LENGTH)) {
            for (y in (if (cy - LOOK_LENGTH < 0) 0 else cy-LOOK_LENGTH) until (if (cy+LOOK_LENGTH >= Y_LENGTH) Y_LENGTH else cy+LOOK_LENGTH)) {
                for (z in (if (cz - LOOK_LENGTH < 0) 0 else cz-LOOK_LENGTH) until (if (cz+LOOK_LENGTH >= Z_LENGTH) Z_LENGTH else cz+LOOK_LENGTH)) {
                    if (blocks[x][y][z].id == BlockId.Air) continue
//                    if ((x == cx) and (y == cy) and (z == cz)) continue
                    val faces = getCovered(x, y, z)
                    when {
                        // 北側にあった場合
                        (z < cz) and (!faces.contains(Face.South)) -> nearFaces.add(RealFace(x, y, z, Face.South))
                        // 南側にあった場合
                        (z > cz) and (!faces.contains(Face.North)) -> nearFaces.add(RealFace(x, y, z, Face.North))
                    }
                    when {
                        // 東側にあった場合
                        (x > cx) and (!faces.contains(Face.West)) -> nearFaces.add(RealFace(x, y, z, Face.West))
                        // 西側にあった場合
                        (x < cx) and (!faces.contains(Face.East)) -> nearFaces.add(RealFace(x, y, z, Face.East))
                    }
                    when {
                        // 下側にあった場合
                        (y < cy) and (!faces.contains(Face.Top)) -> nearFaces.add(RealFace(x, y, z, Face.Top))
                        // 上側にあった場合
                        (y > cy) and (!faces.contains(Face.Bottom)) -> nearFaces.add(RealFace(x, y, z, Face.Bottom))
                    }
                }
            }
        }
        return nearFaces
    }

    /**
     * ブロックのうち、完全に覆われているところなどを描画しないため、カバーされている場所を取得する
     */
    fun getCovered(x: Int, y: Int, z: Int): EnumSet<Face> {
        val block = blocks[x][y][z]

        val set = EnumSet.noneOf(Face::class.java)

        if (block.id == BlockId.Air) {
            return set
        }
        // bottom
        if (y > 0) {
            if (blocks[x][y-1][z].id !== BlockId.Air) {
                set.add(Face.Bottom)
            }
        }
        // top
        if (y < Y_LENGTH-1) {
            if (blocks[x][y+1][z].id !== BlockId.Air) {
                set.add(Face.Top)
            }
        }
        // north
        if (z < Z_LENGTH-1) {
            if (blocks[x][y][z+1].id !== BlockId.Air) {
                set.add(Face.South)
            }
        }
        // south
        if (z > 0) {
            if (blocks[x][y][z-1].id !== BlockId.Air) {
                set.add(Face.North)
            }
        }
        // east
        if (x < X_LENGTH-1) {
            if (blocks[x+1][y][z].id !== BlockId.Air) {
                set.add(Face.East)
            }
        }
        // west
        if (x > 0) {
            if (blocks[x-1][y][z].id !== BlockId.Air) {
                set.add(Face.West)
            }
        }

        return set
    }
}
