package engine

import processing.core.PConstants
import java.util.EnumSet

class Renderer(private var engine: Engine) {
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
        if (block.id == BlockId.Air) return
        val faces = engine.world.getCovered(x, y, z)
        if (faces == EnumSet.allOf(Face::class.java)) {
            return
        }
        // TODO: テクスチャ
        if (!faces.contains(Face.Top)) {
            engine.window.pushMatrix()
            engine.window.fill(255)
            engine.window.beginShape()
            engine.window.vertex(x*100F, y*-100F, z*-100F)
            engine.window.vertex(x*100F+100, y*-100F, z*-100F)
            engine.window.vertex(x*100F+100, y*-100F, -1*(z*100F+100))
            engine.window.vertex(x*100F, y*-100F, z*-100F-100)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.North)) {
            engine.window.pushMatrix()
            engine.window.fill(255F, 0F, 0F)
            engine.window.beginShape()
            engine.window.vertex(x*100F+100, y*-100F, -1*(z*100F+100))
            engine.window.vertex(x*100F, y*-100F, z*-100F-100)
            engine.window.vertex(x*100F, -1*(y*100F-100), z*-100F-100)
            engine.window.vertex(x*100F+100, -1*(y*100F-100), -1*(z*100F+100))
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.South)) {
            engine.window.pushMatrix()
            engine.window.fill(0F, 255F, 0F)
            engine.window.beginShape()
            engine.window.vertex(x*100F+100, y*-100F, -1*(z*100F))
            engine.window.vertex(x*100F, y*-100F, z*-100F)
            engine.window.vertex(x*100F, -1*(y*100F-100), z*-100F)
            engine.window.vertex(x*100F+100, -1*(y*100F-100), -1*(z*100F))
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.East)) {
            engine.window.pushMatrix()
            engine.window.fill(0F, 0F, 255F)
            engine.window.beginShape()
            engine.window.vertex(x*100F+100, y*-100F, -1*(z*100F))
            engine.window.vertex(x*100F+100, y*-100F, -1*(z*100F+100))
            engine.window.vertex(x*100F+100, y*-100F+100, -1*(z*100F+100))
            engine.window.vertex(x*100F+100, y*-100F+100, -1*(z*100F))
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.West)) {
            engine.window.pushMatrix()
            engine.window.fill(255F, 0F, 255F)
            engine.window.beginShape()
            engine.window.vertex(x*100F, y*-100F, -1*(z*100F))
            engine.window.vertex(x*100F, y*-100F, -1*(z*100F+100))
            engine.window.vertex(x*100F, y*-100F+100, -1*(z*100F+100))
            engine.window.vertex(x*100F, y*-100F+100, -1*(z*100F))
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
        if (!faces.contains(Face.Bottom)) {
            engine.window.pushMatrix()
            engine.window.fill(255F, 255F, 0F)
            engine.window.beginShape()
            engine.window.vertex(x*100F, y*-100F+100, z*-100F)
            engine.window.vertex(x*100F+100, y*-100F+100, z*-100F)
            engine.window.vertex(x*100F+100, y*-100F+100, -1*(z*100F+100))
            engine.window.vertex(x*100F, y*-100F+100, z*-100F-100)
            engine.window.endShape(PConstants.CLOSE)
            engine.window.popMatrix()
        }
    }
}
