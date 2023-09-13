package engine

enum class BlockId(val id: Int, val hasCollider: Boolean, val transmit: Boolean) {
    Bedrock(0, true, false),
    Air(1, false, true),
    Stone(2, true, false),
    Dirt(3, true, false),
    StoneBricks(4, true, false),
    Acacia(5, true, false),
    RedStoneBlock(6, true, false),
    Bricks(7, true, false),
    DiamondBlock(8, true, false),
    Glass(9, true, true),
    BlueIce(10, true, false);
    companion object {
        fun fromId(id: Int) = when (id) {
            0 -> Bedrock
            1 -> Air
            2 -> Stone
            3 -> Dirt
            4 -> StoneBricks
            5 -> Acacia
            6 -> RedStoneBlock
            7 -> Bricks
            8 -> DiamondBlock
            9 -> Glass
            10 -> BlueIce
            else -> Air
        }
    }
}
