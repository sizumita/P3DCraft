package engine

import java.util.EnumSet

class World {
    companion object {
        const val X_LENGTH = 64
        const val Y_LENGTH = 64
        const val Z_LENGTH = 64
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
    }

    fun putBlock(x: Int, y: Int, z: Int, block: Block) {
        blocks[x][y][z] = block
    }

    /**
     * ブロックのうち、完全に覆われているところなどを描画しないため、カバーされいる場所を取得する
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
                set.add(Face.North)
            }
        }
        // south
        if (z > 0) {
            if (blocks[x][y][z-1].id !== BlockId.Air) {
                set.add(Face.South)
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
