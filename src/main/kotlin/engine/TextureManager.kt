package engine

class TextureManager(engine: Engine) {
    private val bedrock = engine.loadTexture("bedrock.png")
    private val red = engine.loadTexture("red.png")
    private val stone = engine.loadTexture("stone.png")
    private val dirt = engine.loadTexture("dirt.png")
    private val stoneBricks = engine.loadTexture("stone_bricks.png")
    private val acacia = engine.loadTexture("acacia_log.png")
    private val acaciaTop = engine.loadTexture("acacia_log_top.png")
    private val redStoneBlock = engine.loadTexture("redstone_block.png")
    private val bricks = engine.loadTexture("bricks.png")
    private val diamondBlock = engine.loadTexture("diamond_block.png")
    private val glass = engine.loadTexture("glass.png")
    private val blueIce = engine.loadTexture("blue_ice.png")

    fun getTexture(blockId: BlockId, face: Face) = when (blockId) {
        BlockId.Air -> bedrock
        BlockId.Bedrock -> bedrock
        BlockId.Stone -> stone
        BlockId.Dirt -> dirt
        BlockId.StoneBricks -> stoneBricks
        BlockId.Acacia -> when (face) {
            Face.Top, Face.Bottom -> acaciaTop
            else -> acacia
        }
        BlockId.RedStoneBlock -> redStoneBlock
        BlockId.Bricks -> bricks
        BlockId.DiamondBlock -> diamondBlock
        BlockId.Glass -> glass
        BlockId.BlueIce -> blueIce
    }
}
