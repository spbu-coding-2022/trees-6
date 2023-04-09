package redBlackTree

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import treeInvariants.TreesInvariants
import kotlin.random.Random

const val seed = 10

class RBTreeTest {

    private val randomizer = Random(seed)

    private val treeChecker = TreesInvariants<Int, Int, RBNode<Int, Int>>()
    private lateinit var keyValue: Array<Pair<Int, Int>>

    @BeforeEach
    fun beforeEach() {
        keyValue = Array(10000) { Pair(randomizer.nextInt(), randomizer.nextInt()) }
    }

    @Test
    fun addNodeTest() {
        val tree = RBTree<Int, Int>()
        for (i in 0 until 10000) {
            tree.insert(keyValue[i].first, keyValue[i].second)
            assert(treeChecker.checkRBTreeInvariants(tree.root!!)) { "Adding test error on $i iteration with params ${keyValue[i].first} ${keyValue[i].second}" }
        }
    }
}