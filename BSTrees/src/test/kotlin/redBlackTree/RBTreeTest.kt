package redBlackTree

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import treeInvariants.TreesInvariants
import kotlin.random.Random

const val seed = 10

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RBTreeTest {

    private val randomizer = Random(seed)

    private lateinit var keyValue: List<Pair<Int, Int>>
    private lateinit var bigKeyValue: List<Pair<Int, Int>>
    private val tree = RBTree<Int, Int>()
    private val treeChecker = TreesInvariants<Int, Int, RBNode<Int, Int>>()


    @BeforeAll
    fun beforeAll() {
        keyValue = List(1000) { Pair(randomizer.nextInt(5000), randomizer.nextInt(5000)) }.distinctBy { it.first }
        bigKeyValue = List(100000) { Pair(randomizer.nextInt(500000), randomizer.nextInt(500000)) }.distinctBy { it.first }
    }

    @BeforeEach
    fun beforeEach() {
        tree.root = null
    }

    @Test
    fun `init null tree`() {
        val tree = RBTree<Int, Int>()
        assertEquals(null, tree.root) { "After initialization, the tree must have NULL root" }
    }

    @Test
    fun `adding a node`() {
        keyValue.forEach {
            tree.insert(it.first, it.second)

            assert(treeChecker.checkRBTreeInvariants(tree.root)) { "Error adding a node. The invariants of the tree are violated" }
        }
    }

    @Test
    fun `adding node with equal keys`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        tree.insert(keyValue[0].first, keyValue[0].second + 1)

        assertEquals(keyValue[0].second + 1, tree.find(keyValue[0].first)) { "Error when adding nodes with equal key" }
    }

    @Test
    fun `big addition test`() {
        val bigTree = RBTree<Int, Int>()

        bigKeyValue.forEach { bigTree.insert(it.first, it.second) }

        assert(treeChecker.checkRBTreeInvariants(bigTree.root)) { "big adding test error" }
    }

    @Test
    fun `find test`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue.forEach {
            assertEquals(it.second, tree.find(it.first))
        }
    }

    @Test
    fun `deleting a node`() {
        keyValue.forEach { tree.insert(it.first, it.second) }

        keyValue.forEach {
            tree.delete(it.first)

            assertAll( "The tree must be balanced and must not contain a node after deletion",
                { assertEquals(null, tree.find(it.first)) },
                { assert(treeChecker.checkRBTreeInvariants(tree.root)) }
            )
        }
    }

    @Test
    fun `big deletion test`() {
        val bigTree = RBTree<Int, Int>()
        bigKeyValue.forEach { bigTree.insert(it.first, it.second) }

        bigKeyValue = bigKeyValue.shuffled()
        bigKeyValue.forEach { bigTree.delete(it.first) }

        assertEquals(null, bigTree.root) { "big deletion test error. The tree should be empty" }
    }
}