package engine

import engine.structs.Position
import engine.structs.RealFace
import processing.core.PVector
import java.util.EnumSet

class World {
    companion object {
        const val X_LENGTH = 64
        const val Y_LENGTH = 64
        const val Z_LENGTH = 64
        const val LOOK_LENGTH = 5

        fun toWorldPosition(vector: PVector) = Position(vector.x.toInt()/100, vector.y.toInt()/(-100), vector.z.toInt()/100)
    }
    /**
     * x,y,zの順番の三次元配列。
     * yが上下、xが左右、zが前後。
     * processingは右手系だが、我々は左手系に慣れているため実際に反映させるRendererでは座標は-1をかけたものになる。
     * 参考: https://yoppa.org/proga10/1301.html
     */
    var blocks = Array(64) { Array(64) { Array(64) { Block(BlockId.Air) } } }
    private var initialized = false

    /**
     * - y=0にbedrockを置く
     * - y=1~30にブロックを自動生成する
     */
    fun initialize() {
        if (initialized) return
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
        initialized = true
    }

    fun initializeFromData(worldData: WorldData) {
        worldData.blocks.forEachIndexed { ix, lists ->
            // x
            lists.forEachIndexed { iy, lists2 ->
                lists2.forEachIndexed { iz, i ->
                    blocks[ix][iy][iz] = Block(BlockId.fromId(i))
                }
            }
        }
        initialized = true
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

    fun removeBlock(x: Int, y: Int, z: Int) {
        blocks[x][y][z] = Block(BlockId.Air)
    }

    /**
     * 与えられた座標から見ることができる面で、x,y,zそれぞれ±LOOK_LENGTHにある面を返す
     */
    fun getNearFaces(p: Position): MutableList<RealFace> {
        val nearFaces = mutableListOf<RealFace>()
        // TODO この仕様だと、１ブロック超えた先に空洞があった場合そちらに置かれてしまう可能性がある。
        for (x in (if (p.x - LOOK_LENGTH < 0) 0 else p.x-LOOK_LENGTH) until (if (p.x+LOOK_LENGTH >= X_LENGTH) X_LENGTH else p.x+LOOK_LENGTH)) {
            for (y in (if (p.y - LOOK_LENGTH < 0) 0 else p.y-LOOK_LENGTH) until (if (p.y+LOOK_LENGTH >= Y_LENGTH) Y_LENGTH else p.y+LOOK_LENGTH)) {
                for (z in (if (p.z - LOOK_LENGTH < 0) 0 else p.z-LOOK_LENGTH) until (if (p.z+LOOK_LENGTH >= Z_LENGTH) Z_LENGTH else p.z+LOOK_LENGTH)) {
                    if (blocks[x][y][z].id == BlockId.Air) continue
//                    if ((x == p.x) and (y == p.y) and (z == p.z)) continue
                    val faces = getCovered(x, y, z)
                    when {
                        // 北側にあった場合
                        (z < p.z) and (!faces.contains(Face.South)) -> nearFaces.add(RealFace(x, y, z, Face.South))
                        // 南側にあった場合
                        (z > p.z) and (!faces.contains(Face.North)) -> nearFaces.add(RealFace(x, y, z, Face.North))
                    }
                    when {
                        // 東側にあった場合
                        (x > p.x) and (!faces.contains(Face.West)) -> nearFaces.add(RealFace(x, y, z, Face.West))
                        // 西側にあった場合
                        (x < p.x) and (!faces.contains(Face.East)) -> nearFaces.add(RealFace(x, y, z, Face.East))
                    }
                    when {
                        // 下側にあった場合
                        (y <= p.y) and (!faces.contains(Face.Top)) -> nearFaces.add(RealFace(x, y, z, Face.Top))
                        // 上側にあった場合
                        (y >= p.y) and (!faces.contains(Face.Bottom)) -> nearFaces.add(RealFace(x, y, z, Face.Bottom))
                    }
                }
            }
        }
        return nearFaces
    }

    fun getBlock(position: Position): Block? {
        if (!position.isInWorld()) return null
        return blocks[position.x][position.y][position.z]
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
            if (!blocks[x][y-1][z].id.transmit) {
                set.add(Face.Bottom)
            }
        }
        // top
        if (y < Y_LENGTH-1) {
            if (!blocks[x][y+1][z].id.transmit) {
                set.add(Face.Top)
            }
        }
        // north
        if (z < Z_LENGTH-1) {
            if (!blocks[x][y][z+1].id.transmit) {
                set.add(Face.South)
            }
        }
        // south
        if (z > 0) {
            if (!blocks[x][y][z-1].id.transmit) {
                set.add(Face.North)
            }
        }
        // east
        if (x < X_LENGTH-1) {
            if (!blocks[x+1][y][z].id.transmit) {
                set.add(Face.East)
            }
        }
        // west
        if (x > 0) {
            if (!blocks[x-1][y][z].id.transmit) {
                set.add(Face.West)
            }
        }

        return set
    }
}
