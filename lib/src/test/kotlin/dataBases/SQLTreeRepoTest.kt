package dataBases

import bstrees.model.dataBases.reps.SQLTreeRepo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import utils.TreeDataUtil
import java.nio.file.Files
import kotlin.io.path.Path

const val DB_NAME = "SQLTreeRepo"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SQLTreeRepoTest {
    private val repo = SQLTreeRepo(DB_NAME)

    @Test
    fun `setting and getting unique tree`(){
        val dataTree = TreeDataUtil.getTreeData("first", "BS")
        repo.setTree(dataTree)

        val resultDataTree = repo.getTree("first", "BS")

        Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree, resultDataTree), "error setting unique tree")
    }

    @Test
    fun `setting and getting trees with the same names and types`(){
        val dataTree1 = TreeDataUtil.getTreeData("name", "RB")
        val dataTree2 = TreeDataUtil.getTreeData("name", "RB")

        repo.setTree(dataTree1)
        val resultDataTree1 = repo.getTree("name", "RB")

        repo.setTree(dataTree2)
        val resultDataTree2 = repo.getTree("name", "RB")

        Assertions.assertAll("Error setting trees with the same name",
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree1, resultDataTree1)) },
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree2, resultDataTree2)) }
        )
    }

    @Test
    fun `setting and getting trees with the same names but different types`(){
        val dataTree1 = TreeDataUtil.getTreeData("name2", "BS")
        val dataTree2 = TreeDataUtil.getTreeData("name2", "RB")

        repo.setTree(dataTree1)
        repo.setTree(dataTree2)

        val resultDataTree1 = repo.getTree("name2", "BS")
        val resultDataTree2 = repo.getTree("name2", "RB")

        Assertions.assertAll("Error setting trees with the same name but different types",
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree1, resultDataTree1)) },
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree2, resultDataTree2)) }
        )
    }

    @Test
    fun `setting and getting trees with different names but the same types`(){
        val dataTree1 = TreeDataUtil.getTreeData("name3", "AVL")
        val dataTree2 = TreeDataUtil.getTreeData("name4", "AVL")

        repo.setTree(dataTree1)
        repo.setTree(dataTree2)

        val resultDataTree1 = repo.getTree("name3", "AVL")
        val resultDataTree2 = repo.getTree("name4", "AVL")

        Assertions.assertAll("Error setting trees with different names but the same types",
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree1, resultDataTree1)) },
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree2, resultDataTree2)) }
        )
    }

    @Test
    fun `getting non-existent tree`(){
        val resultDataTree = repo.getTree("name5", "RB")

        Assertions.assertNull(resultDataTree, "error getting non-existent tree")
    }

    @Test
    fun `deleting tree`(){
        val dataTree1 = TreeDataUtil.getTreeData("name6", "RB")

        repo.setTree(dataTree1)
        val resultDataTree1 = repo.getTree("name6", "RB")

        repo.deleteTree("name6", "RB")
        val resultDataTree2 = repo.getTree("name6", "RB")

        Assertions.assertAll("Error deleting tree",
            { Assertions.assertTrue(TreeDataUtil.isEqualsTrees(dataTree1, resultDataTree1)) },
            { Assertions.assertNull(resultDataTree2) }
        )
    }

    @AfterAll
    fun `delete DB`(){
        Files.walk(Path(DB_NAME))
            .sorted(Comparator.reverseOrder())
            .map { it.toFile() }
            .forEach { it.delete() }
    }
}