package engine
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class WorldData(
    val name: String,
    val blocks: List<List<List<Int>>>
)
