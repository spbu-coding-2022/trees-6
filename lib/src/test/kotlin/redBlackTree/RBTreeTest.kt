package redBlackTree

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import bstrees.model.trees.redBlack.RBNode
import bstrees.model.trees.redBlack.RBTree
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

const val seed = 10

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class RBTreeTest {

    private val randomizer = Random(seed)

    private lateinit var keyValue: List<Pair<Int, Int>>
    private lateinit var bigKeyValue: List<Pair<Int, Int>>
    private val tree = RBTree<Int, Int>()
    private val treeChecker = RBTreeInvariants<Int, Int, RBNode<Int, Int>>()


    @BeforeAll
    fun prepareNodes() {
        keyValue = List(1000) { Pair(randomizer.nextInt(5000), randomizer.nextInt(5000)) }.distinctBy { it.first }
        bigKeyValue =
            List(100000) { Pair(randomizer.nextInt(500000), randomizer.nextInt(500000)) }.distinctBy { it.first }
    }

    @BeforeEach
    fun resetTree() {
        tree.root = null
    }

    @Test
    fun `init null tree`() {
        val tree = RBTree<Int, Int>()
        assertEquals(null, tree.root) { "After initialization, the tree must have NULL root" }
    }

    @Test
    fun `adding a node`() {
        tree.insert(keyValue[0].first, keyValue[0].second)
        Assertions.assertTrue(treeChecker.checkRBTreeInvariants(tree.root)) { "Error adding a node" }
    }

    @Test
    fun `adding nodes`() {
        keyValue.forEach {
            tree.insert(it.first, it.second)

            assert(treeChecker.checkRBTreeInvariants(tree.root)) { "Error adding nodes" }
        }
    }

    @Test
    fun `adding nodes with equal key`() {
        keyValue.forEach { tree.insert(it.first, it.second) }
        keyValue.forEach {
            tree.insert(it.first, it.second + 1)
            assertEquals(it.second + 1, tree.find(it.first))
        }

        Assertions.assertTrue(treeChecker.checkRBTreeInvariants(tree.root)) { "Error adding nodes with equal keys" }
    }

    @Test
    fun `deleting a part of nodes`(){
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue = keyValue.shuffled()

        for(i in 0 until keyValue.size){
            tree.delete(keyValue[i].first)
            for(j in i + 1 until keyValue.size){
                assertAll("Error deleting a node. The tree must be balanced and must contain all nodes that are not deleted",
                    { Assertions.assertNotNull(tree.find(keyValue[j].first)) },
                    { Assertions.assertTrue(treeChecker.checkRBTreeInvariants(tree.root)) }
                )
            }
        }
    }

    @Test
    fun `adding a lot of nodes`() {
        val bigTree = RBTree<Int, Int>()

        bigKeyValue.forEach { bigTree.insert(it.first, it.second) }

        assert(treeChecker.checkRBTreeInvariants(bigTree.root)) { "Error adding a lot of nodes" }
    }

    @Test
    fun `find test`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue.forEach {
            assertEquals(it.second, tree.find(it.first)) { "Error finding nodes" }
        }
    }

    @ParameterizedTest(name = "Function get returns correct value for key {0}")
    @MethodSource("keyProvider")
    fun `find return a correct value`(key: Int) {
        keyValue.forEach { tree.insert(it.first, it.second) }

        assertEquals(keyValue.find { it.first == key }?.second, tree.find(key))

        tree.delete(key)

        assertEquals(null, tree.find(key))
    }

    companion object {
        @JvmStatic
        fun keyProvider(): List<Arguments> {
            return (0..1000).map {
                Arguments.of(Random.nextInt(5000))
            }
        }
    }

    @Test
    fun `deleting a node`() {
        tree.insert(keyValue[0].first, keyValue[0].second)
        tree.delete(keyValue[0].first)
        assertAll("Error deleting a node. The tree must be balanced and must not contain a node after deletion",
            { Assertions.assertNull(tree.find(keyValue[0].first)) },
            { Assertions.assertTrue(treeChecker.checkRBTreeInvariants(tree.root)) }
        )
    }

    @Test
    fun `deleting nodes`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue.forEach {
            tree.delete(it.first)

            assertAll("Error deleting nodes. The tree must be balanced and must not contain a node after deletion",
                { assertEquals(null, tree.find(it.first)) },
                { assert(treeChecker.checkRBTreeInvariants(tree.root)) }
            )
        }
    }

    @Test
    fun `deleting a lot of nodes`() {
        val bigTree = RBTree<Int, Int>()
        bigKeyValue.forEach { bigTree.insert(it.first, it.second) }

        bigKeyValue = bigKeyValue.shuffled()
        bigKeyValue.forEach { bigTree.delete(it.first) }

        assertEquals(null, bigTree.root) { "Error deleting nodes. The tree must be empty after deleting nodes" }
    }


    @Test
    fun `find in an empty tree`() {
        Assertions.assertNull(tree.find(bigKeyValue[0].first)) {
            "Error finding a node which doesn't exist. " +
                    "It must return null."
        }
        try {
            tree.find(bigKeyValue[0].first)
        } catch (e: Exception) {
            Assertions.assertTrue(false) {
                "Error finding a node which doesn't exist. " +
                        "Exception is caught"
            }
        }
    }

    @Test
    fun `delete a node which isn't in a tree`() {

        try {
            tree.delete(bigKeyValue[0].first)
        } catch (e: Exception) {
            Assertions.assertTrue(false) {
                "Error deleting a node which doesn't exist. " +
                        "Exception is caught"
            }
        }

        Assertions.assertNull(tree.root) {
            "Error deleting a node which doesn't exist. " +
                    "Tree must stay null"
        }
    }

    @Test
    fun `delete a node which isn't in a tree(tree isn't empty)`() {

        for (i in keyValue.indices) {
            if (i == 0) continue
            tree.insert(keyValue[i].first, keyValue[i].second)
        }

        try {
            tree.delete(bigKeyValue[0].first)
        } catch (e: Exception) {
            Assertions.assertTrue(false) {
                "Error deleting nodes which doesn't exist. " +
                        "Exception is caught"
            }
        }

        Assertions.assertTrue(treeChecker.checkRBTreeInvariants(tree.root)) {
            "Error deleting nodes which doesn't exist. " +
                    "Tree invariants aren't executed"
        }

        for (i in keyValue.indices) {
            if (i == 0) continue
            assertEquals(keyValue[i].second, tree.find(keyValue[i].first))
            { "Error deleting nodes which doesn't exist. Some of nodes are lost" }
        }

    }
}