package balancers

import binarySearchTree.BSTree
import utils.BSTreeUtil

import org.junit.jupiter.api.Test

class BalancerTest {

    @Test
    fun testLeftRotate() {
        val balancer = BSBalancer(BSTreeUtil.createBSTree())

        val privateLeftRotateField = Balancer::class.java.getDeclaredMethod("leftRotate")
        privateLeftRotateField.isAccessible = true
        privateLeftRotateField.invoke(balancer)

        val protectedTreeField = Balancer::class.java.getDeclaredField("tree")
        protectedTreeField.isAccessible = true
        val newTree = protectedTreeField.get(balancer) as BSTree<Int, Int>

        assert(BSTreeUtil.checkTreeEquals(newTree, BSTreeUtil.createLeftRotatedBSTree()))
    }

    @Test
    fun testRightRotate() {
        val balancer = BSBalancer(BSTreeUtil.createBSTree())

        val privateRightRotateField = Balancer::class.java.getDeclaredMethod("rightRotate")
        privateRightRotateField.isAccessible = true
        privateRightRotateField.invoke(balancer)

        val protectedTreeField = Balancer::class.java.getDeclaredField("tree")
        protectedTreeField.isAccessible = true
        val newTree = protectedTreeField.get(balancer) as BSTree<Int, Int>

        assert(BSTreeUtil.checkTreeEquals(newTree, BSTreeUtil.createRightRotatedBSTree()))
    }
}