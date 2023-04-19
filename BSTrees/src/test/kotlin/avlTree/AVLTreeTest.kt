package avlTree

import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import treeInvariants.TreesInvariants
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class AVLTreeTest {

    private lateinit var keyValue: List<Pair<Int, Int>>
    private lateinit var bigKeyValue: List<Pair<Int, Int>>
    private val tree = AvlTree<Int, Int>()
    private val treeChecker = TreesInvariants<Int, Int, AvlNode<Int, Int>>()

    @BeforeAll
    fun prepareNodes() {
        keyValue = List(1000) { Pair(Random.nextInt(5000), Random.nextInt(5000)) }.distinctBy { it.first }
        bigKeyValue = List(100000) { Pair(Random.nextInt(500000), Random.nextInt(500000)) }.distinctBy { it.first }
    }

    @BeforeEach
    fun resetTree() {
        tree.root = null
    }

    @Test
    fun `init null tree`() {
        val tree = AvlTree<Int, Int>()
        Assertions.assertNull(tree.root) { "After initialization, the tree must have NULL root" }
    }

    @Test
    fun `adding a node`() {
        tree.insert(keyValue[0].first, keyValue[0].second)
        Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) { "Error adding a node" }
    }

    @Test
    fun `adding nodes`() {
        for (i in keyValue) tree.insert(i.first, i.second)
        Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) { "Error adding nodes" }
    }

    @Test
    fun `adding a lot of nodes`() {
        for (i in bigKeyValue) {
            tree.insert(i.first, i.second)
        }
        Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) { "Error adding a lot of nodes" }
    }

    @Test
    fun `find test`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue.forEach {
            Assertions.assertEquals(it.second, tree.find(it.first)) { "Error finding nodes" }
        }
    }

    @ParameterizedTest(name = "Function get returns correct value for key {0}")
    @ValueSource(ints = [12, -121, 56, 1, 23728, 6464, 112])
    fun `find return a correct value`(key: Int) {
        keyValue.forEach { tree.insert(it.first, it.second) }

        Assertions.assertEquals(keyValue.find { it.first == key }?.second, tree.find(key))

        tree.delete(key)

        Assertions.assertEquals(null, tree.find(key))
    }

    @Test
    fun `adding nodes with equal key`() {
        keyValue.forEach { tree.insert(it.first, it.second) }
        keyValue.forEach {
            tree.insert(it.first, it.second + 1)
            Assertions.assertEquals(it.second + 1, tree.find(it.first))
        }

        Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) { "Error adding nodes with equal keys" }
    }

    @Test
    fun `deleting a node`() {
        tree.insert(keyValue[0].first, keyValue[0].second)
        tree.delete(keyValue[0].first)
        assertAll("Error deleting a node. The tree must be balanced and must not contain a node after deletion",
            { Assertions.assertNull(tree.find(keyValue[0].first)) },
            { Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) }
        )
    }

    @Test
    fun `deleting nodes`() {
        keyValue.forEach { tree.insert(it.first, it.second) }
        keyValue.forEach {
            tree.delete(it.first)
            assertAll("Error deleting nodes. The tree must be balanced and must not contain a node after deletion",
                { Assertions.assertNull(tree.find(it.first)) },
                { Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) }
            )
        }
    }

    @Test
    fun `deleting a lot of nodes`() {
        bigKeyValue.forEach { tree.insert(it.first, it.second) }

        bigKeyValue = bigKeyValue.shuffled()
        bigKeyValue.forEach { tree.delete(it.first) }

        Assertions.assertNull(tree.root) { "Error deleting nodes. The tree must be empty after deleting nodes" }
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

        Assertions.assertTrue(treeChecker.checkAvlTreeInvariants(tree.root)) {
            "Error deleting nodes which doesn't exist. " +
                    "Tree invariants aren't executed"
        }

        for (i in keyValue.indices) {
            if (i == 0) continue
            Assertions.assertEquals(keyValue[i].second, tree.find(keyValue[i].first))
            { "Error deleting nodes which doesn't exist. Some of nodes are lost" }
        }

    }
}