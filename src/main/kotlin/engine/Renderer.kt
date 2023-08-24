package engine

import engine.structs.Position
import processing.core.PConstants
import java.util.EnumSet

class Renderer(private var engine: Engine) {
    private var textureManager: TextureManager? = null

    fun initialize() {
        textureManager = TextureManager(engine)
    }
    fun renderWorld() {
        // TODO: 将来的にPlayerの周囲のみをRenderingする
        for (x in 0 until World.X_LENGTH) {
            for (y in 0 until World.Y_LENGTH) {
                for (z in 0 until World.Z_LENGTH) {
                    renderBlock(x, y, z, engine.world.blocks[x][y][z])
                }
            }
        }
    }

    private fun renderBlock(x: Int, y: Int, z: Int, block: Block) {
        textureManager ?: return
        if (block.id == BlockId.Air) return
        val faces = engine.world.getCovered(x, y, z)
        if (faces == EnumSet.allOf(Face::class.java)) {
            return
        }
        if (Position(x, y, z) == engine.player.lookingAt) {
            engine.window.stroke(255)
            engine.window.strokeWeight(2F)
        } else {
            engine.window.stroke(0)
            engine.window.strokeWeight(0.5F)
        }

        if (!faces.contains(Face.Top)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(textureManager?.getTexture(block.id, Face.Top))
            engine.window.vertex(x*100F, y*-100F, z*100F, 0F, 0F)
            engine.window.vertex(x*100F+100, y*-100F, z*100F, 1F, 0F)
            engine.window.vertex(x*100F+100, y*-100F, z*100F+100, 1F, 1F)
            engine.window.vertex(x*100F, y*-100F, z*100F+100, 0F, 1F)
            engine.window.endShape()
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.North)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(textureManager?.getTexture(block.id, Face.North))
            engine.window.vertex(x*100F, y*-100F, z*100F, 1F, 0F)
            engine.window.vertex(x*100F+100, y*-100F, z*100F, 0F, 0F)
            engine.window.vertex(x*100F+100, -1*(y*100F-100), z*100F, 0F, 1F)
            engine.window.vertex(x*100F, -1*(y*100F-100), z*100F, 1F, 1F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.South)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(textureManager?.getTexture(block.id, Face.South))
            engine.window.vertex(x*100F, y*-100F, z*100F+100, 0F, 0F)
            engine.window.vertex(x*100F+100, y*-100F, z*100F+100, 1F, 0F)
            engine.window.vertex(x*100F+100, -1*(y*100F-100), z*100F+100, 1F, 1F)
            engine.window.vertex(x*100F, -1*(y*100F-100), z*100F+100, 0F, 1F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.West)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(textureManager?.getTexture(block.id, Face.West))
            engine.window.vertex(x*100F, y*-100F, z*100F, 0F, 0F)
            engine.window.vertex(x*100F, y*-100F, z*100F+100, 1F, 0F)
            engine.window.vertex(x*100F, y*-100F+100, z*100F+100, 1F, 1F)
            engine.window.vertex(x*100F, y*-100F+100, z*100F, 0F, 1F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.East)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(textureManager?.getTexture(block.id, Face.East))
            engine.window.vertex(x*100F+100, y*-100F, z*100F, 1F, 0F)
            engine.window.vertex(x*100F+100, y*-100F, z*100F+100, 0F, 0F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*100F+100, 0F, 1F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*100F, 1F, 1F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.Bottom)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(textureManager?.getTexture(block.id, Face.Bottom))
            engine.window.vertex(x*100F, y*-100F+100, z*100F, 1F, 0F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*100F, 0F, 0F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*100F+100, 0F, 1F)
            engine.window.vertex(x*100F, y*-100F+100, z*100F+100, 1F, 1F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
    }
}
