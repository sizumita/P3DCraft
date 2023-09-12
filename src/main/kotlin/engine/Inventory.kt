package engine

class Inventory(val engine: Engine) {
    private val selectedIdx = 0

    fun getSelectedBlockId(): BlockId {
        return BlockId.Stone
    }
}
