package converters

import bstrees.model.dataBases.converters.TreeToDataConverter
import bstrees.model.dataBases.converters.treesConverters.AvlToDataConverter
import bstrees.model.dataBases.converters.treesConverters.RandomBsToDataConverter
import bstrees.model.dataBases.converters.treesConverters.RbToDataConverter
import bstrees.model.dataBases.converters.utils.createStringConverter
import bstrees.model.trees.BSNode
import bstrees.model.trees.BSTree
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.BSTreeUtil
import java.util.stream.Stream


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TreeToDataConverterTest {

    @ParameterizedTest(name = "serialize deserialize ({0}, {1}) {2}tree")
    @MethodSource("keyValueTypeProvider")
    fun `serialize deserialize ({0}, {1}) tree`(keyType: String, valueType: String, treeType: String){
        fun <K : Comparable<K>, V, NODE_TYPE: BSNode<K, V, NODE_TYPE>, TREE_TYPE : BSTree<K, V, NODE_TYPE>> helper(strategy: TreeToDataConverter<K, V, *, *, TREE_TYPE>) {
            val tree: TREE_TYPE = BSTreeUtil.createTree(strategy.keyStringConverter, strategy.valueStringConverter, treeType)

            val dataTree = strategy.serializeTree(tree, "")

            val resultTree = strategy.deserializeTree(dataTree)

            Assertions.assertTrue(BSTreeUtil.checkNodeEquals(tree.root, resultTree.root), "Serialize deserialize (${keyType}, ${valueType}) ${treeType}tree test error")
        }
        helper(createStrategy(treeType, keyType, valueType))
    }


    companion object {
        @JvmStatic
        fun keyValueTypeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("String", "String", "BS"),
                Arguments.of("String", "Int", "BS"),
                Arguments.of("Int", "String", "BS"),
                Arguments.of("String", "String", "BS"),
                Arguments.of("String", "String", "AVL"),
                Arguments.of("String", "Int", "AVL"),
                Arguments.of("Int", "String", "AVL"),
                Arguments.of("String", "String", "AVL"),
                Arguments.of("String", "String", "RB"),
                Arguments.of("String", "Int", "RB"),
                Arguments.of("Int", "String", "RB"),
                Arguments.of("String", "String", "RB")
            )
        }
    }

    private fun createStrategy(treeType: String, keyType: String, valueType: String): TreeToDataConverter<*, *, *, *, *> = when (treeType) {
        "AVL" -> AvlToDataConverter(
            keyStringConverter = createStringConverter(keyType),
            valueStringConverter = createStringConverter(valueType),
            keyType = keyType,
            valueType = valueType
        )
        "RB" -> RbToDataConverter(
            keyStringConverter = createStringConverter(keyType),
            valueStringConverter = createStringConverter(valueType),
            keyType = keyType,
            valueType = valueType
        )
        "BS" -> RandomBsToDataConverter(
            keyStringConverter = createStringConverter(keyType),
            valueStringConverter = createStringConverter(valueType),
            keyType = keyType,
            valueType = valueType
        )
        else -> throw IllegalArgumentException("Unknown tree type $treeType")
    }
}