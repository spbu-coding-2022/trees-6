package binarySearchTree

import org.junit.jupiter.api.*
import treeInvariants.TreesInvariants
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class BSTreeTest {

    private lateinit var keyValue: List<Pair<Int, Int>>
    private lateinit var bigKeyValue: List<Pair<Int, Int>>
    private val tree = BSTree<Int, Int>()
    private val treeChecker = TreesInvariants<Int, Int, BSNode<Int, Int>>()

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
        val tree = BSTree<Int, Int>()
        Assertions.assertNull(tree.root) { "After initialization, the tree must have NULL root" }
    }

    @Test
    fun `adding a node`() {
        tree.insert(keyValue[0].first, keyValue[0].second)
        Assertions.assertTrue(treeChecker.checkBsTreeInvariants(tree.root)) { "Error adding a node" }
    }

    @Test
    fun `adding nodes`() {
        for (i in keyValue) tree.insert(i.first, i.second)
        Assertions.assertTrue(treeChecker.checkBsTreeInvariants(tree.root)) { "Error adding nodes" }
    }

    @Test
    fun `adding a lot of nodes`() {
        for (i in bigKeyValue) {
            tree.insert(i.first, i.second)
        }
        Assertions.assertTrue(treeChecker.checkBsTreeInvariants(tree.root)) { "Error adding nodes" }
    }

    @Test
    fun `find test`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue.forEach {
            Assertions.assertEquals(it.second, tree.find(it.first)) { "Find test error" }
        }
    }

    @Test
    fun `adding nodes with equal key`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        tree.insert(keyValue[0].first, keyValue[0].second + 1)

        Assertions.assertEquals(
            keyValue[0].second + 1,
            tree.find(keyValue[0].first)
        ) { "Error when adding nodes with equal key" }
    }

    @Test
    fun `deleting a node`() {
        tree.insert(keyValue[0].first, keyValue[0].second)
        tree.delete(keyValue[0].first)
        assertAll("The tree must be balanced and must not contain a node after deletion",
            { Assertions.assertNull(tree.find(keyValue[0].first)) },
            { Assertions.assertTrue(treeChecker.checkBsTreeInvariants(tree.root)) }
        )
    }

    @Test
    fun `deleting nodes`() {
        keyValue.forEach { tree.insert(it.first, it.second) }
        keyValue.forEach {
            tree.delete(it.first)
            assertAll("The tree must be balanced and must not contain a node after deletion",
                { Assertions.assertNull(tree.find(it.first)) },
                { Assertions.assertTrue(treeChecker.checkBsTreeInvariants(tree.root)) }
            )
        }
    }

    @Test
    fun `deleting a lot of nodes`() {
        bigKeyValue.forEach { tree.insert(it.first, it.second) }
        bigKeyValue = bigKeyValue.shuffled()
        bigKeyValue.forEach{ tree.delete(it.first) }
        Assertions.assertNull(tree.root)
    }
}