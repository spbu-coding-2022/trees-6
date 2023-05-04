package balancers

import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.dataBases.converters.utils.createStringConverter
import bstrees.model.trees.randomBinarySearch.RandomBSBalancer
import bstrees.model.trees.randomBinarySearch.RandomBSTree

import org.junit.jupiter.api.Test
import utils.BSTreeUtil

@Suppress("UNCHECKED_CAST")
class BalancerTest {

    @Test
    fun `left rotate`() {
        fun <K : Comparable<K>, V> helper(keyStringConverter: ComparableStringConverter<K>, valueStringConverter: StringConverter<V>) {
            val balancer = RandomBSBalancer<K, V>()

            val tree: RandomBSTree<K, V> = BSTreeUtil.createTree(keyStringConverter, valueStringConverter, "BS")
            val newNode = balancer.bsLeftRotate(tree.root!!)

            val leftRotatedTree: RandomBSTree<K, V> = BSTreeUtil.createLeftRotatedTree(keyStringConverter, valueStringConverter, "BS")

            assert(BSTreeUtil.checkNodeEquals(newNode, leftRotatedTree.root))
        }
        helper(
            keyStringConverter = createStringConverter("Int"),
            valueStringConverter = createStringConverter("Int"),
        )
    }

    @Test
    fun `right rotate`() {
        fun <K : Comparable<K>, V> helper(keyStringConverter: ComparableStringConverter<K>, valueStringConverter: StringConverter<V>) {
            val balancer = RandomBSBalancer<K, V>()

            val tree: RandomBSTree<K, V> = BSTreeUtil.createTree(keyStringConverter, valueStringConverter, "BS")
            val newNode = balancer.bsRightRotate(tree.root!!)

            val rightRotatedTree: RandomBSTree<K, V> = BSTreeUtil.createRightRotatedTree(keyStringConverter, valueStringConverter, "BS")

            assert(BSTreeUtil.checkNodeEquals(newNode, rightRotatedTree.root))
        }
        helper(
            keyStringConverter = createStringConverter("Int"),
            valueStringConverter = createStringConverter("Int"),
        )
    }
}