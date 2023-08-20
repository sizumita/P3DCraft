package engine

import processing.core.PApplet

class Engine(var window: PApplet) {
    var world = World()
    var renderer = Renderer(this)

    fun initialize() {
        world.initialize()
    }

    fun draw() {
        renderer.renderWorld()
    }

    fun keyPressed() {

    }

    fun keyReleased() {

    }
}
