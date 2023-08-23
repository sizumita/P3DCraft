package engine

enum class BlockId(val id: Int, val hasCollider: Boolean, val rgb: Triple<Float, Float, Float>) {
    Bedrock(0, true, Triple(0F, 62F, 255F)),
    Air(1, false, Triple(0F, 0F, 0F)),
    Stone(2, true, Triple(102F, 102F, 102F)),
    Dirt(3, true, Triple(144F, 98F, 0F)),
}
