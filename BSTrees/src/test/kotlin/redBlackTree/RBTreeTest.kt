package redBlackTree

import org.junit.jupiter.api.*
import treeInvariants.TreesInvariants
import kotlin.random.Random

const val seed = 10

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RBTreeTest {

    private val randomizer = Random(seed)

    private lateinit var keyValue: Array<Pair<Int, Int>>
    private val tree = RBTree<Int, Int>()
    private val treeChecker = TreesInvariants<Int, Int, RBNode<Int, Int>>()


    @BeforeAll
    fun beforeAll() {
        keyValue = Array(10000) { Pair(randomizer.nextInt(10000), randomizer.nextInt(10000)) }
    }

    @Test
    fun `invariants after addition`() {
        val tree = RBTree<Int, Int>()
        for (i in 0 until 10000) {
            tree.insert(keyValue[i].first, keyValue[i].second)
            assert(treeChecker.checkRBTreeInvariants(tree.root)) { "Adding test error on $i iteration with params ${keyValue[i].first} ${keyValue[i].second}" }
        }
    }

    @Test
    fun `invariants after deletion`(){
        keyValue.forEach { tree.insert(it.first, it.second) }
        keyValue.shuffle()
        for(i in 0 until 10000){
            tree.delete(keyValue[i].first)
            assert(treeChecker.checkRBTreeInvariants(tree.root)) { "Adding test error on $i iteration with params ${keyValue[i].first} ${keyValue[i].second}" }

        }
        assert(tree.root == null) { "tree is not null" }
    }
}