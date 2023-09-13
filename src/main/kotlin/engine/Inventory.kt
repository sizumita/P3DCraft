package engine

class Inventory(val engine: Engine) {
    private var selectedIdx = 0
    val blocks = arrayListOf(
        BlockId.Stone,
        BlockId.Dirt,
        BlockId.StoneBricks,
        BlockId.Acacia,
        BlockId.RedStoneBlock,
        BlockId.Bricks,
        BlockId.DiamondBlock,
        BlockId.Glass,
        BlockId.BlueIce
        )

    fun keyPressed(key: Char) {
        selectedIdx = when (key) {
            '1', '!' -> 0
            '2', '@' -> 1
            '3', '#' -> 2
            '4', '$' -> 3
            '5', '%' -> 4
            '6', '^' -> 5
            '7', '&' -> 6
            '8', '*' -> 7
            '9', '(' -> 8
            else -> selectedIdx
        }
    }

    fun getSelectedBlockId(): BlockId {
        return blocks[selectedIdx]
    }
}
