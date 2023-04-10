package balancers

import Node
import binarySearchTree.BSNode

import org.junit.jupiter.api.Test
import utils.BSTreeUtil

@Suppress("UNCHECKED_CAST")
class BalancerTest {

    @Test
    fun testLeftRotate() {
        val balancer = BSBalancer<Int, Int>()

        val privateLeftRotateField = Balancer::class.java.getDeclaredMethod("leftRotate", Node::class.java)
        privateLeftRotateField.isAccessible = true
        val newNode = privateLeftRotateField.invoke(balancer, BSTreeUtil.createBSTree().root) as BSNode<Int, Int>

        assert(BSTreeUtil.checkNodeEquals(newNode, BSTreeUtil.createLeftRotatedBSTree().root))
    }

    @Test
    fun testRightRotate() {
        val balancer = BSBalancer<Int, Int>()

        val privateRightRotateField = Balancer::class.java.getDeclaredMethod("rightRotate", Node::class.java)
        privateRightRotateField.isAccessible = true
        val newNode = privateRightRotateField.invoke(balancer, BSTreeUtil.createBSTree().root) as BSNode<Int, Int>

        assert(BSTreeUtil.checkNodeEquals(newNode, BSTreeUtil.createRightRotatedBSTree().root))
    }
}