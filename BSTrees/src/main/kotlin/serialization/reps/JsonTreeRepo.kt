package serialization.reps

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import serialization.SerializableTree
import kotlinx.serialization.encodeToString
import mu.KotlinLogging
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import kotlin.io.path.Path

private val logger = KotlinLogging.logger { }

object JsonTreeRepo : DBTreeRepo {
    private fun createDirPaths() {
        File("JSONTreeRep").mkdir()
        File(Path("JSONTreeRep", "BSTree").toUri()).mkdir()
        File(Path("JSONTreeRep", "RBTree").toUri()).mkdir()
        File(Path("JSONTreeRep", "AvlTree").toUri()).mkdir()

        logger.info { "[JSON] Dir paths was created" }
    }

    private fun getPathToFile(typeTree: String, treeName: String): String {
        return Path("JSONTreeRep", typeTree, "${treeName}.json").toString()
    }

    override fun getTree(treeType: String, treeName: String): SerializableTree? {
        createDirPaths()

        val filePath = getPathToFile(treeType, treeName)
        lateinit var file: FileReader
        var fileFound = true

        return try {
            file = FileReader(filePath)
            val jsonText = file.readText()

            Json.decodeFromString<SerializableTree>(jsonText)
        } catch (e: FileNotFoundException) {
            logger.warn { "[JSON] Tree file not found"}
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

    override fun setTree(serializableTree: SerializableTree) {
        createDirPaths()

        val filePath = getPathToFile(serializableTree.treeType, serializableTree.name)
        lateinit var file: FileWriter

        try {
            file = FileWriter(filePath)
            file.write(Json.encodeToString(serializableTree))
        } catch (e: Exception) {
            logger.error { "[JSON] Error getting the tree: $e" }
            throw e
        } finally {
            file.flush()
            file.close()
        }
        logger.info { "[JSON] Set tree - treeName: ${serializableTree.name}, treeType: ${serializableTree.treeType}" }
    }

    override fun deleteTree(treeType: String, treeName: String) {
        createDirPaths()

        val path = getPathToFile(treeType, treeName)

        try {
            File(path).delete()
        } catch (e: Exception) {
            logger.error { "[JSON] Error getting the tree: $e" }
            throw e
        }
        logger.info { "[JSON] Deleted tree - treeName: $treeName, treeType: $treeType" }
    }
}