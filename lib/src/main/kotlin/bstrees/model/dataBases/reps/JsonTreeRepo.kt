package bstrees.model.dataBases.reps

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import bstrees.model.dataBases.TreeData
import kotlinx.serialization.encodeToString
import mu.KotlinLogging
import java.io.*
import kotlin.io.path.Path

private val logger = KotlinLogging.logger { }


class JsonTreeRepo(private val dir: String) : TreeRepo {

    init {
        makeDir(dir)
        logger.info { "[JSON] Dir paths was created" }
    }

    private fun makeDir(dir: String) {
        File(dir).mkdir()
        File(Path(dir, "BS").toUri()).mkdir()
        File(Path(dir, "RB").toUri()).mkdir()
        File(Path(dir, "AVL").toUri()).mkdir()
    }

    private fun getPathToFile(treeName: String, typeTree: String): String {
        return Path(dir, typeTree, "${treeName}.json").toString()
    }

    override fun getTree(treeName: String, treeType: String): TreeData? {
        val filePath = getPathToFile(treeName, treeType)
        lateinit var file: FileReader
        var fileFound = true

        return try {
            file = FileReader(filePath)
            val jsonText = file.readText()

            Json.decodeFromString<TreeData>(jsonText)
        } catch (e: FileNotFoundException) {
            logger.warn { "[JSON] Tree file not found" }
            fileFound = false
            null
        } catch (e: Exception) {
            logger.error { "[JSON] Error getting the tree: $e" }
            throw e
        } finally {
            if (fileFound) {
                file.close()
                logger.info { "[JSON] Got tree - treeName: $treeName, treeType: $treeType" }
            }
        }
    }

    override fun setTree(treeData: TreeData) {
        val filePath = getPathToFile(treeData.name, treeData.treeType)
        lateinit var file: FileWriter

        try {
            file = FileWriter(filePath)
            file.write(Json.encodeToString(treeData))
        } catch (e: Exception) {
            logger.error { "[JSON] Error getting the tree: $e" }
            throw e
        } finally {
            file.flush()
            file.close()
        }
        logger.info { "[JSON] Set tree - treeName: ${treeData.name}, treeType: ${treeData.treeType}" }
    }

    override fun deleteTree(treeName: String, treeType: String) {
        val path = getPathToFile(treeName, treeType)

        try {
            File(path).delete()
        } catch (e: Exception) {
            logger.error { "[JSON] Error getting the tree: $e" }
            throw e
        }
        logger.info { "[JSON] Deleted tree - treeName: $treeName, treeType: $treeType" }
    }
}