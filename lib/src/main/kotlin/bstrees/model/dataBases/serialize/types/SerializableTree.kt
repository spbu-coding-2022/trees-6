package bstrees.model.dataBases.serialize.types
import kotlinx.serialization.Serializable

@Serializable
class SerializableTree(
    val name: String,
    val treeType: String,
    val root: SerializableNode?
)