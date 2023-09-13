package launcher

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import engine.Engine
import engine.WorldData
import processing.core.PApplet

class Launcher(val window: PApplet) {
    var worlds = arrayListOf<WorldData>()
    var currentEngine: Engine? = null

    fun setup() {
        loadWorlds()
    }

    private fun loadWorlds() {
        worlds = ArrayList(window.listFiles("data/worlds").map {
            if (it.extension != "json") return
            jacksonObjectMapper().readValue(it.readText(), WorldData::class.java)
        })
    }

    fun startEngine(idx: Int) {
        if (currentEngine == null) {
            currentEngine = Engine.fromData(worlds[idx])
            currentEngine?.start()
        }
    }

    fun createNew() {
        if (currentEngine == null) {
            currentEngine = Engine()
            currentEngine?.start()
        }
    }
}
