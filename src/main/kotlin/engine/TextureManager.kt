package engine

class TextureManager(engine: Engine) {
    private val bedrock = engine.loadTexture("bedrock.png")
    private val red = engine.loadTexture("red.png")
    private val stone = engine.loadTexture("stone.png")
    private val dirt = engine.loadTexture("dirt.png")

    fun getTexture(blockId: BlockId, face: Face) = when (blockId) {
        BlockId.Air -> bedrock
        BlockId.Bedrock -> bedrock
        BlockId.Stone -> stone
        BlockId.Dirt -> dirt
    }
}
