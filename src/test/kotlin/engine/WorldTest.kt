package engine

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.EnumSet

class WorldTest {
    @Test
    fun coveredTop() {
        val world = World()
        world.blocks[0][0][0] = Block(BlockId.Bedrock)
        world.blocks[0][1][0] = Block(BlockId.Dirt)
        assertEquals(world.getCovered(0, 0, 0), EnumSet.of(Face.Top))
    }

    @Test
    fun coveredBottom() {
        val world = World()
        world.blocks[1][1][1] = Block(BlockId.Bedrock)
        world.blocks[1][0][1] = Block(BlockId.Bedrock)
        assertEquals(world.getCovered(1, 1, 1), EnumSet.of(Face.Bottom))
    }

    @Test
    fun coveredNorth() {
        val world = World()
        world.blocks[1][1][1] = Block(BlockId.Bedrock)
        world.blocks[1][1][2] = Block(BlockId.Bedrock)
        assertEquals(world.getCovered(1, 1, 1), EnumSet.of(Face.North))
    }

    @Test
    fun coveredSouth() {
        val world = World()
        world.blocks[1][1][1] = Block(BlockId.Bedrock)
        world.blocks[1][1][0] = Block(BlockId.Bedrock)
        assertEquals(world.getCovered(1, 1, 1), EnumSet.of(Face.South))
    }

    @Test
    fun coveredEast() {
        val world = World()
        world.blocks[1][1][1] = Block(BlockId.Bedrock)
        world.blocks[2][1][1] = Block(BlockId.Bedrock)
        assertEquals(world.getCovered(1, 1, 1), EnumSet.of(Face.East))
    }

    @Test
    fun coveredWest() {
        val world = World()
        world.blocks[1][1][1] = Block(BlockId.Bedrock)
        world.blocks[0][1][1] = Block(BlockId.Bedrock)
        assertEquals(world.getCovered(1, 1, 1), EnumSet.of(Face.West))
    }

    @Test
    fun coveredNorthAndSouth() {
        val world = World()
        world.blocks[1][1][1] = Block(BlockId.Bedrock)
        world.blocks[1][1][2] = Block(BlockId.Bedrock)
        world.blocks[1][1][0] = Block(BlockId.Bedrock)
        assertEquals(world.getCovered(1, 1, 1), EnumSet.of(Face.North, Face.South))
    }
}
