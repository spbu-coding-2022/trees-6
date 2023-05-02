package balancers

import bstrees.model.trees.Balancer
import bstrees.model.trees.BSNode
import bstrees.model.trees.randomBinarySearch.RandomBSBalancer
import bstrees.model.trees.randomBinarySearch.RandomBSNode

import org.junit.jupiter.api.Test
import utils.BSTreeUtil

@Suppress("UNCHECKED_CAST")
class BalancerTest {

    @Test
    fun `left rotate`() {
        val balancer = RandomBSBalancer<Int, Int>()

        val privateLeftRotateField = Balancer::class.java.getDeclaredMethod("leftRotate", BSNode::class.java)
        privateLeftRotateField.isAccessible = true
        val newNode = privateLeftRotateField.invoke(balancer, BSTreeUtil.createBSTree().root) as RandomBSNode<Int, Int>

        assert(BSTreeUtil.checkNodeEquals(newNode, BSTreeUtil.createLeftRotatedBSTree().root))
    }

    @Test
    fun `right rotate`() {
        val balancer = RandomBSBalancer<Int, Int>()

        val privateRightRotateField = Balancer::class.java.getDeclaredMethod("rightRotate", BSNode::class.java)
        privateRightRotateField.isAccessible = true
        val newNode = privateRightRotateField.invoke(balancer, BSTreeUtil.createBSTree().root) as RandomBSNode<Int, Int>

        assert(BSTreeUtil.checkNodeEquals(newNode, BSTreeUtil.createRightRotatedBSTree().root))
    }
}