package engine

import engine.structs.Position
import processing.core.PConstants
import java.util.EnumSet
import kotlin.math.abs

class Renderer(private var engine: Engine) {
    fun renderWorld() {
        // playerから横35ブロック、たて25ブロック以内を描画
        val eye = World.toWorldPosition(engine.player.getEyePosition())
        for (x in 0 until World.X_LENGTH) {
            if (abs(eye.x-x) > 35) continue
            for (y in 0 until World.Y_LENGTH) {
                if (abs(eye.y-y) > 25) continue
                for (z in 0 until World.Z_LENGTH) {
                    if (abs(eye.z-z) > 35) continue
                    renderBlock(x, y, z, engine.world.blocks[x][y][z])
                }
            }
        }
    }

    private fun renderBlock(x: Int, y: Int, z: Int, block: Block) {
        engine.textureManager ?: return
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
            engine.window.texture(engine.textureManager?.getTexture(block.id, Face.Top))
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
            engine.window.texture(engine.textureManager?.getTexture(block.id, Face.North))
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
            engine.window.texture(engine.textureManager?.getTexture(block.id, Face.South))
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
            engine.window.texture(engine.textureManager?.getTexture(block.id, Face.West))
            engine.window.vertex(x*100F, y*-100F, z*100F, 1F, 1F)
            engine.window.vertex(x*100F, y*-100F, z*100F+100, 0F, 1F)
            engine.window.vertex(x*100F, y*-100F+100, z*100F+100, 0F, 0F)
            engine.window.vertex(x*100F, y*-100F+100, z*100F, 1F, 0F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.East)) {
            engine.window.pushMatrix()
            engine.window.beginShape()
            engine.window.texture(engine.textureManager?.getTexture(block.id, Face.East))
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
            engine.window.texture(engine.textureManager?.getTexture(block.id, Face.Bottom))
            engine.window.vertex(x*100F, y*-100F+100, z*100F, 1F, 0F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*100F, 0F, 0F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*100F+100, 0F, 1F)
            engine.window.vertex(x*100F, y*-100F+100, z*100F+100, 1F, 1F)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
    }
}
