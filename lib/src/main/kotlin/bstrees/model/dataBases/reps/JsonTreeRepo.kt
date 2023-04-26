package bstrees.model.dataBases.reps

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import bstrees.model.dataBases.serialize.types.SerializableTree
import kotlinx.serialization.encodeToString
import mu.KotlinLogging
import java.io.*
import kotlin.io.path.Path
import java.nio.file.FileAlreadyExistsException

private val logger = KotlinLogging.logger { }


class JsonTreeRepo(private val dir: String) : DBTreeRepo {

    init {
        makeDir(dir)
        logger.info { "[JSON] Dir paths was created" }
    }

    private fun makeDir(dir: String) {
        File(dir).mkdir()
        File(Path(dir, "BSTree").toUri()).mkdir()
        File(Path(dir, "RBTree").toUri()).mkdir()
        File(Path(dir, "AvlTree").toUri()).mkdir()
    }

    private fun getPathToFile(treeName: String, typeTree: String): String {
        return Path(dir, typeTree, "${treeName}.json").toString()
    }

    override fun getTree(treeName: String, treeType: String): SerializableTree? {
        val filePath = getPathToFile(treeName, treeType)
        lateinit var file: FileReader
        var fileFound = true

        return try {
            file = FileReader(filePath)
            val jsonText = file.readText()

            Json.decodeFromString<SerializableTree>(jsonText)
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

    override fun setTree(serializableTree: SerializableTree) {
        val filePath = getPathToFile(serializableTree.name, serializableTree.treeType)
        lateinit var file: FileWriter
        var fileAlreadyExists = false

        try {
            if (File(filePath).exists()) {
                fileAlreadyExists = true
                throw FileAlreadyExistsException("")
            }
            file = FileWriter(filePath)
            file.write(Json.encodeToString(serializableTree))
        } catch (e: FileAlreadyExistsException) {
            logger.warn { "[JSON] A tree with that name already exists" }
        } catch (e: Exception) {
            logger.error { "[JSON] Error getting the tree: $e" }
            throw e
        } finally {
            if (!fileAlreadyExists) {
                file.flush()
                file.close()
            }
        }
        logger.info { "[JSON] Set tree - treeName: ${serializableTree.name}, treeType: ${serializableTree.treeType}" }
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