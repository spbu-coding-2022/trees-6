package balancers

import trees.Balancer
import trees.Node
import trees.binarySearch.BSBalancer
import trees.binarySearch.BSNode

import org.junit.jupiter.api.Test
import utils.BSTreeUtil

@Suppress("UNCHECKED_CAST")
class BalancerTest {

    @Test
    fun `left rotate`() {
        val balancer = BSBalancer<Int, Int>()

        val privateLeftRotateField = Balancer::class.java.getDeclaredMethod("leftRotate", Node::class.java)
        privateLeftRotateField.isAccessible = true
        val newNode = privateLeftRotateField.invoke(balancer, BSTreeUtil.createBSTree().root) as BSNode<Int, Int>

        assert(BSTreeUtil.checkNodeEquals(newNode, BSTreeUtil.createLeftRotatedBSTree().root))
    }

    @Test
    fun `right rotate`() {
        val balancer = BSBalancer<Int, Int>()

        val privateRightRotateField = Balancer::class.java.getDeclaredMethod("rightRotate", Node::class.java)
        privateRightRotateField.isAccessible = true
        val newNode = privateRightRotateField.invoke(balancer, BSTreeUtil.createBSTree().root) as BSNode<Int, Int>

        assert(BSTreeUtil.checkNodeEquals(newNode, BSTreeUtil.createRightRotatedBSTree().root))
    }
}